package com.fpoly.foodapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.foodapp.database.DbHelper;
import com.fpoly.foodapp.modules.BilldetailModule;

import java.util.ArrayList;

public class bill_detail_DAO {
    DbHelper helper;
public bill_detail_DAO (Context context){
    helper = new DbHelper(context);
}
public ArrayList<BilldetailModule> getAll(){
ArrayList<BilldetailModule> list = new ArrayList<>();
    SQLiteDatabase database  = helper.getReadableDatabase();
    Cursor cursor =database.rawQuery("select bill.id_bill  , " +
            "item.name ,bill.iditem ,item.quantities, item.cost , bill.tongtien ,bill.ngaymua from hoadonchitiet   bill , ItemCart item " +
            "where bill.iditem  = item.id", null);
if(cursor.getCount()!=0){
    cursor.moveToFirst();
    while(!cursor.isAfterLast()){

        cursor.moveToNext();
    }
}
return list;
}
}
