package ru.vassuv.startapp.screen.isold.test1

import android.animation.ValueAnimator
import android.widget.TextView
import android.view.ViewGroup
import android.animation.Animator
import android.animation.ArgbEvaluator
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.support.transition.Transition
import android.support.transition.TransitionValues
import android.util.AttributeSet


@TargetApi(Build.VERSION_CODES.KITKAT)
class TextColorTransition : Transition {

    override fun getTransitionProperties() : Array<String> = TRANSITION_PROPERTIES

    constructor()

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(transitionValues: TransitionValues) {
        if (transitionValues.view is TextView) {
            val color = (transitionValues.view as TextView).currentTextColor
            transitionValues.values.put(PROPNAME_TEXT_COLOR, color)
        }
    }

    override  fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?,
                        endValues: TransitionValues?): Animator? {
        if (startValues == null || endValues == null) {
            return null
        }

        val startTextColor = startValues.values[PROPNAME_TEXT_COLOR] as Int
        val endTextColor = endValues.values[PROPNAME_TEXT_COLOR] as Int
        val textView = endValues.view as TextView
        val argbEvaluator = ArgbEvaluator()

        val animator = ValueAnimator.ofFloat(0f, 1.0f)
        animator.addUpdateListener { animation ->
            textView.setTextColor(argbEvaluator.evaluate(animation.animatedFraction, startTextColor, endTextColor) as Int)
        }

        return animator
    }

    companion object {
        private val PROPNAME_TEXT_COLOR = "kvest:textColorTransition:textColor"
        private val TRANSITION_PROPERTIES = arrayOf(PROPNAME_TEXT_COLOR)
    }
}
