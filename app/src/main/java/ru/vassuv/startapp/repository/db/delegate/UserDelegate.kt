package ru.vassuv.startapp.repository.db.delegate

import android.content.ContentValues
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.transaction
import ru.vassuv.startapp.repository.StageData
import ru.vassuv.startapp.repository.dataclass.User
import ru.vassuv.startapp.repository.db.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class UserDelegate : ReadWriteProperty<StageData, User?> {
    override fun getValue(thisRef: StageData, property: KProperty<*>): User? {
        val cursor = dbHelper.writableDatabase.raw(USER, "$ID=0", NAME, AGE )
        if (!cursor.moveToFirst()) return null

        val user = User (cursor.getString(cursor.getColumnIndex(NAME)),
                cursor.getInt(cursor.getColumnIndex(AGE)))
        cursor.close()
        return user
    }

    override fun setValue(thisRef: StageData, property: KProperty<*>, value: User?) {
        if (value == null) return
        dbHelper.writableDatabase.transaction {
           if(raw(USER, "$ID=0").count == 0) {
               insert(USER,
                       ID to 0,
                       NAME to value.name,
                       AGE to value.age)
           } else {
               val contentValues = ContentValues()
               contentValues.put(NAME, value.name)
               contentValues.put(AGE, value.age)
               update(USER, contentValues, "$ID=0", null)
           }
        }
    }
}

