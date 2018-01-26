package ru.vassuv.startapp.utils

interface UiListener {
    fun showMessage(message: String)
    fun showLoader()
    fun hideLoader()
}