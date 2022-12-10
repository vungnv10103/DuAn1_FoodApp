package com.fpoly.foodapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.Bill_detail_paid;
import com.fpoly.foodapp.activities.Billdetail_activity;
import com.fpoly.foodapp.activities.FavouriteActivity;
import com.fpoly.foodapp.activities.ListUserActivity;
import com.fpoly.foodapp.activities.LoginActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    TextView txtsoluongtruycaptrang , txtsoluongdonhangdaban , txtdoanhthucuahang;
    ConstraintLayout constraintLayout , constraintLayout1 ,constraintLayout2 , constraintLayout3;
    ImageView imgLogout;
    PieChart pieChart1 ;
    List<PieEntry> list = new ArrayList<>();
    Intent intent;
    Bundle bundle ;
    int value;
    SharedPreferences sharedPreferences  , sharedPreferences1;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        pieChart1 = findViewById(R.id.piechartok);
        init();
        intent = getIntent();



        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , LoginActivity.class);
                startActivity(intent);

            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                finishAffinity();
            }
        });
        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this , Bill_detail_paid.class));
            }
        });
        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this , Billdetail_activity.class));
            }
        });
        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListUserActivity.class));
                finishAffinity();
            }
        });
    }

    private void setupChart() {
        PieDataSet pieDataSet = new PieDataSet(list , "Pie Chart");
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieData.setValueTextSize(15f);
        pieChart1.setCenterText("Thống Kê");
        pieChart1.getDescription().setEnabled(false);
        pieChart1.animateY(5000 , Easing.EaseInOutQuad);
        pieChart1.setData(pieData);
        pieChart1.invalidate();
    }
    private void setValue() {
        list.add(new PieEntry(value, "số luong tai khoan"));
        list.add(new PieEntry(50, "đơn hàng đã bán"));
    }
    public void init(){
        txtsoluongtruycaptrang = findViewById(R.id.txtsoluongtruycap);
        txtsoluongdonhangdaban = findViewById(R.id.tvSoLuongBan);
        txtdoanhthucuahang = findViewById(R.id.txtDoanhThu);
        constraintLayout = findViewById(R.id.constraintLayout7);
        constraintLayout1 = findViewById(R.id.constraintLayout8);
        constraintLayout2 = findViewById(R.id.constraintLayout9);
        constraintLayout3 = findViewById(R.id.constraintLayout10);
        imgLogout = findViewById(R.id.imgLogout);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("soluongtaikhoan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = snapshot.getValue(Integer.class);
                txtsoluongtruycaptrang.setText(String.valueOf(value));
                setValue();
                setupChart();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}