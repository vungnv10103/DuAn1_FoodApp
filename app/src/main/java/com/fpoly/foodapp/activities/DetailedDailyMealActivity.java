package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ImageView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.DetailerDailyAdapter;
import com.fpoly.foodapp.modules.DetailerDailyModule;

import java.util.ArrayList;
import java.util.List;

public class DetailedDailyMealActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DetailerDailyModule> detailerDailyModuleList;
    DetailerDailyAdapter dailyAdapter;
    ImageView imageView;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_daily_meal);

        String type = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.detailed_rec);
        imageView = findViewById(R.id.detailer_img);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailerDailyModuleList = new ArrayList<>();
        dailyAdapter = new DetailerDailyAdapter(this, detailerDailyModuleList);
        recyclerView.setAdapter(dailyAdapter);

        if (type != null && type.equalsIgnoreCase("breakfast")) {
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav1, "Breakfast 1", "description", "4.6", "20", "6:00 to 9:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav2, "Breakfast 2", "description", "4.4", "40", "6:00 to 9:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav3, "Breakfast 3", "description", "4.9", "30", "6:00 to 9:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav3, "Breakfast 4", "description", "4.9", "30", "6:00 to 9:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav3, "Breakfast 5", "description", "4.9", "30", "6:00 to 9:00"));
            dailyAdapter.notifyDataSetChanged();
        }

        if (type != null && type.equalsIgnoreCase("lunch")) {
            imageView.setImageResource(R.drawable.lunch);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l1, "Lunch 1", "description", "4.4", "40", "11:00 to 14:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l2, "Lunch 2", "description", "4.9", "35", "11:00 to 14:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l3, "Lunch 3", "description", "4.7", "45", "11:00 to 14:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l3, "Lunch 4", "description", "4.7", "45", "11:00 to 14:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l3, "Lunch 5", "description", "4.7", "45", "11:00 to 14:00"));
            dailyAdapter.notifyDataSetChanged();
        }

        if (type != null && type.equalsIgnoreCase("dinner")) {
            imageView.setImageResource(R.drawable.dinner);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d1, "Dinner 1", "description", "4.5", "45", "18:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d2, "Dinner 2", "description", "4.4", "40", "18:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d3, "Dinner 3", "description", "4.8", "50", "18:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d3, "Dinner 4", "description", "4.8", "50", "18:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d3, "Dinner 5", "description", "4.8", "50", "18:00 to 21:00"));
            dailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("sweets")) {
            imageView.setImageResource(R.drawable.sweets);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s1, "Sweets 1", "description", "4.4", "40", "11:00 to 23:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s2, "Sweets 2", "description", "4.3", "30", "11:00 to 23:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s3, "Sweets 3", "description", "4.9", "35", "11:00 to 23:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s4, "Sweets 4", "description", "4.6", "50", "11:00 to 23:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s4, "Sweets 5", "description", "4.6", "50", "11:00 to 23:00"));
            dailyAdapter.notifyDataSetChanged();
        }

        if (type != null && type.equalsIgnoreCase("coffee")) {
            imageView.setImageResource(R.drawable.coffee);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf1, "Coffee 1", "description", "4.9", "35", "6:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf2, "Coffee 2", "description", "4.4", "40", "6:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf3, "Coffee 3", "description", "4.7", "30", "6:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf3, "Coffee 4", "description", "4.7", "30", "6:00 to 21:00"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf3, "Coffee 5", "description", "4.7", "30", "6:00 to 21:00"));
            dailyAdapter.notifyDataSetChanged();
        }
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