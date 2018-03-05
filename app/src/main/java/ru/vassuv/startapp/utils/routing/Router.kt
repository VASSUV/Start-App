package ru.vassuv.startapp.utils.routing

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.*
import ru.vassuv.startapp.utils.UiListener
import ru.vassuv.startapp.utils.routing.animate.AnimateForward

object Router: BaseRouter() {
    lateinit var onNewRootScreenListener: ((screenKey: String) -> Unit)
    lateinit var onBackScreenListener: () -> Unit
    lateinit var uiListener: UiListener

    fun backTo(screenKey: String) = executeCommands(BackTo(screenKey))

    fun navigateTo(screenKey: String, data: Any? = Bundle()) = executeCommands(Forward(screenKey, data))

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun navigateToWithAnimate(screenKey: String,  animate: FragmentTransaction.() -> Unit) {
        executeCommands(AnimateForward(screenKey, Bundle(), animate))
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun navigateToWithAnimate(screenKey: String, data: Any?, animate: FragmentTransaction.() -> Unit) {
        executeCommands(AnimateForward(screenKey, data, animate))
    }

    fun replaceScreen(screenKey: String, data: Any? = Bundle()) = executeCommands(Replace(screenKey, data))

    fun newScreenChain(screenKey: String, data: Any? = Bundle()) {
        executeCommands(BackTo(null), Forward(screenKey, data))
    }

    fun newRootScreen(screenKey: String, data: Any? = Bundle()) {
        executeCommands(BackTo(null), Replace(screenKey, data))
        onNewRootScreenListener(screenKey)
    }

    fun exit() {
        onBackScreenListener()
        executeCommands(Back())
    }

    fun exitWithMessage(message: String) {
        executeCommands(Back(), SystemMessage(message))
    }
}

