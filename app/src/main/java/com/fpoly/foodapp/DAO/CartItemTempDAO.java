package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.CartTempModule;
import com.fpoly.foodapp.modules.UsersModule;

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
        values.put("checkSelected", obj.checkSelected);
        values.put("name", obj.name);
        values.put("cost", obj.cost);
        values.put("newCost", obj.costNew);
        values.put("idRecommend", obj.idRecommend);
        values.put("quantitiesNew", obj.quantitiesNew);
        values.put("quantities", obj.quantities);

        return db.insert("CartTemp", null, values);
    }
    public int updatePrice( CartTempModule obj) {
        ContentValues values = new ContentValues();
        values.put("name", obj.name);
        values.put("newCost", obj.costNew);
        values.put("quantitiesNew", obj.quantitiesNew);
        return db.update("CartTemp", values, "name=?", new String[]{obj.name});
    }
    public double getToTal(int pos,int mCheck) {
        String sql = "SELECT * FROM CartTemp WHERE mCheck=?";
        List<CartTempModule> list = getData(sql, String.valueOf(mCheck));
        return list.get(pos).costNew;
    }
    @SuppressLint("Range")
    public Double getToTalPrice() {
        String sql = "SELECT sum(newCost) from CartTemp";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToFirst()){
            return cursor.getDouble(cursor.getColumnIndex("sum(newCost)"));
        }
        return cursor.getDouble(cursor.getColumnIndex("sum(newCost)"));
    }

    public int delete(String name) {
        return db.delete("CartTemp", "name=?", new String[]{name});
    }
    public int deleteByCheckSelected(int checkSelected) {
        return db.delete("CartTemp", "checkSelected=?", new String[]{String.valueOf(checkSelected)});
    }

    public ArrayList<CartTempModule> deleteCurrentCart() {
        String sql = "delete from CartTemp";
        db.execSQL(sql);
        return new ArrayList<>();
    }

    public List<CartTempModule> getALL(int idUser, int checkSelected) {
        String sql = "SELECT * FROM CartTemp WHERE idUser=? AND checkSelected=?";
        return getData(sql, new String[]{String.valueOf(idUser), String.valueOf(checkSelected)});
    }
    public List<CartTempModule> getALL(int idUser) {
        String sql = "SELECT * FROM CartTemp WHERE idUser=?";
        return getData(sql, String.valueOf(idUser));
    }
    public List<CartTempModule> getALLPro(int idUser) {
        String sql = "SELECT id,mCheck,checkSelected,name, idRecommend,idUser,newCost,quantitiesNew, sum(quantities) AS TotalQuantity, sum(cost) as TotalPrice FROM CartTemp WHERE idUser=? GROUP BY idRecommend ORDER By id DESC";
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
            obj.checkSelected = Integer.parseInt(cursor.getString(cursor.getColumnIndex("checkSelected")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex("TotalPrice")));
            obj.costNew = Double.valueOf(cursor.getString(cursor.getColumnIndex("newCost")));
            obj.quantitiesNew = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantitiesNew")));
            obj.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("TotalQuantity")));
            list.add(obj);
        }
        return list;
    }

}
