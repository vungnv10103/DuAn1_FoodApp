package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.DetailerDailyAdapter;
import com.fpoly.foodapp.modules.DetailerDailyModule;

import java.util.ArrayList;
import java.util.List;

public class DetailedDailyMealActivity extends AppCompatActivity {
RecyclerView recyclerView;
List<DetailerDailyModule> detailerDailyModuleList;
DetailerDailyAdapter dailyAdapter;
ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_daily_meal);

        String type =getIntent().getStringExtra("type");

        recyclerView =findViewById(R.id.detailed_rec);
        imageView = findViewById(R.id.detailer_img);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailerDailyModuleList =new ArrayList<>();
        dailyAdapter =new DetailerDailyAdapter(detailerDailyModuleList);
        recyclerView.setAdapter(dailyAdapter);

        if(type !=null && type.equalsIgnoreCase("breakfast")){
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav1,"Breakfast","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav2,"Breakfast","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.fav3,"Breakfast","description","4.4","40","10 to 9"));
            dailyAdapter.notifyDataSetChanged();
        }

        if(type !=null && type.equalsIgnoreCase("lunch")){
            imageView.setImageResource(R.drawable.lunch);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l1,"Lunch","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l2,"Lunch","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.l3,"Lunch","description","4.4","40","10 to 9"));
            dailyAdapter.notifyDataSetChanged();
        }

        if(type !=null && type.equalsIgnoreCase("dinner")){
            imageView.setImageResource(R.drawable.dinner);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d1,"Dinner","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d2,"Dinner","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.d3,"Dinner","description","4.4","40","10 to 9"));
            dailyAdapter.notifyDataSetChanged();
        }
        if(type !=null && type.equalsIgnoreCase("sweets")){
            imageView.setImageResource(R.drawable.sweets);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s1,"Sweets","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s2,"Sweets","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s3,"Sweets","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.s4,"Sweets","description","4.4","40","10 to 9"));
            dailyAdapter.notifyDataSetChanged();
        }

        if(type !=null && type.equalsIgnoreCase("coffee")){
            imageView.setImageResource(R.drawable.coffe);
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf1,"Coffee","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf2,"Coffee","description","4.4","40","10 to 9"));
            detailerDailyModuleList.add(new DetailerDailyModule(R.drawable.cf3,"Coffee","description","4.4","40","10 to 9"));
            dailyAdapter.notifyDataSetChanged();
        }
    }
}