package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.LocationModule;
import com.fpoly.foodapp.modules.UsersModule;

import java.util.ArrayList;
import java.util.List;

public class LocationDAO {
    private SQLiteDatabase db;

    public LocationDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LocationModule obj) {
        ContentValues values = new ContentValues();
        values.put("name", obj.name);
        values.put("email", obj.email);
        values.put("phone", obj.phone);
        values.put("location", obj.location);

        return db.insert("Location", null, values);
    }

    public int delete(int id) {
        return db.delete("Location", "id=?", new String[]{String.valueOf(id)});
    }


    public List<LocationModule> getALL() {
        String sql = "SELECT * FROM Location";
        return getData(sql);
    }
    public List<LocationModule> getALLByEmail(String email) {
        String sql = "SELECT * FROM Location WHERE email=?";
        return getData(sql, email);
    }


    @SuppressLint("Range")
    private List<LocationModule> getData(String sql, String... selectionArgs) {
        List<LocationModule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            LocationModule obj = new LocationModule();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.name = cursor.getString(cursor.getColumnIndex("name"));
            obj.email = cursor.getString(cursor.getColumnIndex("email"));
            obj.phone = cursor.getString(cursor.getColumnIndex("phone"));
            obj.location = cursor.getString(cursor.getColumnIndex("location"));
            list.add(obj);

        }
        return list;

    }
}
