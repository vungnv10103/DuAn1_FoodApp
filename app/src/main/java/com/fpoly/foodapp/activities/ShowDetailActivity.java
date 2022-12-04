package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.DAO.demo_item_cart_dao;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.demo_cart_item_adapter;
import com.fpoly.foodapp.modules.demo_cart_item;
import com.fpoly.foodapp.ui.cart.CartFragment;

import java.io.IOException;


public class ShowDetailActivity extends AppCompatActivity {
    ImageView imgProduct;
    TextView tvTitle, tvPrice, tvQuantity;
    TextView tvRate, tvTime, tvCalo;
    ImageView imgPlus, imgMinus;
    TextView tvDescription;
    TextView tvTotalPrices, tvAddToCart;

    int quantity = 1;
    static demo_item_cart_dao demo_item_cart_dao;
    demo_cart_item item;
    static UsersDAO usersDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        checkImg();
        demo_item_cart_dao = new demo_item_cart_dao(getApplicationContext());
        usersDAO = new UsersDAO(getApplicationContext());

        tvQuantity.setText(String.valueOf(quantity));


        String title = bundle.getString("title");
        tvTitle.setText(title);
        double price = bundle.getDouble("price");
        tvPrice.setText(String.format("$ %s", price));


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

        tvDescription.setText("Description...................");

        tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                String email = pref.getString("EMAIL", "");
                item = new demo_cart_item();
                int quanti = Integer.parseInt(tvQuantity.getText().toString());
                double cost_total = price * quanti;
                item.idUser = usersDAO.getIDUser(email);
                item.check = 0;
                item.name = title;
                item.cost = cost_total;
                item.quantities = quanti;
                if (demo_item_cart_dao.insert(item) > 0) {
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
}