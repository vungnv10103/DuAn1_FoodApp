package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.CartSystemModule;

import java.util.ArrayList;
import java.util.List;

public class CartSystemDAO {
    private SQLiteDatabase db;

    public CartSystemDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(CartSystemModule obj) {
        ContentValues values = new ContentValues();
        values.put("idUser", obj.idUser);
        values.put("img", obj.img);
        values.put("mCheck", obj.check);
        values.put("checkSelected", obj.checkSelected);
        values.put("name", obj.name);
        values.put("cost", obj.cost);
        values.put("newCost", obj.costNew);
        values.put("idRecommend", obj.idRecommend);
        values.put("quantitiesNew", obj.quantitiesNew);
        values.put("quantities", obj.quantities);

        return db.insert("SystemCart", null, values);
    }

    public int delete(int id) {
        return db.delete("SystemCart", "id=?", new String[]{String.valueOf(id)});
    }

    public int getCheck(int id) {
        String sql = "SELECT * FROM SystemCart WHERE id=?";
        List<CartSystemModule> list = getData(sql, String.valueOf(id));
        return list.get(0).check;
    }

    public List<CartSystemModule> getALL(int idUser) {
        String sql = "SELECT * FROM SystemCart WHERE idUser=?";
        return getData(sql, String.valueOf(idUser));
    }

    @SuppressLint("Range")
    private List<CartSystemModule> getData(String sql, String... selectionArgs) {
        List<CartSystemModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            CartSystemModule obj = new CartSystemModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            obj.idRecommend = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idRecommend")));
            obj.img = cursor.getString(cursor.getColumnIndex("img"));
            obj.check = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mCheck")));
            obj.checkSelected = Integer.parseInt(cursor.getString(cursor.getColumnIndex("checkSelected")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex("cost")));
            obj.costNew = Double.valueOf(cursor.getString(cursor.getColumnIndex("newCost")));
            obj.quantitiesNew = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantitiesNew")));
            obj.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantities")));
            list.add(obj);

        }
        return list;

    }
}
