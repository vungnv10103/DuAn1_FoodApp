package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.FavouriteAdapter;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.modules.ItemFavourite;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView rcvFavourite;
    FavouriteAdapter favouriteAdapter;
    List<ItemRecommend> listFavourite;

    static RecommendDAO recommendDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        init();

        listFavourite = new ArrayList<>();
        recommendDAO = new RecommendDAO(getApplicationContext());
        listFavourite = (ArrayList<ItemRecommend>) recommendDAO.getALLFavourite(1);
        if (listFavourite.size() == 0){
            Toast.makeText(this, "Chưa có món ăn yêu thích !", Toast.LENGTH_SHORT).show();
        }
        favouriteAdapter = new FavouriteAdapter(getApplicationContext(), listFavourite);
        rcvFavourite.setAdapter(favouriteAdapter);

        rcvFavourite.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        rcvFavourite.setHasFixedSize(true);
        rcvFavourite.setNestedScrollingEnabled(false);
    }

    private void init(){
        rcvFavourite = findViewById(R.id.rcvFavourite);
    }
}