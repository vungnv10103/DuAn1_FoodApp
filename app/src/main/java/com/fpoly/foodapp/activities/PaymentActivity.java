package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.fpoly.foodapp.DAO.CartItemDAO;
import com.fpoly.foodapp.DAO.CartItemTempDAO;
import com.fpoly.foodapp.DAO.OderDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.CartItemAdapter;
import com.fpoly.foodapp.adapters.CartItemTempAdapter;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.CartTempModule;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvNumberOder, tvValueVoucher, tvName, tvPhone, tvAddress, tvToTalCost;
    private RecyclerView rcvListProduct;
    static CartItemDAO cartItemDAO;
    CartItemModule itemCart;
    ArrayList<CartTempModule> listCart;
    CartItemTempAdapter CartItemTempAdapter;
    static CartItemTempDAO cartItemTempDAO;
    static UsersDAO usersDAO;
    static OderDAO oderDAO;
    ArrayList<OderHistoryModel> listOder;
    public static int check = 0;
    private Button btnPurchase;
    private static final String TAG = "test";

    private LinearLayout selectLocation, selectVoucher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        selectLocation = findViewById(R.id.selectLocation);
        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this, SelectLocationActivity.class));
            }
        });
        tvName = findViewById(R.id.tvFullName);
        tvPhone = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);

        usersDAO = new UsersDAO(getApplicationContext());
        oderDAO = new OderDAO(getApplicationContext());
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int idUser = usersDAO.getIDUser(email);
        tvNumberOder = findViewById(R.id.tvNumberOder);
        String name = usersDAO.getNameUser(email);
        String phone = usersDAO.getPhone(email);
        String address = usersDAO.getAddress(email);

        tvName.setText(name + " | ");
        tvPhone.setText(phone);
        tvAddress.setText(address);


        listOder = (ArrayList<OderHistoryModel>) oderDAO.getAllByIdUser(idUser);
        btnPurchase = findViewById(R.id.btnPurchase);

        if (listOder != null) {
            int numberNotification = listOder.size();
            tvNumberOder.setText(" " + numberNotification);
        }


        toolbar = findViewById(R.id.toolBarCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Thanh toán");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cartItemDAO = new CartItemDAO(getApplicationContext());
        cartItemTempDAO = new CartItemTempDAO(getApplicationContext());
        rcvListProduct = findViewById(R.id.rcvListProductDetail);
        listCart = (ArrayList<CartTempModule>) cartItemTempDAO.getALL(idUser, 1);
        if (listCart.size() == 0) {
            btnPurchase.setText("Mua hàng(0)");
        } else {
            btnPurchase.setText("Mua hàng(" + listCart.size() + ")");
            CartItemTempAdapter = new CartItemTempAdapter(listCart, getApplicationContext());
            rcvListProduct.setAdapter(CartItemTempAdapter);
            rcvListProduct.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        }
        tvValueVoucher = findViewById(R.id.tvValueVoucher);
        selectVoucher = findViewById(R.id.selectVoucher);
        selectVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this, DealsActivity.class));

            }
        });
        if (check > 0) {
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("discount");
            int discount = bundle.getInt("value");
            tvValueVoucher.setText("- " + discount + " %");
        }
        double price = cartItemTempDAO.getToTalPrice();
        tvToTalCost = findViewById(R.id.tvToTalCost);
//        tvToTalCost.setText(String.format("%.2f", price));
        double abc = 0.0;
        for (int i = 0; i < listCart.size(); i++) {
            abc += cartItemTempDAO.getToTal(i,0);
        }
        tvToTalCost.setText(String.format("%.2f", abc));

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItemDAO.deleteSelected(1) > 0) {
                    if (cartItemTempDAO.deleteByCheckSelected(1) > 0) {
                        Log.d(TAG, "onClick: " + "removed products as by");
                        Toast.makeText(PaymentActivity.this, "Mua hàng thành công !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                        finishAffinity();
                    }

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_home:
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                return true;
            case R.id.action_support:

                return true;
            case R.id.action_order:
                startActivity(new Intent(PaymentActivity.this, OrderHistoryActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int idUser = usersDAO.getIDUser(email);
        listCart = (ArrayList<CartTempModule>) cartItemTempDAO.getALL(idUser, 1);
        double price = cartItemTempDAO.getToTalPrice();
        tvToTalCost = findViewById(R.id.tvToTalCost);

//        tvToTalCost.setText(String.format("%.2f", price));
        double abc = 0.0;
        for (int i = 0; i < listCart.size(); i++) {
            abc += cartItemTempDAO.getToTal(i,0);
        }
        tvToTalCost.setText(String.format("%.2f", abc));

    }
}