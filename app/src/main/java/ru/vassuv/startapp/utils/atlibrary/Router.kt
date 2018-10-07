package ru.vassuv.startapp.utils.atlibrary

import android.os.Bundle
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.*
import ru.vassuv.startapp.fabric.FrmFabric

object Router: BaseRouter() {
    lateinit var onNewRootScreenListener: ((screenKey: String) -> Unit)
    lateinit var onBackScreenListener: () -> Unit

    fun navigateTo(screenKey: String) = navigateTo(screenKey, Bundle())
    fun replaceScreen(screenKey: String) = replaceScreen(screenKey, Bundle())
    fun newScreenChain(screenKey: String) = newScreenChain(screenKey, Bundle())
    fun newRootScreen(screenKey: String) = newRootScreen(screenKey, Bundle())
    fun backTo(screenKey: String) = executeCommands(BackTo(screenKey))

    fun navigateTo(screenKey: String, data: Any?) = executeCommands(Forward(screenKey, data))

    fun replaceScreen(screenKey: String, data: Any?) = executeCommands(Replace(screenKey, data))

    fun newScreenChain(screenKey: String, data: Any?) {
        executeCommands(BackTo(null), Forward(screenKey, data))
    }

    fun newRootScreen(screenKey: String, data: Any?) {
        executeCommands(BackTo(null), Replace(screenKey, data))
        onNewRootScreenListener(screenKey)
    }

    fun exit() {
        onBackScreenListener()
        executeCommands(Back())
    }
}