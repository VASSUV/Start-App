package ru.vassuv.startapp.utils.routing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.*
import ru.vassuv.startapp.utils.atlibrary.Logger
import ru.vassuv.startapp.utils.routing.animate.AnimateForward

abstract class Navigator
protected constructor(private val fragmentManager: FragmentManager,
                      private val containerId: Int,
                      private var onChangeFragment: () -> Unit) : Navigator {
    internal var screenNames: MutableList<String> = ArrayList()

    init {
        fragmentManager.addOnBackStackChangedListener { onChangeFragment() }
    }

    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> {
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                        .addToBackStack(command.screenKey)
                        .commit()
                screenNames.add(command.screenKey)
            }
            is AnimateForward -> {
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                        .apply {
                            command.animate.invoke(this)
                        }
                        .addToBackStack(command.screenKey)
                        .commit()
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
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                        .addToBackStack(command.screenKey)
                        .commit()

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
        val lastFragment = screenNames.lastOrNull()
        if(lastFragment!= null) openFragment(screenNames.size - 1, lastFragment)

        printScreensScheme()
    }

    private fun printScreensScheme() = Logger.trace("Screen chain:", screenNames.joinToString(" ➔ ", "[", "]"))

    fun setScreenNames(value: MutableList<*>) {
        screenNames = value.map { it.toString() } as MutableList<String>
        printScreensScheme()
    }

    private fun backToRoot() {
        (0 until fragmentManager.backStackEntryCount).forEach { fragmentManager.popBackStack() }
        fragmentManager.executePendingTransactions()
    }

    protected abstract fun createFragment(screenKey: String, data: Bundle): Fragment?

    protected abstract fun exit()

    protected abstract fun openFragment(position: Int, name: String)

    private fun backToUnexisting() = backToRoot()

}