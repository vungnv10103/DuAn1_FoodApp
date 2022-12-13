package com.fpoly.foodapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "FoodApp.db";
    static final int DB_VERSION = 1;


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableItemCart = "create table ItemCart(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mCheck INTEGER not null," +
                "name TEXT not null," +
                "cost REAL not null," +
                "idUser INTEGER REFERENCES User(id)," +
                "idRecommend INTEGER REFERENCES Recommend(id)," +
                "quantities INTEGER not null)";
        db.execSQL(createTableItemCart);

        String createTableUser = "create table User(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img TEXT ," +
                "name TEXT ," +
                "email TEXT not null," +
                "pass TEXT not null," +
                "phoneNumber TEXT ," +
                "feedback TEXT ," +
                "address TEXT not null)";
        db.execSQL(createTableUser);

        String createTableVoucher = "create table Voucher(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img INTEGER not null," +
                "voucherTitle TEXT not null," +
                "voucherDeadline TEXT not null)";
        db.execSQL(createTableVoucher);

        String createTableVoucherSystem = "create table VoucherSystem(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img INTEGER not null," +
                "voucherTitle TEXT not null," +
                "voucherDeadline TEXT not null)";
        db.execSQL(createTableVoucherSystem);

        String createTableCategory = "create table Category(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img TEXT not null," +
                "name TEXT not null)";
        db.execSQL(createTableCategory);

        String createTableProduct = "create table Product(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img TEXT not null," +
                "name TEXT not null," +
                "cost REAL not null," +
                "location TEXT not null," +
                "quantity INTEGER not null," +
                "type TEXT REFERENCES Category(name))";
        db.execSQL(createTableProduct);

        String createTableRecommended = "create table Recommend(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img TEXT not null," +
                "favourite INTEGER not null," +
                "name TEXT not null," +
                "idUser INTEGER REFERENCES User(id)," +
                "location TEXT not null," +
                "description TEXT not null," +
                "timeDelay TEXT not null," +
                "calo REAL not null," +
                "rate REAL not null," +
                "quantity_sold INTEGER not null," +
                "cost REAL not null)";
        db.execSQL(createTableRecommended);

        String createTableOder = "create table Oder(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT not null," +
                "listProduct TEXT not null," +
                "status TEXT not null," +
                "time TEXT not null," +
                "idUser INTEGER REFERENCES User(id)," +
                "checkStatus INTEGER not null," +
                "totalPrice REAL not null," +
                "tax REAL not null," +
                "delivery REAL not null," +
                "totalOder REAL not null)";
        db.execSQL(createTableOder);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableItemCart = "drop table if exists ItemCart";
        db.execSQL(dropTableItemCart);
        String dropTableUser = "drop table if exists User";
        db.execSQL(dropTableUser);
        String dropTableVoucher = "drop table if exists Voucher";
        db.execSQL(dropTableVoucher);
        String dropTableVoucherSystem = "drop table if exists VoucherSystem";
        db.execSQL(dropTableVoucherSystem);
        String dropTableCategory = "drop table if exists Category";
        db.execSQL(dropTableCategory);
        String dropTableProduct = "drop table if exists Product";
        db.execSQL(dropTableProduct);
        String dropTableRecommend = "drop table if exists Recommend";
        db.execSQL(dropTableRecommend);
        String dropTableOder = "drop table if exists Oder";
        db.execSQL(dropTableOder);

        onCreate(db);

    }
}
