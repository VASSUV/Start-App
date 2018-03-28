package ru.vassuv.startapp.screen.intro

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.vassuv.processor.FrmFabric
import ru.vassuv.router.Router

@InjectViewState
class IntroPresenter : MvpPresenter<IntroView>() {
    fun loginClick() {
        Router.navigateTo(FrmFabric.LOGIN)
    }

    fun myProjectClick() {
        Router.navigateTo(FrmFabric.START)
    }

    fun demoClick() {
        Router.navigateTo(FrmFabric.TEST_3)
    }
}
