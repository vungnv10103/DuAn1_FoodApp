package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.CartTempModule;

import java.util.ArrayList;
import java.util.List;

public class CartItemTempDAO {
    private SQLiteDatabase db;

    public CartItemTempDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(CartTempModule obj) {
        ContentValues values = new ContentValues();
        values.put("idUser", obj.idUser);
        values.put("img", obj.img);
        values.put("mCheck", obj.check);
        values.put("name", obj.name);
        values.put("cost", obj.cost);
        values.put("idRecommend", obj.idRecommend);
        values.put("quantities", obj.quantities);

        return db.insert("CartTemp", null, values);
    }

    public int delete(String name) {
        return db.delete("CartTemp", "name=?", new String[]{name});
    }

    public ArrayList<CartTempModule> deleteCurrentCart() {
        String sql = "delete from CartTemp";
        db.execSQL(sql);
        return new ArrayList<>();
    }

    public List<CartTempModule> getALL(int idUser) {
        String sql = "SELECT * FROM CartTemp WHERE idUser=?";
        return getData(sql, String.valueOf(idUser));
    }
    public List<CartTempModule> getALLPro(int idUser) {
        String sql = "SELECT id,mCheck,name, idRecommend,idUser, sum(quantities) AS TotalQuantity, sum(cost) as TotalPrice FROM CartTemp WHERE idUser=? GROUP BY idRecommend ORDER By id DESC";
        return getDataPro(sql, String.valueOf(idUser));
    }

    @SuppressLint("Range")
    private List<CartTempModule> getData(String sql, String... selectionArgs) {
        List<CartTempModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            CartTempModule obj = new CartTempModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            obj.idRecommend = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idRecommend")));
            obj.img = cursor.getString(cursor.getColumnIndex("img"));
            obj.check = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mCheck")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex("cost")));
            obj.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantities")));
            list.add(obj);
        }
        return list;
    }

    @SuppressLint("Range")
    private List<CartTempModule> getDataPro(String sql, String... selectionArgs) {
        List<CartTempModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            CartTempModule obj = new CartTempModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            obj.idRecommend = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idRecommend")));
            obj.check = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mCheck")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex("TotalPrice")));
            obj.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("TotalQuantity")));
            list.add(obj);
        }
        return list;
    }

}
