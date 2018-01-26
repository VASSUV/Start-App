package ru.vassuv.startapp.screen.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_intro.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



class IntroFragment : BaseFragment(), IntroView {
    override val type = FrmFabric.INTRO

    companion object {

        fun newInstance(args: Bundle): IntroFragment {
            val fragment: IntroFragment = IntroFragment()
            fragment.arguments = args
            return fragment
        }
    }
    @InjectPresenter
    lateinit var presenter: IntroPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        // get the center for the clipping circle
        val cx = (myView.getLeft() + myView.getRight()) * 15
        val cy = (myView.getTop() + myView.getBottom()) * 15

// get the final radius for the clipping circle
        val finalRadius = Math.max(myView.getWidth(), myView.getHeight())

// create the animator for this view (the start radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 90f, finalRadius.toFloat())

// make the view visible and start the animation
        myView.setVisibility(View.VISIBLE)
        anim.start()
    }

    override fun setText(text: String) {
//        textView.text = text
    }
}
