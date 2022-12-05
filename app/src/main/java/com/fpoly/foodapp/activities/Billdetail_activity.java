package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fpoly.foodapp.DAO.bill_detail_DAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.Billdetails_adapter;
import com.fpoly.foodapp.adapters.demo_cart_item_adapter;
import com.fpoly.foodapp.modules.BilldetailModule;
import com.fpoly.foodapp.modules.demo_cart_item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Billdetail_activity extends AppCompatActivity {

    private ArrayList<demo_cart_item> list = new ArrayList<>();
    private RecyclerView RCL;
    ArrayList<BilldetailModule> moduleArrayList = new ArrayList<>();
    demo_cart_item_adapter demo_cart_item_adapter;
    Billdetails_adapter adapter ;
    Button btnsukien;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billdetail);
        RCL = findViewById(R.id.rclbill);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayoutManager manager = new LinearLayoutManager(this , RecyclerView.VERTICAL , false) ;
        RCL.setLayoutManager(manager);
        adapter = new Billdetails_adapter(moduleArrayList , this);
        getdata();
        RCL.setAdapter(adapter);
        RCL.setHasFixedSize(true);
        RCL.setNestedScrollingEnabled(false);
    }
    public void getdata(){
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("objec_bill");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren())
                {
                    BilldetailModule item =snapshot1.getValue(BilldetailModule.class);
                    moduleArrayList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}