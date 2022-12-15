package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.ProductHiddenAdapter;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;

import java.util.List;

public class ProductHiddenActivity extends AppCompatActivity {
    private RecyclerView rcvProductHidden;
    static RecommendDAO recommendDAO;
    ProductHiddenAdapter productHiddenAdapter;
    List<ItemRecommend> listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_hidden);
        rcvProductHidden = findViewById(R.id.rcvProductHidden);
        recommendDAO = new RecommendDAO(getApplicationContext());
        listProduct = recommendDAO.getALL(1);
        productHiddenAdapter = new ProductHiddenAdapter(getApplicationContext(), listProduct);
        rcvProductHidden.setAdapter(productHiddenAdapter);
        rcvProductHidden.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listProduct = recommendDAO.getALL(1);
        productHiddenAdapter = new ProductHiddenAdapter(getApplicationContext(), listProduct);
        rcvProductHidden.setAdapter(productHiddenAdapter);
        rcvProductHidden.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
    }
}