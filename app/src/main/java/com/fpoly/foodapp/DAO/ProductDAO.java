//package com.fpoly.foodapp.DAO;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.fpoly.foodapp.R;
//import com.fpoly.foodapp.adapters.item_product.ItemProduct;
//import com.fpoly.foodapp.database.DbHelper;
//import com.fpoly.foodapp.modules.UsersModule;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProductDAO {
//    private SQLiteDatabase db;
//
//    public ProductDAO(Context context) {
//        DbHelper dbHelper = new DbHelper(context);
//        db = dbHelper.getWritableDatabase();
//    }
//
//    public long insert(ItemProduct obj) {
//        ContentValues values = new ContentValues();
//        values.put("img", obj.getResource_image());
//        values.put("name", obj.getTitle());
//        values.put("cost", obj.getMoney());
//        values.put("location", obj.getLocation());
//        values.put("quantity", obj.getQuantities());
//        values.put("type", obj.getType());
//
//        return db.insert("Product", null, values);
//    }
//    public String getUriImg(String name) {
//        String sql = "SELECT * FROM Product WHERE name=?";
//        List<ItemProduct> list = getData(sql, name);
//        return list.get(0).getResource_image();
//    }
//    public String getLocation(String name) {
//        String sql = "SELECT * FROM Product WHERE name=?";
//        List<ItemProduct> list = getData(sql, name);
//        return list.get(0).getLocation();
//    }
//
//
//    public int delete(int id) {
//        return db.delete("Product", "id=?", new String[]{String.valueOf(id)});
//    }
//
//
//    public List<ItemProduct> getALL() {
//        String sql = "SELECT * FROM Product";
//        return getData(sql);
//    }
//
//
//    @SuppressLint("Range")
//    private List<ItemProduct> getData(String sql, String... selectionArgs) {
//        List<ItemProduct> list = new ArrayList<>();
//        Cursor cursor = db.rawQuery(sql, selectionArgs);
//        while (cursor.moveToNext()) {
//            ItemProduct obj = new ItemProduct();
//            obj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
//            obj.setImg(R.drawable.plus_circle);
//            obj.setTitle(cursor.getString(cursor.getColumnIndex("name")));
//            obj.setResource_image(cursor.getString(cursor.getColumnIndex("img")));
//            obj.setMoney(Double.parseDouble(cursor.getString(cursor.getColumnIndex("cost"))));
//            obj.setLocation(cursor.getString(cursor.getColumnIndex("location")));
//            obj.setQuantities(Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity"))));
//            obj.setType(cursor.getString(cursor.getColumnIndex("type")));
//            list.add(obj);
//
//        }
//        return list;
//
//    }
//}
