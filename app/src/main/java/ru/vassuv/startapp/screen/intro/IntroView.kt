package ru.vassuv.startapp.screen.intro

import com.arellomobile.mvp.MvpView

interface IntroView : MvpView {
    fun setText(text: String)
}
