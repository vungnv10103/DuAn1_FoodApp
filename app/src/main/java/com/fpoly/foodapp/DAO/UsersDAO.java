package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.UsersModule;

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
        values.put("feedback", obj.feedback);

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
    public int updateFeedBack(UsersModule obj) {
        ContentValues values = new ContentValues();
        values.put("feedback", obj.feedback);
        return db.update("User", values, "email=?", new String[]{obj.email});
    }
    public int updatePass(UsersModule obj){
        ContentValues values = new ContentValues();
        values.put("pass", obj.pass);
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
        if (list != null){
            return list.get(0).bitmap;
        }
       return "null";
    }
    public int getIDUser(String email) {
        String sql = "SELECT * FROM User WHERE email=?";
        List<UsersModule> list = getData(sql, email);
        return list.get(0).id;
    }
    public String getCurrentPass(String email) {
        String sql = "SELECT * FROM User WHERE email=?";
        List<UsersModule> list = getData(sql, email);
        return list.get(0).pass;
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
    // check login in sqlite
    public int checkLogin(String email, String pass){
        String sql = "SELECT * FROM User WHERE email=? AND pass=?";
        List<UsersModule> list = getData(sql,email,pass);
        if(list == null || list.size()==0){
            return -1;
        }
        return 1;

    }
}
