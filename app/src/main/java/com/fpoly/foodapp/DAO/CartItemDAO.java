package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.CartItemModule;

import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
    private SQLiteDatabase db;

    public CartItemDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(CartItemModule obj){
        ContentValues values = new ContentValues();
        values.put("idUser", obj.idUser);
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
        List<CartItemModule> list = getData(sql, String.valueOf(id));
        return list.get(0).check;
    }
//    public int quant(){
//
//    }


    public List<CartItemModule> getALL(int idUser){
        String sql = "SELECT * FROM ItemCart WHERE idUser=?";
        return getData(sql, String.valueOf(idUser));
    }


    @SuppressLint("Range")
    private List<CartItemModule> getData(String sql, String...selectionArgs){
        List<CartItemModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            CartItemModule obj = new CartItemModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            obj.check = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mCheck")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex("cost")));
            obj.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantities")));
            list.add(obj);

        }
        return list;

    }
}
