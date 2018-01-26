package ru.vassuv.startapp.activity.main

import android.os.Bundle
import android.os.Message
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity

import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.utils.UiListener

import ru.vassuv.startapp.utils.atlibrary.BaseFragment

class MainActivity : MvpAppCompatActivity(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter

    private val uiListener: UiListener
        get() = object : UiListener {
//            val snackBar = Snackbar(container)

            override fun showMessage(message: String) {
                Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
            }

            override fun showLoader() {
                progress.visibility = View.VISIBLE
            }

            override fun hideLoader() {
                progress.visibility = View.GONE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate(supportFragmentManager, savedInstanceState, uiListener)
        navigation.setOnNavigationItemSelectedListener(presenter.onNavigationItemSelectedListener)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }


    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment != null && fragment is BaseFragment) {
            fragment.onBackPressed()
        } else {
            super.onBackPressed()
        }

        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    fun showBottomNavigationView() {
        navigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView() {
        navigation.visibility = View.GONE
    }

    fun setActivateMenuItem(@IdRes menuItemId: Int) {
        navigation.menu.findItem(menuItemId).isChecked = true
    }

    override fun changeTab(itemId: Int) {
        navigation.selectedItemId = itemId
    }
}
