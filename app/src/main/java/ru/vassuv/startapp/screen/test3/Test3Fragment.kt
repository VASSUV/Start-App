package ru.vassuv.startapp.screen.test3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vassuv.startapp.R

import com.arellomobile.mvp.presenter.InjectPresenter
import ru.vassuv.processor.annotation.Route
import ru.vassuv.startapp.screen.BaseFragment

@Route
class Test3Fragment : BaseFragment(), Test3View {

    @InjectPresenter
    lateinit var presenter: Test3Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test3, container, false)
    }
}
