package com.laimiux.sqlin

import android.database.sqlite.SQLiteDatabase

object DatabaseFunctions {
    final fun createTable(db: SQLiteDatabase, table: Table<out Any>) {
        db.execSQL(getTableCreationQuery(table))
    }

    final fun dropTable(db: SQLiteDatabase, table: Table<out Any>) {
        db.execSQL("drop table if exists " + table.tableName)
    }

    private final fun getTableCreationQuery(table: Table<out Any>): String {
        return "create table if not exists " + table.tableName + " (" + createPropertyString(table.columns) + " );"
    }

    private final fun createPropertyString(columns: Array<out Column<out Any>>): String {
        val builder = StringBuilder()

        for (i in columns.indices) {
            val column = columns[i]

            builder.append(column.getColumnName())
            builder.append(' ')
            builder.append(column.type.definition)

            for (property in column.getProperties()) {
                builder.append(' ')
                builder.append(property.definition)
            }

            if (i != columns.size() - 1) {
                builder.append(',')
            }

        }

        return builder.toString()
    }
}