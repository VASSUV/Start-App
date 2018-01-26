package ru.vassuv.startapp.utils.atlibrary

import com.arellomobile.mvp.MvpAppCompatFragment
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.Router


abstract class BaseFragment: MvpAppCompatFragment() {

    fun onBackPressed() {
        Router.exit()
    }

    abstract val type: FrmFabric
}