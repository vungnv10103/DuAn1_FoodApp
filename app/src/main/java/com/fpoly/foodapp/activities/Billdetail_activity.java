package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.Billdetails_adapter;

import com.fpoly.foodapp.modules.billdetailmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Billdetail_activity extends AppCompatActivity {


    private RecyclerView RCL;
    ArrayList<billdetailmodel> moduleArrayList = new ArrayList<>();
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
        adapter =new Billdetails_adapter(moduleArrayList , this);
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
                    billdetailmodel item =snapshot1.getValue(billdetailmodel.class);
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