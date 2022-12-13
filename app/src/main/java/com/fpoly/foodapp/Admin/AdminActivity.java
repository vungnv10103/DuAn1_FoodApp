package com.fpoly.foodapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.fpoly.foodapp.adapters.Billdetail_paid_Adapter;
import com.fpoly.foodapp.modules.billdetail_paid_model;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
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
    List<PieEntry> list2 = new ArrayList<>();
    Intent intent;
    Bundle bundle ;
    ArrayList<billdetail_paid_model> list1 = new ArrayList<>();
    Billdetail_paid_Adapter adapter = new Billdetail_paid_Adapter(list1 , this);
    int value;
    SharedPreferences sharedPreferences  , sharedPreferences1;
 double doanhthu ;
    int i = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        pieChart1 = findViewById(R.id.piechartok);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("soluongtaikhoan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = snapshot.getValue(Integer.class);
                txtsoluongtruycaptrang.setText(String.valueOf(value));
                list.add(new PieEntry(value, "số luong tai khoan"));
                setupChart();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference reference1 = database1.getReference("soluongdondaban");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                i = snapshot.getValue(Integer.class);
                txtsoluongdonhangdaban.setText(String.valueOf(i));
                list.add(new PieEntry(i, "đơn hàng đã bán"));
                setupChart();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        init();
        intent = getIntent();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("object_bill_paid");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    billdetail_paid_model model = snapshot1.getValue(billdetail_paid_model.class);
                    list1.add(model);
                }
                for(int k = 0 ; k <=list1.size()-1;k++){
                    doanhthu +=list1.get(k).getTongtien();
                }
                txtdoanhthucuahang.setText(String.valueOf(doanhthu));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





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
                finishAffinity();
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





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}