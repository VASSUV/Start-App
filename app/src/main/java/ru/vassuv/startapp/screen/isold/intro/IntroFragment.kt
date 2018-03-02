package ru.vassuv.startapp.screen.isold.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_intro.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

class IntroFragment : BaseFragment(), IntroView {

    @InjectPresenter
    lateinit var presenter: IntroPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }
}
