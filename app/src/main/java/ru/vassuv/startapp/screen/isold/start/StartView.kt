package ru.vassuv.startapp.screen.isold.start

import android.transition.Transition
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface StartView : MvpView {
    fun setText(text: String)
    fun setEnterTransition(transition: Transition)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSharedNameByPosition(name: String, pos: Int)
}
