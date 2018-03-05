package ru.vassuv.startapp.screen.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vassuv.startapp.R
import ru.vassuv.startapp.screen.BaseFragment

import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_intro.*
import ru.vassuv.processor.annotation.Route
import ru.vassuv.processor

@Route
class IntroFragment : BaseFragment(), IntroView {
    @InjectPresenter
    lateinit var presenter: IntroPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener { presenter.loginClick() }
        myProjectButton.setOnClickListener { presenter.myProjectClick() }
        demoButton.setOnClickListener { presenter.demoClick() }

        FrmFabric
    }

}
