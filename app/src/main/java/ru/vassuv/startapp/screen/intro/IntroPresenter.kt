package ru.vassuv.startapp.screen.intro

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.vassuv.router.Router
import ru.vassuv.startapp.fabric.FrmFabric

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
