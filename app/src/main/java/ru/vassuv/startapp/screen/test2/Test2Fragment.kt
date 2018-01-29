package ru.vassuv.startapp.screen.test2

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vassuv.startapp.R
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_test2.*
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.routing.animate.DetailsTransition


class Test2Fragment : BaseFragment(), Test2View {
    override val type: FrmFabric
        get() = FrmFabric.TEST2

    companion object {
        fun newInstance(args: Bundle): Test2Fragment {
            val fragment = Test2Fragment()
            fragment.arguments = args
            return fragment
        }
    }

    init {
    }

    @InjectPresenter
    lateinit var presenter: Test2Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test2, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            onBackPressed()
        }
    }
}