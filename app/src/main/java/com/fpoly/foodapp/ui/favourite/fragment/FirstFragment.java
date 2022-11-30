package com.fpoly.foodapp.ui.favourite.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.FeaturedAdapter;
import com.fpoly.foodapp.adapters.FeaturedVerAdapter;
import com.fpoly.foodapp.modules.FeaturedModule;
import com.fpoly.foodapp.modules.FeaturedVerModule;
import com.fpoly.foodapp.modules.Food;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    ///////Hor
    List<FeaturedModule> featuredModules;
    RecyclerView recyclerView;
    FeaturedAdapter featuredAdapter;
    //////Ver
    List<Food> foods;
    RecyclerView recyclerView2;
    FeaturedVerAdapter featuredVerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        swipeRefreshLayout = new SwipeRefreshLayout(getContext());
        //////////////hor
        recyclerView = view.findViewById(R.id.featured_hor_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        featuredModules = new ArrayList<>();
        featuredModules.add(new FeaturedModule(R.drawable.fav1, "Featured 1", "Description 1"));
        featuredModules.add(new FeaturedModule(R.drawable.fav2, "Featured 2", "Description 2"));
        featuredModules.add(new FeaturedModule(R.drawable.fav3, "Featured 3", "Description 3"));
        featuredModules.add(new FeaturedModule(R.drawable.fav3, "Featured 4", "Description 4"));
        featuredModules.add(new FeaturedModule(R.drawable.fav3, "Featured 5", "Description 5"));

        featuredAdapter = new FeaturedAdapter(featuredModules);
        recyclerView.setAdapter(featuredAdapter);
///////////////ver

        recyclerView2 = view.findViewById(R.id.featured_ver_rec);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        foods = new ArrayList<>();
        foods.add(new Food(R.drawable.ver1, "Creamy sticky rice", 2.5, R.drawable.plus_circle));
        foods.add(new Food(R.drawable.ver2, "Hamburger", 2.8, R.drawable.plus_circle));
        foods.add(new Food(R.drawable.ver3, "Pasta", 2.8, R.drawable.plus_circle));
        foods.add(new Food(R.drawable.ver3, "Pasta 2", 2.8, R.drawable.plus_circle));
        foods.add(new Food(R.drawable.ver3, "Pasta 3", 2.8, R.drawable.plus_circle));


        featuredVerAdapter = new FeaturedVerAdapter(getContext(), foods);
        recyclerView2.setAdapter(featuredVerAdapter);

        return view;
    }
}