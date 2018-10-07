package ru.vassuv.startapp.utils.atlibrary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.*


abstract class Navigator
protected constructor(private val fragmentManager: FragmentManager,
                      private val containerId: Int,
                      onChangeFragment: () -> Unit) : Navigator {
    internal var screenNames: MutableList<String> = ArrayList()

    init {
        fragmentManager.addOnBackStackChangedListener(onChangeFragment)
    }

    private fun FragmentTransaction.applyCommand(command: Command): FragmentTransaction {
        when (command) {
            is Forward -> {
                val animList = getAnimationList(command.screenKey)
                setCustomAnimations(animList.enterAnimation,
                        animList.exitAnimation,
                        animList.popEnterAnimation,
                        animList.popExitAnimation)
                replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                addToBackStack(command.screenKey)
                screenNames.add(command.screenKey)
            }
            is Replace -> {
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStackImmediate()
                }
                val animList = getAnimationList(command.screenKey)
                setCustomAnimations(animList.enterAnimation,
                        animList.exitAnimation,
                        animList.popEnterAnimation,
                        animList.popExitAnimation)
                replace(containerId, createFragment(command.screenKey, command.transitionData as Bundle))
                addToBackStack(command.screenKey)

                if (screenNames.size > 0)
                    screenNames.removeAt(screenNames.size - 1)
                screenNames.add(command.screenKey)
            }
            is Back -> {
                fragmentManager.popBackStackImmediate()
                showLastFragment(if (fragmentManager.fragments.size == 0) null else fragmentManager.fragments[fragmentManager.fragments.size - 1])

                if (screenNames.size > 0)
                    screenNames.removeAt(screenNames.size - 1)
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
                showLastFragment(if (fragmentManager.fragments.size == 0) null else fragmentManager.fragments[fragmentManager.fragments.size - 1])

                if (screenNames.size > 0)
                    screenNames = ArrayList(screenNames.subList(0,
                            fragmentManager.backStackEntryCount + 1))
            }
        }
        return this
    }

    private fun FragmentTransaction.showLastFragment(fragment: Fragment?) {
        if (fragment != null) {
            val animList = getAnimationList(null)
            setCustomAnimations(animList.popEnterAnimation, animList.popExitAnimation)
            show(fragment)
        } else {
            exit()
        }
    }

    abstract fun getAnimationList(screenKey: String?): AnimationList

    override fun applyCommands(commands: Array<out Command>?) {
        if (commands != null) {
            for (command in commands) {
                fragmentManager.beginTransaction().applyCommand(command).commitAllowingStateLoss()
            }
        }
        val lastFragment = screenNames.lastOrNull()
        if (lastFragment != null) openFragment(screenNames.size - 1, lastFragment)
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

    protected abstract fun createFragment(screenKey: String, data: Bundle): Fragment

    protected abstract fun exit()

    protected abstract fun openFragment(fragmentPosition: Int, name: String)

    private fun backToUnexisting() = backToRoot()

}

class AnimationList( val enterAnimation : Int,
                     val exitAnimation : Int,
                     val popEnterAnimation : Int,
                     val popExitAnimation : Int)

