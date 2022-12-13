package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.Billdetails_adapter;

import com.fpoly.foodapp.modules.billdetailmodel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Billdetail_activity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    private RecyclerView RCL;
    ArrayList<billdetailmodel> moduleArrayList = new ArrayList<>();

    Billdetails_adapter adapter;
    Button btnsukien;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billdetail);
        RCL = findViewById(R.id.rclbill);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RCL.setLayoutManager(manager);
        adapter = new Billdetails_adapter(moduleArrayList, this);
        getdata();
        RCL.setAdapter(adapter);
        RCL.setHasFixedSize(true);
        RCL.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
            getdata();
        super.onStart();
    }

    public void getdata() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("objec_bill");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(moduleArrayList!=null){
                    moduleArrayList.clear();
                }
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    billdetailmodel item = snapshot1.getValue(billdetailmodel.class);
                    moduleArrayList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //cách 2
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                billdetailmodel item = snapshot.getValue(billdetailmodel.class);
//                   if(moduleArrayList!=null){
//                       moduleArrayList.add(item);
//                       adapter.notifyDataSetChanged();
//                   }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();

    }


}