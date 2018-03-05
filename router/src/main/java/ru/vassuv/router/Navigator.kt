package ru.vassuv.router

import android.os.Bundle

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import ru.terrakok.cicerone.commands.*
import ru.vassuv.startapp.utils.routing.animate.AnimateForward
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

abstract class Navigator
protected constructor(val fragmentManager: FragmentManager,
                      val containerId: Int,
                      var onChangeFragment: () -> Unit) : ru.terrakok.cicerone.Navigator {

    var screenNames: MutableList<String> = ArrayList()
        internal set(value) {
            field = value
            printScreensScheme()
        }

    private val logger = Logger.getLogger("Navigator")

    init {
        fragmentManager.addOnBackStackChangedListener { onChangeFragment() }
    }

    fun setScreenNames(value: MutableList<*>) {
        screenNames = value.map { it.toString() }.toMutableList()
    }

    fun FragmentTransaction.applyCommands(commands: Array<out Command>?): FragmentTransaction {
        commands?.forEach { command ->
            when (command) {
                is Forward -> {
                    replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                    addToBackStack(command.screenKey)
                    screenNames.add(command.screenKey)
                }
                is AnimateForward -> {
                    replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                    apply {
                        command.animate.invoke(this)
                    }
                    addToBackStack(command.screenKey)
                    screenNames.add(command.screenKey)
                }
                is Back -> {
                    if (fragmentManager.backStackEntryCount > 0)
                        fragmentManager.popBackStackImmediate()
                    else
                        exit()

                    if (screenNames.size > 0)
                        screenNames.removeAt(screenNames.size - 1)
                }
                is Replace -> {
                    if (fragmentManager.backStackEntryCount > 0) {
                        fragmentManager.popBackStackImmediate()
                    }
                    replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                    addToBackStack(command.screenKey)

                    if (screenNames.size > 0)
                        screenNames.removeAt(screenNames.size - 1)
                    screenNames.add(command.screenKey)
                }
                is BackTo -> {
                    val key = command.screenKey

                    if (key == null) {
                        backToRoot()
                        screenNames.clear()
                    } else {
                        var hasScreen = false
                        for (i in 0 until fragmentManager.backStackEntryCount) {
                            if (key == fragmentManager.getBackStackEntryAt(i).name) {
                                fragmentManager.popBackStackImmediate(key, 0)
                                hasScreen = true
                                break
                            }
                        }
                        if (!hasScreen) {
                            backToUnexisting()
                        }
                    }
                    if (screenNames.size > 0)
                        screenNames = ArrayList(screenNames.subList(0,
                                fragmentManager.backStackEntryCount + 1))
                }
            }
        }
        return this
    }

    override fun applyCommands(commands: Array<out Command>) {
        fragmentManager.beginTransaction().applyCommands(commands).commitAllowingStateLoss()
        val lastFragment = screenNames.lastOrNull()
        if (lastFragment != null) openFragment(screenNames.size - 1, lastFragment)
        printScreensScheme()
    }

    private fun printScreensScheme() = logger.log(Level.ALL, "Screen chain:", screenNames.joinToString(" ➔ ", "[", "]"))

    private fun backToRoot() {
        (0 until fragmentManager.backStackEntryCount).forEach { fragmentManager.popBackStack() }
        fragmentManager.executePendingTransactions()
    }

    protected abstract fun createFragment(screenKey: String, data: Bundle): Fragment?

    protected abstract fun exit()

    protected abstract fun openFragment(position: Int, name: String)

    private fun backToUnexisting() = backToRoot()

}
