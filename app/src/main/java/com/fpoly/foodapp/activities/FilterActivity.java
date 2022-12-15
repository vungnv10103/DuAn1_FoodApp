package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.adapters.recommend.RecommendAdapterNew;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView imgBack , imgDelete;
    private View mHeaderView;
    private RecommendAdapterNew recommendAdapterNew;
    private EditText edtSeach ;
    private RecyclerView recyclerViewRecommend;
    private List<ItemRecommend> list = new ArrayList<>();
      static RecommendDAO recommendDAO ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(FilterActivity.this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();
        recommendDAO = new RecommendDAO(this);

        list = recommendDAO.getALL(0);
        recommendAdapterNew = new RecommendAdapterNew(this , list);
        edtSeach = findViewById(R.id.edFilter);
        imgDelete = findViewById(R.id.imgDeleteFilter);
        navigationView = findViewById(R.id.naviView);
        recyclerViewRecommend = findViewById(R.id.rclFilter);
        navigationView.setNavigationItemSelectedListener(this);
        mHeaderView = navigationView.getHeaderView(0);
        imgBack = mHeaderView.findViewById(R.id.imgBack);
        listRecommend();
        edtSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edtSeach.getText().toString().trim().isEmpty()) {
                    imgDelete.setVisibility(View.VISIBLE);
                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edtSeach.setText("");
                        }
                    });
                } else {
                    imgDelete.setVisibility(View.INVISIBLE);
                }
                recommendAdapterNew.getFilter().filter(s.toString());
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int index = item.getItemId();
        switch (index) {
            case R.id.a:
                Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
                break;
            case R.id.b:
                Toast.makeText(this, "b", Toast.LENGTH_SHORT).show();
                break;
            case R.id.c:
                Toast.makeText(this, "c", Toast.LENGTH_SHORT).show();
                break;
            case R.id.info:
                Toast.makeText(this, "info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backHome:
                startActivity(new Intent(FilterActivity.this, MainActivity.class));
                finishAffinity();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }
    public void listRecommend() {
        ItemRecommend item = new ItemRecommend();
        list = (ArrayList<ItemRecommend>) recommendDAO.getALL(0);
        recommendAdapterNew = new RecommendAdapterNew(FilterActivity.this, list);
        recyclerViewRecommend.setAdapter(recommendAdapterNew);

        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(FilterActivity.this, RecyclerView.HORIZONTAL, false));
        recyclerViewRecommend.setHasFixedSize(true);
        recyclerViewRecommend.setNestedScrollingEnabled(false);

    }
}