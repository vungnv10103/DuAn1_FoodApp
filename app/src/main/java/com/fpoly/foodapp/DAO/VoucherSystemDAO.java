package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.UsersModule;
import com.fpoly.foodapp.modules.VoucherModule;
import com.fpoly.foodapp.modules.VoucherSystemModule;

import java.util.ArrayList;
import java.util.List;

public class VoucherSystemDAO {
    private SQLiteDatabase db;

    public VoucherSystemDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(VoucherSystemModule obj) {
        ContentValues values = new ContentValues();
        values.put("img", obj.img);
        values.put("voucherTitle", obj.discount);
        values.put("voucherDeadline", obj.voucherDeadline);

        return db.insert("VoucherSystem", null, values);
    }



    public int delete(int id) {
        return db.delete("VoucherSystem", "id=?", new String[]{String.valueOf(id)});

    }
    public String getDeadLineVoucher(int id){
        String sql = "SELECT * FROM VoucherSystem WHERE id=?";
        List<VoucherSystemModule> list = getData(sql, String.valueOf(id));
        return list.get(0).voucherDeadline;
    }
    public int getDiscount(int id){
        String sql = "SELECT * FROM VoucherSystem WHERE id=?";
        List<VoucherSystemModule> list = getData(sql, String.valueOf(id));
        return list.get(0).discount;
    }


    public List<VoucherSystemModule> getALL() {
        String sql = "SELECT * FROM VoucherSystem";
        return getData(sql);
    }
    public List<VoucherSystemModule> getALLValid() {
        String sql = "SELECT * FROM VoucherSystem WHERE voucherDeadline >  date()";
        return getData(sql);
    }


    @SuppressLint("Range")
    private List<VoucherSystemModule> getData(String sql, String... selectionArgs) {
        List<VoucherSystemModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            VoucherSystemModule obj = new VoucherSystemModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.img = Integer.parseInt(cursor.getString(cursor.getColumnIndex("img")));
            obj.discount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("voucherTitle")));
            obj.voucherDeadline = cursor.getString(cursor.getColumnIndex("voucherDeadline"));
            list.add(obj);

        }
        return list;

    }
}
