package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.fpoly.foodapp.R;

import java.util.List;

public class ShowdetailActivity extends AppCompatActivity {
    TextView title , txtdongia , txtnumberitem1 , txtmota;
    TextView txttongtien;
    TextView txtdanhgia1;
    TextView txttime1;
    TextView txtcalo1;
    TextView txtaddtocart;
    ImageView plusbtn , minusbtn;
    ImageView imgpic;
    int soluong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);
        anhxa();
        txtnumberitem1.setText(String.valueOf(soluong));
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieu");
        double gia = bundle.getDouble("money_piza1");
        int anh1 = bundle.getInt("anh");
        imgpic.setImageResource(anh1);
        Toast.makeText(this, ""+gia, Toast.LENGTH_LONG).show();
        txtdongia.setText("$"+gia);
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong++;
                txtnumberitem1.setText(String.valueOf(soluong));
                double tongtien  = soluong*gia;
                txttongtien.setText(tongtien+"$");
            }
        });

        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soluong>0){
                    soluong--;
                    txtnumberitem1.setText(String.valueOf(soluong));
                    double tongtien  = soluong*gia;
                    txttongtien.setText("$"+tongtien);
                }
                else {
                    Toast.makeText(ShowdetailActivity.this, "số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtmota.setText("có bánh mì  , sà lách  , cà chua ,thịt gà , thịt bò , phô mai");
        txtaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
    private void anhxa() {
        title = findViewById(R.id.titletxt);
        txtdongia = findViewById(R.id.pricetxt);
        txtnumberitem1 = findViewById(R.id.txtnumberitem);
        txtmota = findViewById(R.id.txtdescription);
        txttongtien = findViewById(R.id.txttotalprice);
        txtdanhgia1 = findViewById(R.id.txtdanhgia);
        txttime1 = findViewById(R.id.txttime);
        txtcalo1 = findViewById(R.id.txtcalo);
        plusbtn = findViewById(R.id.them);
        minusbtn = findViewById(R.id.giam);
        txtaddtocart = findViewById(R.id.addcart);
        imgpic = findViewById(R.id.foodpic);

    }
}