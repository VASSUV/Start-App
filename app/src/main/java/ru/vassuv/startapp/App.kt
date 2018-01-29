package ru.vassuv.startapp

import android.app.Application
import android.content.Context
import ru.terrakok.cicerone.Cicerone
import ru.vassuv.startapp.utils.atlibrary.Logger
import ru.vassuv.startapp.utils.routing.Navigator
import ru.vassuv.startapp.utils.routing.Router

class App(val cicerone: Cicerone<Router> = Cicerone.create(Router)) : Application() {

    companion object {
        private lateinit var app: App

        val context: Context
            get() = app.applicationContext

        fun log(vararg args: Any?) = Logger.trace(args)
        fun logExc(text: String, exception: Throwable) = Logger.traceException(text, exception)
        fun setNavigationHolder(navigator: Navigator?) = app.cicerone.navigatorHolder.setNavigator(navigator)
        fun resetNavigator() = app.cicerone.navigatorHolder.removeNavigator()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}