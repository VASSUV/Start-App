package ru.vassuv.startapp.activity.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.FragmentManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.vassuv.processor.FrmFabric
import ru.vassuv.router.Navigator
import ru.vassuv.router.Router
import ru.vassuv.router.removeNavigator
import ru.vassuv.router.setNavigator
import ru.vassuv.startapp.App
import ru.vassuv.startapp.R
import ru.vassuv.startapp.screen.BaseFragment
import ru.vassuv.startapp.utils.UiListener
import java.io.Serializable

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private var navigator: Navigator? = null
    private var fragmentManager: FragmentManager? = null
    private var currentType: String = ""

    private val changedFragmentListener = { setScreenState() }

    private val currentFragment: BaseFragment?
        get() = fragmentManager?.findFragmentById(R.id.fragment_container) as BaseFragment?

    fun onCreate(fragmentManager: FragmentManager, savedInstanceState: Bundle?, uiListener: UiListener) {
        this.fragmentManager = fragmentManager

        initRouter(uiListener)

        navigator = createNavigator(fragmentManager)

        App.uiListener = uiListener

        if (savedInstanceState == null) {
            onRunApplication()
        } else {
            navigator?.setScreenNames(savedInstanceState.getSerializable(STATE_SCREEN_NAMES) as ArrayList<*>)
        }
    }

    private fun onRunApplication() {
        Router.newRootScreen(FrmFabric.SPLASH)
    }

    private fun initRouter(uiListener: UiListener) {
        Router.onNewRootScreenListener = { screenKey ->
        }

        Router.onBackScreenListener = { }

    }

    private fun createNavigator(fragmentManager: FragmentManager): Navigator {
        return object : Navigator(fragmentManager, R.id.fragment_container, changedFragmentListener) {

            override fun createFragment(screenKey: String, data: Bundle) = FrmFabric.createFragment(screenKey, data)

            override fun exit() {
            }

            override fun openFragment(position: Int, name: String) {
                if (position == 0) viewState.hideBackButton() else viewState.showBackButton()
            }
        }
    }

    val onNavigationItemSelectedListener
        get() = OnNavigationItemSelectedListener { item ->

            fun startScreen(name: String): Boolean {
                if (name != currentType)
                    Router.newRootScreen(name)
                return true
            }

            when (item.itemId) {
//                R.id.navigation_home -> startScreen(FrmFabric.START.name)
//                R.id.navigation_dashboard -> startScreen(FrmFabric.SPLASH.name)
//                R.id.navigation_notifications -> startScreen(FrmFabric.INTRO.name)
                else -> false
            }
        }

    fun onStart() {
        setScreenState()
    }

    fun onResume() {
        setNavigator(navigator)
    }

    fun onPause() {
        removeNavigator()
    }

    private fun setScreenState() {
        val fragment: BaseFragment = currentFragment ?: return

        val newType = FrmFabric.valueOf(fragment)

        if (currentType == newType) return

        currentType = newType

        viewState.setTitle(currentType)
        when (currentType) {
            FrmFabric.SPLASH -> {
                viewState.hideBottomNavigatorView()
                viewState.hideActionBar()
            }
            FrmFabric.INTRO -> {
                viewState.hideBottomNavigatorView()
                viewState.hideActionBar()
            }
            else -> {
                viewState.hideBottomNavigatorView()
                viewState.hideActionBar()
                viewState.hideBackButton()

//                viewState.changeTab(R.id.navigation_dashboard)
            }
        }
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
