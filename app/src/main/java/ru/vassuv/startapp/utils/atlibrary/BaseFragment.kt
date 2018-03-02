package ru.vassuv.startapp.utils.atlibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.vassuv.startapp.App
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.routing.Router


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