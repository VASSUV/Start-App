package ru.vassuv.startapp.screen.isold.intro

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.vassuv.startapp.utils.atlibrary.Api
import ru.vassuv.startapp.utils.routing.Router

fun auth(viewState: IntroView): Job = launch(UI) {
    try {
        Router.uiListener.showLoader()

        val (result, error) = Api.AUTH.responseJson(isCheckError = true)
        val (status, message) = error

        if (status > 0 && message != null)
            Router.uiListener.showMessage(message)

    } finally {
        Router.uiListener.hideLoader()
    }
}