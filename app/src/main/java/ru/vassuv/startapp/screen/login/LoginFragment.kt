package ru.vassuv.startapp.screen.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vassuv.startapp.R
import ru.vassuv.startapp.screen.login.LoginView
import ru.vassuv.startapp.screen.login.LoginPresenter
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

import com.arellomobile.mvp.presenter.InjectPresenter

class LoginFragment : BaseFragment(), LoginView {
    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}