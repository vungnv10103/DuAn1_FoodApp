package com.fpoly.foodapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.LoginActivity;
import com.fpoly.foodapp.adapters.HomeHorAdapter;
import com.fpoly.foodapp.adapters.HomeVerAdapter;
import com.fpoly.foodapp.adapters.UpdateVerticalRec;
import com.fpoly.foodapp.modules.HomeHorModule;
import com.fpoly.foodapp.modules.HomeVerModule;

import java.util.ArrayList;

public class HomeFragment extends Fragment  implements UpdateVerticalRec{
    RecyclerView home_hor_rec, home_ver_rec;
    ArrayList<HomeHorModule> listHor;
    ArrayList<HomeVerModule> listVer;
    HomeHorAdapter homeHorAdapter;
    HomeVerAdapter homeVerAdapter;
    private EditText edSearch;
    ImageView imgAccount;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_home, container, false);
        imgAccount = root.findViewById(R.id.imgaccount);
        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , LoginActivity.class);
                startActivity(intent);

            }
        });
        edSearch = root.findViewById(R.id.edSearch);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Toast.makeText(getContext(), "text: " + editable.toString(), Toast.LENGTH_SHORT).show();



            }
        });



        /*
                Horizontal RecycleView
         */
        home_hor_rec = root.findViewById(R.id.home_hor_rec);
        listHor = new ArrayList<>();
        listHor.add(new HomeHorModule(R.drawable.pizza, "Pizza"));
        listHor.add(new HomeHorModule(R.drawable.hamburger, "Hamburger"));
        listHor.add(new HomeHorModule(R.drawable.fried_potatoes, "Fries"));
        listHor.add(new HomeHorModule(R.drawable.ice_cream, "Ice Cream"));
        listHor.add(new HomeHorModule(R.drawable.sandwich, "Sandwich"));
        listHor.add(new HomeHorModule(R.drawable.fruit_juice, "Fruit Juice"));


//        homeHorAdapter = new HomeHorAdapter(getActivity(), listHor);
        homeHorAdapter = new HomeHorAdapter( this, getActivity() , listHor);
        home_hor_rec.setAdapter(homeHorAdapter);

        home_hor_rec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        home_hor_rec.setHasFixedSize(true);
        home_hor_rec.setNestedScrollingEnabled(false);

        /*
                Vertical RecycleView
         */

        home_ver_rec = root.findViewById(R.id.home_ver_rec);
        listVer = new ArrayList<>();

        homeVerAdapter = new HomeVerAdapter(getActivity(), listVer);
        home_ver_rec.setAdapter(homeVerAdapter);
        home_ver_rec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));



        return root;
    }


    @Override
    public void callBack(int pos, ArrayList<HomeVerModule> list) {
        homeVerAdapter = new HomeVerAdapter(getContext() , list);
        homeVerAdapter.notifyDataSetChanged();
        home_ver_rec.setAdapter(homeVerAdapter);
    }
}