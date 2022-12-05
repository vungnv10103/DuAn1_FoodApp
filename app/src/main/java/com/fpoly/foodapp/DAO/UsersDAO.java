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

public class UsersDAO {
    private SQLiteDatabase db;

    public UsersDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(UsersModule obj) {
        ContentValues values = new ContentValues();
        values.put("img", obj.bitmap);
        values.put("name", obj.name);
        values.put("email", obj.email);
        values.put("pass", obj.pass);
        values.put("phoneNumber", obj.phoneNumber);
        values.put("address", obj.address);
        return db.insert("User", null, values);
    }

    public int updateProfile( UsersModule obj) {
        ContentValues values = new ContentValues();
        values.put("name", obj.name);
        values.put("phoneNumber", obj.phoneNumber);
        values.put("address", obj.address);

        return db.update("User", values, "email=?", new String[]{obj.email});
    }

    public int updateImg(UsersModule obj) {
        ContentValues values = new ContentValues();
        values.put("img", obj.bitmap);
        return db.update("User", values, "email=?", new String[]{obj.email});
    }

    public String getNameUser(String email) {
        String sql = "SELECT * FROM User WHERE email=?";
        List<UsersModule> list = getData(sql, email);
        return list.get(0).name;
    }
    public String getPhone(String email) {
        String sql = "SELECT * FROM User WHERE email=?";
        List<UsersModule> list = getData(sql, email);
        return list.get(0).phoneNumber;
    }
    public String getAddress(String email) {
        String sql = "SELECT * FROM User WHERE email=?";
        List<UsersModule> list = getData(sql, email);
        return list.get(0).address;
    }

    public String getUriImg(String email) {
        String sql = "SELECT * FROM User WHERE email=?";
        List<UsersModule> list = getData(sql, email);
        return list.get(0).bitmap;
    }
    public int getIDUser(String email) {
        String sql = "SELECT * FROM User WHERE email=?";
        List<UsersModule> list = getData(sql, email);
        return list.get(0).id;
    }

    public int delete(int id) {
        return db.delete("User", "id=?", new String[]{String.valueOf(id)});
    }


    public List<UsersModule> getALL() {
        String sql = "SELECT * FROM User";
        return getData(sql);
    }


    @SuppressLint("Range")
    private List<UsersModule> getData(String sql, String... selectionArgs) {
        List<UsersModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            UsersModule obj = new UsersModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.bitmap = cursor.getString(cursor.getColumnIndex("img"));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.email = cursor.getString(cursor.getColumnIndex("email"));
            obj.pass = cursor.getString(cursor.getColumnIndex("pass"));
            obj.phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
            obj.address = cursor.getString(cursor.getColumnIndex("address"));
            list.add(obj);
        }
        return list;

    }
}
