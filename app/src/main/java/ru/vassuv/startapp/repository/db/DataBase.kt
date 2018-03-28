package ru.vassuv.startapp.repository.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import ru.vassuv.startapp.App

private val CURRENT_VERSION: Int = 20170101
private val dbName: String = "startapp.db"

val dbHelper = object : ManagedSQLiteOpenHelper(App.context, dbName, null, CURRENT_VERSION){

        override fun onCreate(db: SQLiteDatabase) {
            db.createTable("user", false,
                    "id" to INTEGER + PRIMARY_KEY,
                    "name" to TEXT,
                    "age" to INTEGER)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        }
}

fun SQLiteDatabase.raw(name: String, whereSelection: String, vararg columns: String): Cursor {
   return rawQuery("select ${if(columns.isEmpty()) "*" else columns.joinToString()} from $name $whereSelection", null)
}