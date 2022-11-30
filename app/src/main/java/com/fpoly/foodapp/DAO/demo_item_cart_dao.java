package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.UsersModule;
import com.fpoly.foodapp.modules.demo_cart_item;

import java.util.ArrayList;
import java.util.List;

public class demo_item_cart_dao {
    private SQLiteDatabase db;

    public demo_item_cart_dao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(demo_cart_item obj){
        ContentValues values = new ContentValues();
        values.put("mCheck", obj.check );
        values.put("name", obj.name);
        values.put("cost", obj.cost);
        values.put("quantities", obj.quantities);

        return db.insert("ItemCart",null,values);
    }
    public int delete(int id){
        return db.delete("ItemCart","id=?", new String[]{String.valueOf(id)});
    }
    public int getCheck(int id) {
        String sql = "SELECT * FROM ItemCart WHERE id=?";
        List<demo_cart_item> list = getData(sql, String.valueOf(id));
        return list.get(0).check;
    }
//    public int quant(){
//
//    }


    public List<demo_cart_item> getALL(){
        String sql = "SELECT * FROM ItemCart";
        return getData(sql);
    }


    @SuppressLint("Range")
    private List<demo_cart_item> getData(String sql, String...selectionArgs){
        List<demo_cart_item> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            demo_cart_item obj = new demo_cart_item();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.check = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mCheck")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex("cost")));
            obj.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantities")));
            list.add(obj);

        }
        return list;

    }
}
