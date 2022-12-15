package com.fpoly.foodapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.CartItemDAO;
import com.fpoly.foodapp.DAO.CartItemTempDAO;
import com.fpoly.foodapp.DAO.LocationDAO;
import com.fpoly.foodapp.DAO.OderDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.CartItemTempAdapter;
import com.fpoly.foodapp.adapters.LocationAdapter;
import com.fpoly.foodapp.modules.CartTempModule;
import com.fpoly.foodapp.modules.LocationModule;
import com.fpoly.foodapp.modules.OderHistoryModel;

import java.util.ArrayList;

public class SelectLocationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcvListLocation;
    static UsersDAO usersDAO;
    static LocationDAO locationDAO;
    ArrayList<LocationModule> listLocal;
    LocationAdapter locationAdapter;
    public static int check = 0;
    private LinearLayout addLocation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        toolbar = findViewById(R.id.toolBarCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Chọn địa chỉ nhận hàng");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addLocation = findViewById(R.id.addLocation);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLocationActivity.this, AddLocationActivity.class));
            }
        });
        usersDAO = new UsersDAO(getApplicationContext());
        locationDAO = new LocationDAO(getApplicationContext());
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int idUser = usersDAO.getIDUser(email);
        listLocal = (ArrayList<LocationModule>) locationDAO.getALLByEmail(email);
        rcvListLocation = findViewById(R.id.rcvListLocation);
        if (listLocal.size() == 0) {

        } else {
            locationAdapter = new LocationAdapter(getApplicationContext(), listLocal);
            rcvListLocation.setAdapter(locationAdapter);
            LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            rcvListLocation.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcvListLocation.getContext(), layoutManager.getOrientation());
            rcvListLocation.addItemDecoration(dividerItemDecoration);
            rcvListLocation.setHasFixedSize(true);
            rcvListLocation.setNestedScrollingEnabled(false);
        }
    }

}