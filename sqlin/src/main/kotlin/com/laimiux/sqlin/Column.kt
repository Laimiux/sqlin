package com.laimiux.sqlin

import android.database.Cursor
import android.content.ContentValues

public trait Column<T: Any> {
    public fun getColumnName(): String
    val type: TypeProperty<T>
    public fun getProperties(): Array<out Property>


    public fun getValue(cursor: Cursor): T? {
        val index = getColumnIndex(cursor)

        // Make sure anything exists
        if(cursor.isNull(index)) {
            return null
        }

        return this.type.fromCursor(index, cursor)
    }

    private fun getColumnIndex(cursor: Cursor): Int {
        return cursor.getColumnIndexOrThrow(this.getColumnName())
    }

    public fun putValue(values: ContentValues, value: T?) {
        type.toContentValues(values, getColumnName(), value)
    }
}