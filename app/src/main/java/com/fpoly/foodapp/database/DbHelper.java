package com.fpoly.foodapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "demo_cart_item.db";
    static final int DB_VERSION = 1;


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableItemCart = "create table ItemCart(" +
                "name TEXT not null," +
                "cost REAL not null)";
        db.execSQL(createTableItemCart);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableItemCart = "drop table if exists ItemCart";
        db.execSQL(dropTableItemCart);

        onCreate(db);

    }
}
