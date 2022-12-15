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

    public long insert(CartItemModule obj) {
        ContentValues values = new ContentValues();
        values.put("idUser", obj.idUser);
        values.put("img", obj.img);
        values.put("mCheck", obj.check);
        values.put("name", obj.name);
        values.put("cost", obj.cost);
        values.put("idRecommend", obj.idRecommend);
        values.put("quantities", obj.quantities);

        return db.insert("ItemCart", null, values);
    }

    public int delete(int id) {
        return db.delete("ItemCart", "id=?", new String[]{String.valueOf(id)});
    }

    public int deleteAllByName(String name) {
        return db.delete("ItemCart", "name=?", new String[]{name});
    }

    public int updateStatus(CartItemModule obj) {
        ContentValues values = new ContentValues();
        values.put("mCheck", obj.check);
        return db.update("ItemCart", values, "name=?", new String[]{obj.name});
    }

    //    public int quant(){
//
//    }
    public ArrayList<CartItemModule> deleteCurrentCart() {
        String sql = "delete from ItemCart";
        db.execSQL(sql);
        return new ArrayList<>();
    }

    public List<CartItemModule> getALL(int idUser) {
        String sql = "SELECT * FROM ItemCart WHERE idUser=?";
        return getData(sql, String.valueOf(idUser));
    }

    public List<CartItemModule> getALLPro(int idUser) {
        String sql = "SELECT id,mCheck,idUser, idRecommend, name,img, sum(quantities) AS TotalQuantity, sum(cost) as TotalPrice FROM ItemCart WHERE idUser=? GROUP BY idRecommend ORDER By id DESC";
        return getDataPro(sql, String.valueOf(idUser));
    }

    public List<CartItemModule> getALLSelected(int check) {
        String sql = "SELECT id,mCheck,idUser, idRecommend, name,img, sum(quantities) AS TotalQuantity, sum(cost) as TotalPrice FROM ItemCart WHERE mCheck=? GROUP BY idRecommend ORDER By id DESC ";
        return getDataPro(sql, String.valueOf(check));
    }

    @SuppressLint("Range")
    private List<CartItemModule> getData(String sql, String... selectionArgs) {
        List<CartItemModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            CartItemModule obj = new CartItemModule();
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
    private List<CartItemModule> getDataPro(String sql, String... selectionArgs) {
        List<CartItemModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            CartItemModule obj = new CartItemModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            obj.idRecommend = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idRecommend")));
            obj.img = cursor.getString(cursor.getColumnIndex("img"));
            obj.check = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mCheck")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex("TotalPrice")));
            obj.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("TotalQuantity")));
            list.add(obj);
        }
        return list;
    }

}
