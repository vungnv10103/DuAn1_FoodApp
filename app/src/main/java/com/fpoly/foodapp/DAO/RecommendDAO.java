package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.category.ItemCategory;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.RecommendedModule;
import com.fpoly.foodapp.modules.UsersModule;

import java.util.ArrayList;
import java.util.List;

public class RecommendDAO {
    private SQLiteDatabase db;

    public RecommendDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ItemRecommend obj) {
        ContentValues values = new ContentValues();
        values.put("img", obj.img_resource);
        values.put("name", obj.title);
        values.put("cost", obj.price);
        values.put("idUser", obj.idUser);
        values.put("favourite", obj.favourite);
        values.put("location", obj.location);


        return db.insert("Recommend", null, values);
    }
    public int updateFavourite(ItemRecommend obj){
        ContentValues values = new ContentValues();
        values.put("favourite", obj.favourite);
        return db.update("Recommend", values, "name=?", new String[]{obj.title});
    }

    public String getUriImg(String name) {
        String sql = "SELECT * FROM Recommend WHERE name=?";
        List<ItemRecommend> list = getData(sql, name);
        return list.get(0).img_resource;
    }
    public int getFavourite(String name) {
        String sql = "SELECT * FROM Recommend WHERE name=?";
        List<ItemRecommend> list = getData(sql, name);
        return list.get(0).favourite;
    }


    public int delete(String name) {
        return db.delete("Recommend", "name=?", new String[]{name});
    }


    public List<ItemRecommend> getALL() {
        String sql = "SELECT * FROM Recommend";
        return getData(sql);
    }
    public List<ItemRecommend> getALLFavourite(int favourite){
        String sql = "SELECT * FROM Recommend WHERE favourite=?";
        return getData(sql, String.valueOf(favourite));
    }


    @SuppressLint("Range")
    private List<ItemRecommend> getData(String sql, String... selectionArgs) {
        List<ItemRecommend> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ItemRecommend obj = new ItemRecommend();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            obj.img_resource = cursor.getString(cursor.getColumnIndex("img"));
            obj.title = cursor.getString(cursor.getColumnIndex("name"));
            obj.price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("cost")));
            obj.favourite = Integer.parseInt(cursor.getString(cursor.getColumnIndex("favourite")));
            obj.location = cursor.getString(cursor.getColumnIndex("location"));
            list.add(obj);

        }
        return list;

    }
}
