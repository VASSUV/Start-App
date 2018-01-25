package ru.vassuv.startapp.utils.atlibrary

import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import ru.vassuv.startapp.App

abstract class Helper(val CURRENT_VERSION: Int, val bdName: String)
    : ManagedSQLiteOpenHelper(App.context, bdName, null, CURRENT_VERSION)

open class DataBase(var dbHelper: Helper)