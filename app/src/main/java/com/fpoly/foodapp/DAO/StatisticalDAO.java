package com.fpoly.foodapp.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.adapters.recommend.ItemStatisticalRecommend;
import com.fpoly.foodapp.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class StatisticalDAO {
    private SQLiteDatabase db;
    private Context context;

    public StatisticalDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    //
    @SuppressLint("Range")
    public List<ItemStatisticalRecommend> getTop(){
        //String sql = "SELECT idRecommend, COUNT(idUser) AS Solanmua FROM ItemCart GROUP BY idRecommend";
        String sql = "SELECT idRecommend, sum(quantities) AS Soluongmua FROM ItemCart GROUP BY idRecommend ORDER BY  Soluongmua DESC";
        List<ItemStatisticalRecommend> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ItemStatisticalRecommend item = new ItemStatisticalRecommend();
            item.idRecommend = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idRecommend")));
            item.quantities = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Soluongmua")));
            list.add(item);
        }
        return list;
    }
}
