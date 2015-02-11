package com.laimiux.sqlin

import android.content.ContentValues
import android.database.Cursor
import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

public trait DaoConverter<T> {
    fun toContentValues(values: ContentValues, item: T)

    fun toItem(cursor: Cursor): T

    fun ContentValues.put(columnName: String, date: Date) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val format = dateFormat.format(date)

        this.put(columnName, format)
    }

    fun <T> ContentValues.put(column: Column<T>, value: T?) {
        column.putValue(this, value)
    }
}