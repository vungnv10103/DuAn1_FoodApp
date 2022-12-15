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
import com.fpoly.foodapp.modules.CartTempModule;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvNumberOder, tvValueVoucher;
    private RecyclerView rcvListProduct;
    static CartItemDAO cartItemDAO;
    ArrayList<CartTempModule> listCart;
    CartItemTempAdapter CartItemTempAdapter;
    static CartItemTempDAO cartItemTempDAO;
    static UsersDAO  usersDAO;
    static OderDAO oderDAO;
    ArrayList<OderHistoryModel> listOder;
    public static int check = 0;
    private Button btnPurchase;

    private LinearLayout selectVoucher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        usersDAO = new UsersDAO(getApplicationContext());
        oderDAO = new OderDAO(getApplicationContext());
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int idUser = usersDAO.getIDUser(email);
        tvNumberOder = findViewById(R.id.tvNumberOder);

        listOder = (ArrayList<OderHistoryModel>) oderDAO.getAllByIdUser(idUser);
        btnPurchase = findViewById(R.id.btnPurchase);

        if (listOder != null){
            int numberNotification = listOder.size();
            tvNumberOder.setText(" " + numberNotification);
        }




        toolbar = findViewById(R.id.toolBarCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Đơn Hàng");
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
        listCart = (ArrayList<CartTempModule>) cartItemTempDAO.getALL(idUser);
        if (listCart.size() == 0) {
            btnPurchase.setText("Mua hàng(0)");
        } else {
            btnPurchase.setText("Mua hàng(" + listCart.size() + ")");
            CartItemTempAdapter = new CartItemTempAdapter(listCart, getApplicationContext());
            rcvListProduct.setAdapter(CartItemTempAdapter);
            rcvListProduct.setLayoutManager(new LinearLayoutManager(getApplicationContext() ,RecyclerView.VERTICAL, false));
        }
        tvValueVoucher = findViewById(R.id.tvValueVoucher);
        selectVoucher = findViewById(R.id.selectVoucher);
        selectVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this, DealsActivity.class));

            }
        });
        if (check > 0){
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("discount");
            int discount = bundle.getInt("value");
            tvValueVoucher.setText("- " +discount+" %");
        }







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
}