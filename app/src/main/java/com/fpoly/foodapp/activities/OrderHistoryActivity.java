package com.fpoly.foodapp.activities;

import static com.fpoly.foodapp.R.id.rcvOderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fpoly.foodapp.DAO.OderDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.ListUserAdapter;
import com.fpoly.foodapp.adapters.OderHistoryAdapter;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.fpoly.foodapp.modules.UsersModule;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView rcvOder;
    static OderDAO oderDAO;
    static UsersDAO usersDAO;
    OderHistoryAdapter oderHistoryAdapter;
    ArrayList<OderHistoryModel> listOder;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    private ImageView imgBackPay;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        imgBackPay = findViewById(R.id.imgBackPay);
        imgBackPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rcvOder = findViewById(R.id.rcvOderHistory);
        oderDAO = new OderDAO(getApplicationContext());
        usersDAO = new UsersDAO(getApplicationContext());
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
         String email = pref.getString("EMAIL", "");
         int id = usersDAO.getIDUser(email);

        listOder = (ArrayList<OderHistoryModel>) oderDAO.getAllByIdUser(id);
        oderHistoryAdapter = new OderHistoryAdapter(listOder, getApplicationContext());
        rcvOder.setAdapter(oderHistoryAdapter);

        rcvOder.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        rcvOder.setHasFixedSize(true);
        rcvOder.setNestedScrollingEnabled(false);

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