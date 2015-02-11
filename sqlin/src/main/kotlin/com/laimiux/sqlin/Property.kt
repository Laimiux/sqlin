package com.laimiux.sqlin

import android.database.Cursor
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import android.content.ContentValues


public trait Property {
    val definition: String
}

object INTEGER: TypeProperty<Long>("integer") {
    override fun toContentValues(values: ContentValues, columnName: String, value: Long?) {
        values.put(columnName, value)
    }

    override fun fromCursor(index: Int, cursor: Cursor): Long {
        return cursor.getLong(index)
    }
}

object TEXT: TypeProperty<String>("text") {
    override fun toContentValues(values: ContentValues, columnName: String, value: String?) {
        values.put(columnName, value)
    }

    override fun fromCursor(index: Int, cursor: Cursor): String {
        return cursor.getString(index)
    }
}

object BOOLEAN: TypeProperty<Boolean>("integer") {
    override fun toContentValues(values: ContentValues, columnName: String, value: Boolean?) {
        values.put(columnName, value)
    }

    override fun fromCursor(index: Int, cursor: Cursor): Boolean {
        return cursor.getInt(index) == 1
    }
}

object DATE: TypeProperty<Date>("text") {
    override fun toContentValues(values: ContentValues, columnName: String, value: Date?) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val format = dateFormat.format(value)
        values.put(columnName, format)
    }

    override fun fromCursor(index: Int, cursor: Cursor): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.parse(cursor.getString(index))
    }
}

public abstract class TypeProperty<T>(override val definition: String): Property {
    abstract fun toContentValues(values: ContentValues, columnName: String, value: T?)
    abstract fun fromCursor(index : Int, cursor: Cursor): T
}

public enum class DBProperties(override val definition: String) : Property {
    PRIMARY_KEY : DBProperties("primary key")
    NOT_NULL : DBProperties("not null")
}