package ru.vassuv.startapp.utils.atlibrary

import android.os.Bundle
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.*

object Router: BaseRouter() {
    lateinit var onNewRootScreenListener: ((screenKey: String) -> Unit)
    lateinit var onBackScreenListener: () -> Unit

    fun navigateTo(screenKey: String) = navigateTo(screenKey, Bundle())
    fun replaceScreen(screenKey: String) = replaceScreen(screenKey, Bundle())
    fun newScreenChain(screenKey: String) = newScreenChain(screenKey, Bundle())
    fun newRootScreen(screenKey: String) = newRootScreen(screenKey, Bundle())
    fun backTo(screenKey: String) = executeCommand(BackTo(screenKey))

    fun navigateTo(screenKey: String, data: Any?) = executeCommand(Forward(screenKey, data))

    fun replaceScreen(screenKey: String, data: Any?) = executeCommand(Replace(screenKey, data))

    fun newScreenChain(screenKey: String, data: Any?) {
        executeCommand(BackTo(null))
        executeCommand(Forward(screenKey, data))
    }

    fun newRootScreen(screenKey: String, data: Any?) {
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