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

    fun backTo(screenKey: String) = executeCommand(BackTo(screenKey))

    fun navigateTo(screenKey: String, data: Any? = Bundle()) = executeCommand(Forward(screenKey, data))

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun navigateToWithAnimate(screenKey: String,  animate: FragmentTransaction.() -> Unit) {
        executeCommand(AnimateForward(screenKey, Bundle(), animate))
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun navigateToWithAnimate(screenKey: String, data: Any?, animate: FragmentTransaction.() -> Unit) {
        executeCommand(AnimateForward(screenKey, data, animate))
    }

    fun replaceScreen(screenKey: String, data: Any? = Bundle()) = executeCommand(Replace(screenKey, data))

    fun newScreenChain(screenKey: String, data: Any? = Bundle()) {
        executeCommand(BackTo(null))
        executeCommand(Forward(screenKey, data))
    }

    fun newRootScreen(screenKey: String, data: Any? = Bundle()) {
        executeCommand(BackTo(null))
        executeCommand(Replace(screenKey, data))
        onNewRootScreenListener(screenKey)
    }

    fun exit() {
        onBackScreenListener()
        executeCommand(Back())
    }

    fun exitWithMessage(message: String) {
        executeCommand(Back())
        executeCommand(SystemMessage(message))
    }
}

