package com.laimiux.sqlin

import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import android.content.ContentValues
import java.util.ArrayList
import java.util.HashMap

/**
 * Dao class that provides query functionality
 */
public class Dao<T>(val database: SQLiteDatabase, val table: Table<T>) {

    fun save(item: T): Long {
        val values = ContentValues()
        table.converter.toContentValues(values, item)
        return database.insertWithOnConflict(table.tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getAll(): List<T> {
        val cursor = database.query(table.tableName, table.getColumnNames(), null, null, null, null, null)
        return getAll(cursor)
    }

    private fun getAll(cursor: Cursor): List<T> {
        val items = ArrayList<T>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            val item = table.converter.toItem(cursor)
            items.add(item)
            cursor.moveToNext()
        }

        cursor.close()
        return items
    }

    fun query(): QueryBuilder {
        return QueryBuilder()
    }

    public inner class QueryBuilder {
        private var orderList: MutableList<String>? = null
        private var whereMap: MutableMap<String, String>? = null


        fun <T> orderBy(column: Column<T>, order: Order): QueryBuilder {
            if(orderList == null) {
                orderList = ArrayList()
            }

            orderList!!.add("${column.getColumnName()} ${order.name}")
            return this
        }

        fun <K> equals(column: Column<K>, value: Any): QueryBuilder {
            if(whereMap == null) {
                whereMap = HashMap()
            }

            whereMap!!.put("${column.getColumnName()} = ?", value.toString())

            return this
        }

        fun run(): List<T> {
            val orderBy: String? =
                    if(orderList == null) null
                    else orderList!!.reduce { (computed, s) -> computed + "," }


            var (selection: String?, selectionArgs: Array<String>?) = fromWhereMap()

            val cursor = database.query(table.tableName, table.getColumnNames(), selection, selectionArgs, null, null, orderBy)
            return getAll(cursor)
        }

        private fun fromWhereMap(): Pair<String?, Array<String>?> {
            if(whereMap == null) {
                return Pair<String?, Array<String>?>(null, null)
            }

            var selection: String = whereMap!!.keySet().reduce{(computed, s) -> "${computed} and ${s}"}
            var selectionArgs = whereMap!!.values().copyToArray()


            return Pair(selection, selectionArgs)
        }
    }
}