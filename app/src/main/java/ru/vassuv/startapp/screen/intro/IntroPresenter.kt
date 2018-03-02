package ru.vassuv.startapp.screen.intro

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.routing.Router

@InjectViewState
class IntroPresenter : MvpPresenter<IntroView>() {
    fun loginClick() {
        Router.navigateTo(FrmFabric.LOGIN.name)
    }

    fun myProjectClick() {
        Router.navigateTo(FrmFabric.SPLASH.name)
    }

    fun demoClick() {
        Router.navigateTo(FrmFabric.SPLASH.name)
    }
}
