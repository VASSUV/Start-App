package ru.vassuv.startapp.screen

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.vassuv.router.Router
import ru.vassuv.startapp.App


abstract class BaseFragment: MvpAppCompatFragment() {

    private val CLASS_NAME = this.javaClass.simpleName

    fun onBackPressed() {
        Router.exit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.log("♠♠♠ $CLASS_NAME ♠♠♠:  onViewCreated()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        App.log("♠♠♠ $CLASS_NAME ♠♠♠: onDestroyView()")
    }
}