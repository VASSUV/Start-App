package ru.vassuv.startapp.screen.isold.intro

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.vassuv.startapp.utils.atlibrary.Api
import ru.vassuv.startapp.App

fun auth(viewState: IntroView): Job = launch(UI) {
    try {
        App.uiListener?.showLoader()

        val (result, error) = Api.AUTH.responseJson(isCheckError = true)
        val (status, message) = error

        if (status > 0 && message != null)
            App.uiListener?.showMessage(message)

    } finally {
        App.uiListener?.hideLoader()
    }
}