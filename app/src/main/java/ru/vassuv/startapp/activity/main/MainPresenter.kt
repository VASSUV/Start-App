package ru.vassuv.startapp.activity.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.vassuv.startapp.App
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment
import ru.vassuv.startapp.utils.atlibrary.Navigator
import ru.vassuv.startapp.utils.atlibrary.Router
import java.io.Serializable

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private var navigator: Navigator? = null
    private var fragmentManager: FragmentManager? = null
    private var currentType = FrmFabric.EMPTY

    private val changedFragmentListener = { setScreenState() }

    val onNavigationItemSelectedListener
        get() = OnNavigationItemSelectedListener { item ->

            fun startScreen(name: String): Boolean {
                if(name != currentType.name)
                    Router.newRootScreen(name)
                return true
            }

            when (item.itemId) {
                R.id.navigation_home -> startScreen(FrmFabric.START.name)
                R.id.navigation_dashboard -> startScreen(FrmFabric.SPLASH.name)
                R.id.navigation_notifications -> startScreen(FrmFabric.INTRO.name)
                else -> false
            }
        }


    private val currentFragment: BaseFragment?
        get() = fragmentManager?.findFragmentById(R.id.fragment_container) as BaseFragment?

    fun onStart() {
        setScreenState()
    }

    private fun setScreenState() {
        val fragment = currentFragment

        if (fragment === null || currentType === fragment.type) return

        currentType = fragment.type

        when (currentType) {
            FrmFabric.START -> {
            }
            FrmFabric.INTRO -> {
            }
            FrmFabric.SPLASH -> {
            }
        }
    }

    fun onCreate(fragmentManager: FragmentManager, savedInstanceState: Bundle?) {
        this.fragmentManager = fragmentManager

        Router.onNewRootScreenListener = { screenKey ->
        }

        Router.onBackScreenListener = { }

        navigator = object : Navigator(fragmentManager, R.id.fragment_container, changedFragmentListener) {
            override fun createFragment(screenKey: String, data: Bundle): Fragment? {
                return FrmFabric.valueOf(screenKey).create(data)
            }

            override fun showSystemMessage(message: String) {
            }

            override fun exit() {
            }

            override fun openFragment(name: String) {
            }
        }

        if (savedInstanceState == null) {
            onRunApplication()
        } else {
            navigator?.setScreenNames(savedInstanceState.getSerializable(STATE_SCREEN_NAMES) as ArrayList<*>)
        }
    }

    private fun onRunApplication() {
        viewState.changeTab(R.id.navigation_home)
    }

    fun onResume() {
        App.setNavigationHolder(navigator)
    }

    fun onPause() {
        App.resetNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentManager = null
    }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(STATE_SCREEN_NAMES, navigator?.screenNames as Serializable?)
    }

    companion object {
        private val STATE_SCREEN_NAMES = "state_screen_names"
    }
}
