package ru.vassuv.startapp.screen.intro

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface IntroView : MvpView {
    fun setText(text: String)
}
