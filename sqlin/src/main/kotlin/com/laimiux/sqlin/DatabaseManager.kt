package com.laimiux.sqlin

import android.database.sqlite.SQLiteDatabase
import android.content.Context

public open class DatabaseManager(val databaseInfo: DatabaseInfo) {
    private var databaseHelper: TableOpenHelper? = null
    private var writableDatabase: SQLiteDatabase? = null

    private fun getDatabaseHelper(context: Context): TableOpenHelper {
        if(databaseHelper == null) {
            val applicationContext = context.getApplicationContext()
            synchronized(this){
                if(databaseHelper == null) {
                    databaseHelper = createDatabaseHelper(applicationContext)
                }
            }
        }

        return databaseHelper!!
    }

    final fun getWritableDatabase(context: Context): SQLiteDatabase {
        if(writableDatabase == null) {
            val databaseHelper = getDatabaseHelper(context)
            synchronized(this) {
                if(writableDatabase == null) {
                    writableDatabase = databaseHelper.getWritableDatabase()
                }
            }

        }

        return writableDatabase!!
    }


    fun <T> dao(context: Context, table: Table<T>): Dao<T> {
        val database = getWritableDatabase(context)
        return Dao(database, table)
    }

    /**
     * Override this to create a custom Table Open Helper
     */
    fun createDatabaseHelper(context: Context): TableOpenHelper {
        return TableOpenHelper(context, databaseInfo, null)
    }
}