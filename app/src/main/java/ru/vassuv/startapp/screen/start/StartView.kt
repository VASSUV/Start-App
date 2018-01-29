package ru.vassuv.startapp.screen.start

import android.transition.Transition
import com.arellomobile.mvp.MvpView

interface StartView : MvpView {
    fun setText(text: String)
    fun setEnterTransition(transition: Transition)

}
