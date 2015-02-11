package com.laimiux.sqlin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TableOpenHelper extends SQLiteOpenHelper {
    private final DatabaseInfo databaseInfo;

    public TableOpenHelper(Context context, DatabaseInfo dbInfo, SQLiteDatabase.CursorFactory factory) {
        super(context.getApplicationContext(), dbInfo.getDatabaseName(), factory, dbInfo.getDatabaseVersion());
        this.databaseInfo = dbInfo;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        databaseInfo.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        databaseInfo.getUpgradeStrategy(db, oldVersion, newVersion).upgrade();
    }
}

