package ru.vassuv.startapp.utils.atlibrary

import android.support.v4.view.ViewCompat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric


abstract class BaseFragment : MvpAppCompatFragment() {

    fun onBackPressed() {
        Router.exit()
    }

    abstract val type: FrmFabric

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (nextAnim == R.anim.enter_from_right) {
            val nextAnimation = AnimationUtils.loadAnimation(context, nextAnim)
            nextAnimation.setAnimationListener(object : Animation.AnimationListener {
                private var startZ = 0f


                override fun onAnimationStart(animation: Animation) {
                    view?.let { view ->
                        startZ = ViewCompat.getTranslationZ(view)
                        ViewCompat.setTranslationZ(view, startZ + incZValue ++)
                    }
                }


                override fun onAnimationEnd(animation: Animation) {
                    view?.postDelayed({
                        if (view != null) {
//                            ViewCompat.setTranslationZ(view!!, startZ)
                        }
                    }, 100)
                }

                override fun onAnimationRepeat(animation: Animation) = Unit
            })
            return nextAnimation
        } else {
            return null
        }
    }

    companion object {

        private var incZValue = 1;
    }
}