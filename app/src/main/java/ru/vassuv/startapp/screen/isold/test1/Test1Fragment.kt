package ru.vassuv.startapp.screen.isold.test1

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_test1.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment
import ru.vassuv.startapp.utils.routing.Router
import java.util.*
import android.os.Build
import android.transition.TransitionInflater
import android.view.*
import android.widget.CheckedTextView
import android.widget.CompoundButton
import android.widget.RadioButton
import com.transitionseverywhere.*
import com.transitionseverywhere.extra.Scale
import org.jetbrains.anko.bundleOf


class Test1Fragment : BaseFragment(), Test1View {

    @InjectPresenter
    lateinit var presenter: Test1Presenter

    private var transitionName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionName = arguments?.getString("SHARED_NAME", "") ?: return
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_test1, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            inflate.findViewById<View>(R.id.button3).transitionName = transitionName
        }

        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExample1()
        initExample2()
        initExample3()
        initExample4()
    }

    private fun initExample4() {
        button4Show.setOnClickListener{
            Router.uiListener.showLoader()
        }
        button4Hide.setOnClickListener{
            Router.uiListener.hideLoader()
        }
    }

    private fun initExample3() {

        button3.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val name = ViewCompat.getTransitionName(button3)
                Router.navigateToWithAnimate(FrmFabric.SPLASH.name, bundleOf("SHARED_NAME" to name)) {
                    addSharedElement(button3, name)
                }
            } else {
                Router.navigateTo(FrmFabric.SPLASH.name)
            }
        }
    }

    private fun initExample2() {
        button2Left.setOnClickListener {
            animate2()
        }
        button2Right.setOnClickListener {
            animate2()
        }
    }

    private fun animate2() {
        TransitionManager.beginDelayedTransition(container2, TransitionSet()
                .addTransition(Slide())
                .addTransition(Scale())
                .addTransition(Recolor()))
        button2Right.invertVisibility()
        val random = Random()
        button2Right.setTextColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)))

        TransitionManager.beginDelayedTransition(container2, TransitionSet()
                .addTransition(Slide())
                .addTransition(Scale())
                .addTransition(Recolor()))
        button2Left.invertVisibility()
        button2Left.setTextColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)))
    }

    private fun initExample1() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            var ax = 0
            var ay = 0
            var cx = 0
            var cy = 0
            var animShow: Animator? = null
            var animHide: Animator? = null
            var isFirst = true
            var finalRadius = 0
            var initialRadius = 0

            val listener = object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    ((if (isFirst) button1 else buttonExample1) as View?)?.visibility = View.INVISIBLE
                }
            }

            fun hideAnimate(view: View, x: Int, y: Int) = ViewAnimationUtils
                    .createCircularReveal(view, x, y, initialRadius.toFloat(), 0f)
                    .apply {
                        addListener(listener)
                        start()
                    }

            fun showAnimate(view: View, x: Int, y: Int) = ViewAnimationUtils
                    .createCircularReveal(view, x, y, 0f, finalRadius.toFloat())
                    .apply {
                        addListener(listener)
                        start()
                    }

            Handler(Looper.getMainLooper()).post {
                ax = (button1.left + button1.right) / 2
                ay = (button1.bottom - button1.top) / 2
                cx = (buttonExample1.left + buttonExample1.right) / 2
                cy = (buttonExample1.bottom - buttonExample1.top) / 2

                finalRadius = Math.max(buttonExample1.width, buttonExample1.height)
                initialRadius = buttonExample1.width
            }

            buttonExample1.setOnTouchListener { v, event ->
                cx = event.x.toInt()
                cy = event.y.toInt()
                animHide = hideAnimate(v, cx, cy)
                animShow = showAnimate(button1, ax, ay)
                isFirst = false
                button1.visibility = View.VISIBLE
                return@setOnTouchListener true
            }
            button1.setOnTouchListener { v, event ->
                ax = event.x.toInt()
                ay = event.y.toInt()
                animHide = hideAnimate(v, ax, ay)
                animShow = showAnimate(buttonExample1, cx, cy)
                isFirst = true
                buttonExample1.visibility = View.VISIBLE
                return@setOnTouchListener true
            }
        } else {
            buttonExample1.setOnTouchListener { v, event ->
                button1.visibility = View.VISIBLE
                buttonExample1.visibility = View.INVISIBLE
                return@setOnTouchListener true
            }
            button1.setOnTouchListener { v, event ->
                buttonExample1.visibility = View.VISIBLE
                button1.visibility = View.INVISIBLE
                return@setOnTouchListener true
            }
        }
    }
}

fun View.invertVisibility() {
    visibility = if (visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
}