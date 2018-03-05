package ru.vassuv.startapp.screen.isold.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_splash.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.screen.BaseFragment

class SplashFragment : BaseFragment(), SplashView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setText(text: String) {
        textView.text = text
    }
}
