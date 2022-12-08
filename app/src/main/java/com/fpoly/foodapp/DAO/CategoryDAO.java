package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fpoly.foodapp.adapters.category.ItemCategory;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private SQLiteDatabase db;

    public CategoryDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ItemCategory obj) {
        ContentValues values = new ContentValues();
        values.put("name" , obj.getName());
        values.put("img", obj.getImg());

        return db.insert("Category", null, values);
    }
    public int updateAll(ItemCategory obj){
        ContentValues values = new ContentValues();
        values.put("name", obj.getName());
        values.put("img", obj.getImg());

        return db.update("Category", values, "id=?", new String[]{String.valueOf(obj.getId())} );
    }
    public String getUriImg(String name) {
        String sql = "SELECT * FROM Category WHERE name=?";
        List<ItemCategory> list = getData(sql, name);
//        if (list.size() == 0){
//            return "";
//        }
        return list.get(0).getImg();
    }



    public int delete(String name) {
        return db.delete("Category", "name=?", new String[]{name});
    }


    public List<ItemCategory> getALL() {
        String sql = "SELECT * FROM Category";
        return getData(sql);
    }


    @SuppressLint("Range")
    private List<ItemCategory> getData(String sql, String... selectionArgs) {
        List<ItemCategory> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ItemCategory obj = new ItemCategory();
            obj.setName(cursor.getString(cursor.getColumnIndex("name")));
            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            obj.setImg(cursor.getString(cursor.getColumnIndex("img")));

            list.add(obj);

        }
        return list;

    }
}
