package ru.vassuv.startapp.utils.atlibrary

import android.content.SharedPreferences
import android.preference.PreferenceManager
import ru.vassuv.startapp.App

private val instance: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(App.context) }

interface ISharedData {
    val nameData: String

    fun getString() = instance.getString(nameData, "")
    fun getInt() = instance.getInt(nameData, 0)
    fun getBoolean() = instance.getBoolean(nameData, false)
    fun getLong() = instance.getLong(nameData, 0)

    fun saveString(value: String) = instance.edit().putString(nameData, value).apply()
    fun saveInt(value: Int) = instance.edit().putInt(nameData, value).apply()
    fun saveBoolean(value: Boolean) = instance.edit().putBoolean(nameData, value).apply()
    fun saveLong(value: Long) = instance.edit().putLong(nameData, value).apply()

    fun remove() = instance.edit().remove(nameData).apply()
}

enum class SharedData : ISharedData{
    LOGIN;

    override val nameData: String = name
}