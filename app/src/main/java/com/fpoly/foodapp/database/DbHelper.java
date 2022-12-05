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
                "quantities INTEGER not null)";
        db.execSQL(createTableItemCart);

        String createTableUser = "create table User(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img TEXT ," +
                "name TEXT ," +
                "email TEXT not null," +
                "pass TEXT not null," +
                "phoneNumber TEXT ," +
                "address TEXT )";
        db.execSQL(createTableUser);

        String createTableVoucher = "create table Voucher(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img INTEGER not null," +
                "voucherTitle TEXT not null," +
                "voucherDeadline TEXT not null)";
        db.execSQL(createTableVoucher);
        String Table_hoa_don_chi_tiet  = "Create table hoadonchitiet(id_bill integer primary key autoincrement , " +
                " iditem integer references ItemCart(id) , iduser integer references User(id) , " +
                "dongia float, tongtien float , ngaymua date    )";
        db.execSQL(Table_hoa_don_chi_tiet);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableItemCart = "drop table if exists ItemCart";
        db.execSQL(dropTableItemCart);
        String dropTableUser = "drop table if exists User";
        db.execSQL(dropTableUser);
        String dropTableVoucher = "drop table if exists Voucher";
        db.execSQL(dropTableVoucher);
        String droptablebilldetail = "drop table if exists hoadonchitiet";
        db.execSQL(droptablebilldetail);
        onCreate(db);

    }
}
