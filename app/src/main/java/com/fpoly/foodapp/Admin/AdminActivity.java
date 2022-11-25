package com.fpoly.foodapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.LoginActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    TextView txtsoluongtruycaptrang , txtsoluongdonhangdaban , txtdoanhthucuahang;
    ConstraintLayout constraintLayout , constraintLayout1 ,constraintLayout2 , constraintLayout3;
    ImageView imgdangxuat;
    PieChart pieChart1 ;
    List<PieEntry> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        pieChart1 = findViewById(R.id.piechartok);
        setvalue();
        anhxa();

        setupchart();
        imgdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , LoginActivity.class);
                startActivity(intent);

            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Món ăn yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Đơn Hàng Đã Bán", Toast.LENGTH_SHORT).show();
            }
        });
        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Đơn Hàng Đang Duyệt", Toast.LENGTH_SHORT).show();
            }
        });
        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "đổi mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupchart() {
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

    private void setvalue() {
        list.add(new PieEntry(16 , "số lượt truy cập"));
//        list.add(new PieEntry(160000, "doanh thu"));
        list.add(new PieEntry(50, "đơn hàng đã bán"));

    }
    public void anhxa(){
        txtsoluongtruycaptrang = findViewById(R.id.txtsoluongtruycap);
        txtsoluongdonhangdaban = findViewById(R.id.txtsoluongban);
        txtdoanhthucuahang = findViewById(R.id.txtdoanhthu);
        constraintLayout = findViewById(R.id.constraintLayout7);
        constraintLayout1 = findViewById(R.id.constraintLayout8);
        constraintLayout2 = findViewById(R.id.constraintLayout9);
        constraintLayout3 = findViewById(R.id.constraintLayout10);
        imgdangxuat = findViewById(R.id.imgdangxuat);

    }
}