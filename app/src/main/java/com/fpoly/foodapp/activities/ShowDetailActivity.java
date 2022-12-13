package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.DAO.CartItemDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.modules.CartItemModule;

import java.io.IOException;


public class ShowDetailActivity extends AppCompatActivity {
    ImageView imgProduct;
    TextView tvTitle, tvPrice, tvQuantity;
    TextView tvRate, tvTime, tvCalo;
    ImageView imgPlus, imgMinus;
    TextView tvDescription;
    TextView tvTotalPrices, tvAddToCart;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    int quantity = 1;
    static CartItemDAO CartItemDAO;
    CartItemModule item;
    static UsersDAO usersDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");


        checkImg();
        CartItemDAO = new CartItemDAO(getApplicationContext());
        usersDAO = new UsersDAO(getApplicationContext());

        tvQuantity.setText(String.valueOf(quantity));

        int idProduct = bundle.getInt("id");
        String title = bundle.getString("title");
        double price = bundle.getDouble("price");
        double rate = bundle.getDouble("rate");
        String timeDelay = bundle.getString("time_delay");
        String description = bundle.getString("description");
        double calo = bundle.getDouble("calo");

        tvTitle.setText(title);
        tvPrice.setText(String.format("$ %s", price));
        tvRate.setText(rate + "");
        tvTime.setText(timeDelay + " min");
        tvCalo.setText(calo + " calories");
        tvDescription.setText(description);
        tvTotalPrices.setText("$ " + String.format("%.2f", price));

        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
                double total_prices = quantity * price;
                tvTotalPrices.setText("$ " + String.format("%.2f", total_prices));
            }
        });

        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity--;
                    tvQuantity.setText(String.valueOf(quantity));
                    double total_prices = quantity * price;
                    tvTotalPrices.setText("$ " + String.format("%.2f", total_prices));
                } else {
                    Toast.makeText(ShowDetailActivity.this, "số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });



        tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                String email = pref.getString("EMAIL", "");
                item = new CartItemModule();
                int quanti = Integer.parseInt(tvQuantity.getText().toString());
                double cost_total = price * quanti;

                item.idUser = usersDAO.getIDUser(email);
                item.idRecommend = idProduct;
                item.check = 0;
                item.name = title;
                item.cost = cost_total;
                item.quantities = quanti;
                if (CartItemDAO.insert(item) > 0) {
                    Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(ShowDetailActivity.this, MainActivity.class));
                finishAffinity();
            }
        });

    }

    private void init() {
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.pricetxt);
        tvQuantity = findViewById(R.id.txtnumberitem);
        tvDescription = findViewById(R.id.tvDescription);
        tvTotalPrices = findViewById(R.id.tvTotalPrice);
        tvRate = findViewById(R.id.tvRate);
        tvTime = findViewById(R.id.tvTime);
        tvCalo = findViewById(R.id.tvCalo);
        imgPlus = findViewById(R.id.imgPlus);
        imgMinus = findViewById(R.id.imgMinus);
        tvAddToCart = findViewById(R.id.tvAddToCart);
        imgProduct = findViewById(R.id.imgFood);

    }
    public void checkImg(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        int img = bundle.getInt("image");
        String uri = bundle.getString("image_resource");
        if (uri != null){
            Bitmap bitmap  = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), Uri.parse(uri));
                imgProduct.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            imgProduct.setImageResource(img);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
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