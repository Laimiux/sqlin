package com.laimiux.sqlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.database.Cursor
import java.util.ArrayList
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import com.laimiux.sqlin
import com.laimiux.sqlin
import com.laimiux.sqlin

trait DatabaseInfo {
    val databaseName: String
    val databaseVersion: Int

    fun getTables(): Array<out Table<out Any>>

    /**
     * You can add your own database migration strategy.
     * Default strategy is to recreate all tables.
     */
    fun getUpgradeStrategy(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): UpgradeStrategy  {
        return RecreateAll(db, this)
    }


    final fun create(db: SQLiteDatabase) {
        for (table in getTables()) {
            DatabaseFunctions.createTable(db, table)

        }
    }

    class RecreateAll(val db: SQLiteDatabase, val databaseInfo: DatabaseInfo): UpgradeStrategy {
        override fun upgrade() {
            for (table in databaseInfo.getTables()) {
                DatabaseFunctions.dropTable(db, table)
            }

            databaseInfo.create(db)
        }
    }

    /**
     * trait used to upgrade database from one version to another
     */
    trait UpgradeStrategy {
        fun upgrade()
    }
}


