package ru.vassuv.router

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.commands.*
import ru.vassuv.startapp.utils.routing.animate.AnimateForward

fun setNavigator(navigator: Navigator?) {
    Router.cicerone.navigatorHolder.setNavigator(navigator)
}

fun removeNavigator() {
    Router.cicerone.navigatorHolder.removeNavigator()
}

object Router: BaseRouter() {

    internal var cicerone: Cicerone<Router> = Cicerone.create(Router)

    lateinit var onNewRootScreenListener: ((screenKey: String) -> Unit)
    lateinit var onBackScreenListener: () -> Unit

    fun backTo(screenKey: String) = executeCommands(BackTo(screenKey))

    fun navigateTo(screenKey: String, data: Any? = Bundle()) = executeCommands(Forward(screenKey, data))

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun navigateToWithAnimate(screenKey: String,  animate: FragmentTransaction.() -> Unit) {
        executeCommands(AnimateForward(screenKey, Bundle(), animate))
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

