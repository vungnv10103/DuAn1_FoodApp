package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.OderHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class OderDAO {
    private SQLiteDatabase db;

    public OderDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(OderHistoryModel obj) {
        ContentValues values = new ContentValues();
        values.put("code", obj.getMadonhang());
        values.put("listProduct", obj.getSoluongsanphan());
        values.put("checkStatus", obj.getCheckStatus());
        values.put("status", obj.getTrangthai());
        values.put("idUser", obj.getIdUser());
        values.put("time", obj.getNgaymua());
        values.put("totalOder", obj.getTongtien());
        values.put("totalPrice", obj.getTongtiensanpham());
        values.put("tax", obj.getTax());
        values.put("delivery", obj.getDalivery());


        return db.insert("Oder", null, values);
    }
    public int updateStatus(OderHistoryModel obj){
        ContentValues values = new ContentValues();
        values.put("status", obj.getTrangthai());
        return db.update("Oder", values, "id=?", new String[]{String.valueOf(obj.id)});
    }



    public int delete(int id) {
        return db.delete("Oder", "id=?", new String[]{String.valueOf(id)});
    }


    public List<OderHistoryModel> getALL() {
        String sql = "SELECT * FROM Oder";
        return getData(sql);
    }
    public List<OderHistoryModel> getAllByIdUser(int idUser){
        String sql = "SELECT * FROM Oder WHERE idUser=?";
        return getData(sql, String.valueOf(idUser));
    }
    public List<OderHistoryModel> getAllByStatus(int idUser, int status){
        String sql = "SELECT * FROM Oder WHERE idUser=? AND checkStatus=?";
        return getData(sql, new String[]{String.valueOf(idUser), String.valueOf(status)});
    }


    @SuppressLint("Range")
    private List<OderHistoryModel> getData(String sql, String... selectionArgs) {
        List<OderHistoryModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            OderHistoryModel obj = new OderHistoryModel();
            obj.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            obj.setMadonhang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("code"))));
            obj.setSoluongsanphan(cursor.getString(cursor.getColumnIndex("listProduct")));
            obj.setCheckStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("checkStatus"))));
            obj.setTrangthai(cursor.getString(cursor.getColumnIndex("status")));
            obj.setIdUser(Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser"))));
            obj.setNgaymua(cursor.getString(cursor.getColumnIndex("time")));
            obj.setTongtiensanpham(Double.parseDouble(cursor.getString(cursor.getColumnIndex("totalPrice"))));
            obj.setTongtien(Double.parseDouble(cursor.getString(cursor.getColumnIndex("totalOder"))));
            obj.setTax(Double.parseDouble(cursor.getString(cursor.getColumnIndex("tax"))));
            obj.setDalivery(Double.parseDouble(cursor.getString(cursor.getColumnIndex("delivery"))));

            list.add(obj);

        }
        return list;

    }
}
