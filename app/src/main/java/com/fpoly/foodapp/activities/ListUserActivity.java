package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.fpoly.foodapp.Admin.AdminActivity;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.ListUserAdapter;
import com.fpoly.foodapp.modules.UsersModule;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    private RecyclerView rcvListUser;
    ListUserAdapter listUserAdapter;
    ArrayList<UsersModule> listUser;
    static UsersDAO usersDAO;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);


        usersDAO = new UsersDAO(getApplicationContext());
        rcvListUser = findViewById(R.id.rcvListUser);

        listUser = (ArrayList<UsersModule>) usersDAO.getALL();
        listUserAdapter = new ListUserAdapter(getApplicationContext(), listUser);
        rcvListUser.setAdapter(listUserAdapter);

        rcvListUser.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        rcvListUser.setHasFixedSize(true);
        rcvListUser.setNestedScrollingEnabled(false);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        finishAffinity();
    }
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}