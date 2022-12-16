package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.fpoly.foodapp.modules.OderHistoryModelNew;

import java.util.ArrayList;
import java.util.List;

public class OderDAO {
    private SQLiteDatabase db;

    public OderDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(OderHistoryModelNew obj) {
        ContentValues values = new ContentValues();
        values.put("code", obj.code);
        values.put("listProduct", obj.listProduct);
        values.put("checkStatus", obj.checkStatus);
        values.put("status", obj.status);
        values.put("idUser", obj.idUser);
        values.put("time", obj.dateTime);
        values.put("totalOder", obj.totalFinal);
        values.put("totalPrice", obj.totalItem);
        values.put("feeTransport", obj.feeTransport);


        return db.insert("Oder", null, values);
    }
    public int updateStatus(OderHistoryModelNew obj){
        ContentValues values = new ContentValues();
        values.put("checkStatus", obj.checkStatus);
        values.put("status", obj.status);
        return db.update("Oder", values, "id=?", new String[]{String.valueOf(obj.id)});
    }



    public int delete(int id) {
        return db.delete("Oder", "id=?", new String[]{String.valueOf(id)});
    }


    public List<OderHistoryModelNew> getALL() {
        String sql = "SELECT * FROM Oder";
        return getData(sql);
    }
    public List<OderHistoryModelNew> getAllByIdUser(int idUser){
        String sql = "SELECT * FROM Oder WHERE idUser=?";
        return getData(sql, String.valueOf(idUser));
    }
    public List<OderHistoryModelNew> getAllByStatus(int idUser, int status){
        String sql = "SELECT * FROM Oder WHERE idUser=? AND checkStatus=?";
        return getData(sql, new String[]{String.valueOf(idUser), String.valueOf(status)});
    }


    @SuppressLint("Range")
    private List<OderHistoryModelNew> getData(String sql, String... selectionArgs) {
        List<OderHistoryModelNew> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            OderHistoryModelNew obj = new OderHistoryModelNew();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.code = Integer.parseInt(cursor.getString(cursor.getColumnIndex("code")));
            obj.listProduct = cursor.getString(cursor.getColumnIndex("listProduct"));
            obj.checkStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("checkStatus")));
            obj.status = cursor.getString(cursor.getColumnIndex("status"));
            obj.idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            obj.dateTime = cursor.getString(cursor.getColumnIndex("time"));
            obj.totalItem = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totalPrice")));
            obj.totalFinal = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totalOder")));
            obj.feeTransport = Double.parseDouble(cursor.getString(cursor.getColumnIndex("feeTransport")));

            list.add(obj);

        }
        return list;

    }
}
