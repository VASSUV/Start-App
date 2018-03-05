package ru.vassuv.startapp

import android.app.Application
import android.content.Context
import ru.vassuv.startapp.utils.UiListener
import ru.vassuv.startapp.utils.atlibrary.Logger

class App : Application() {

    companion object {
        private lateinit var app: App

        val context: Context
            get() = app.applicationContext

        fun log(vararg args: Any?) = Logger.trace(args)
        fun logExc(text: String, exception: Throwable) = Logger.traceException(text, exception)

        var uiListener: UiListener? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}