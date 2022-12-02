package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.VoucherModule;

import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {
    private SQLiteDatabase db;

    public VoucherDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(VoucherModule obj) {
        ContentValues values = new ContentValues();
        values.put("img", obj.img);
        values.put("voucherTitle", obj.voucherTitle);
        values.put("voucherDeadline", obj.voucherDeadline);

        return db.insert("Voucher", null, values);
    }



    public int delete(int id) {
        return db.delete("Voucher", "id=?", new String[]{String.valueOf(id)});

    }


    public List<VoucherModule> getALL() {
        String sql = "SELECT * FROM Voucher";
        return getData(sql);
    }


    @SuppressLint("Range")
    private List<VoucherModule> getData(String sql, String... selectionArgs) {
        List<VoucherModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            VoucherModule obj = new VoucherModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.img = Integer.parseInt(cursor.getString(cursor.getColumnIndex("img")));
            obj.voucherTitle = cursor.getString(cursor.getColumnIndex("voucherTitle"));
            obj.voucherDeadline = cursor.getString(cursor.getColumnIndex("voucherDeadline"));
            list.add(obj);

        }
        return list;

    }
}
