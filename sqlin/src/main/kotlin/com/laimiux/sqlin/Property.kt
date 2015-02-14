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

    override fun valueToString(value: Long?): String {
        return value.toString()
    }
}

object TEXT: TypeProperty<String>("text") {
    override fun toContentValues(values: ContentValues, columnName: String, value: String?) {
        values.put(columnName, value)
    }

    override fun fromCursor(index: Int, cursor: Cursor): String {
        return cursor.getString(index)
    }

    override fun valueToString(value: String?): String {
        return value.toString()
    }
}

object BOOLEAN: TypeProperty<Boolean>("integer") {
    override fun toContentValues(values: ContentValues, columnName: String, value: Boolean?) {
        values.put(columnName, if(value != null && value) 1 else 0)
    }

    override fun fromCursor(index: Int, cursor: Cursor): Boolean {
        return cursor.getInt(index) == 1
    }

    override fun valueToString(value: Boolean?): String {
        if(value != null && value)
            return "1"
        else
            return "0"
    }
}

object DATE: TypeProperty<Date>("text") {
    override fun toContentValues(values: ContentValues, columnName: String, value: Date?) {
        values.put(columnName, encode(value))
    }

    override fun fromCursor(index: Int, cursor: Cursor): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val string: String = cursor.getString(index)
        return dateFormat.parse(string)
    }

    private fun encode(date: Date?): String? {
        val dateString: String? =
                if (date == null) null
                else {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    dateFormat.format(date)
                }

        return dateString
    }

    override fun valueToString(value: Date?): String {
        return encode(value).toString()
    }
}

public abstract class TypeProperty<T>(override val definition: String): Property {
    abstract fun toContentValues(values: ContentValues, columnName: String, value: T?)
    abstract fun fromCursor(index : Int, cursor: Cursor): T
    abstract fun valueToString(value: T?): String
}

public enum class DBProperties(override val definition: String) : Property {
    PRIMARY_KEY : DBProperties("primary key")
    NOT_NULL : DBProperties("not null")
}