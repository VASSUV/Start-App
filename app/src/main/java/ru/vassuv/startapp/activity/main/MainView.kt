package ru.vassuv.startapp.activity.main

import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun changeTab(position: Int)
    fun hideBottomNavigator()
}
