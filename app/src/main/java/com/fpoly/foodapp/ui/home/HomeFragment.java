package com.fpoly.foodapp.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.HomeHorAdapter;
import com.fpoly.foodapp.adapters.HomeVerAdapter;
import com.fpoly.foodapp.modules.HomeHorModule;
import com.fpoly.foodapp.modules.HomeVerModule;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView home_hor_rec, home_ver_rec;
    ArrayList<HomeHorModule> listHor;
    ArrayList<HomeVerModule> listVer;
    HomeHorAdapter homeHorAdapter;
    HomeVerAdapter homeVerAdapter;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_home, container, false);
        home_hor_rec = root.findViewById(R.id.home_hor_rec);
        listHor = new ArrayList<>();
        listHor.add(new HomeHorModule(R.drawable.pizza, "Pizza"));
        listHor.add(new HomeHorModule(R.drawable.hamburger, "Hamburger"));
        listHor.add(new HomeHorModule(R.drawable.fried_potatoes, "Fries"));
        listHor.add(new HomeHorModule(R.drawable.ice_cream, "Ice Cream"));
        listHor.add(new HomeHorModule(R.drawable.sandwich, "Sandwich"));

        listHor.add(new HomeHorModule(R.drawable.pizza, "Pizza"));
        listHor.add(new HomeHorModule(R.drawable.hamburger, "Hamburger"));
        listHor.add(new HomeHorModule(R.drawable.fried_potatoes, "Fries"));
        listHor.add(new HomeHorModule(R.drawable.ice_cream, "Ice Cream"));
        listHor.add(new HomeHorModule(R.drawable.sandwich, "Sandwich"));

        homeHorAdapter = new HomeHorAdapter(getActivity(), listHor);
        home_hor_rec.setAdapter(homeHorAdapter);
        home_hor_rec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        home_hor_rec.setHasFixedSize(true);
        home_hor_rec.setNestedScrollingEnabled(false);

        home_ver_rec = root.findViewById(R.id.home_ver_rec);
        listVer = new ArrayList<>();
        listVer.add(new HomeVerModule(R.drawable.pizza1, "Pizza 1","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza2, "Pizza 2","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza3, "Pizza 3","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza4, "Pizza 4","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza5, "Pizza 5","10:00 - 23:00","4.9","Min - $40"));

        listVer.add(new HomeVerModule(R.drawable.pizza1, "Pizza 1","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza2, "Pizza 2","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza3, "Pizza 3","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza4, "Pizza 4","10:00 - 23:00","4.9","Min - $40"));
        listVer.add(new HomeVerModule(R.drawable.pizza5, "Pizza 5","10:00 - 23:00","4.9","Min - $40"));

        homeVerAdapter = new HomeVerAdapter(getActivity(), listVer);
        home_ver_rec.setAdapter(homeVerAdapter);
        home_ver_rec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        home_ver_rec.setHasFixedSize(true);
        home_ver_rec.setNestedScrollingEnabled(false);




        return root;
    }


}