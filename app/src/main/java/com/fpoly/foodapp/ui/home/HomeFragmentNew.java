package com.fpoly.foodapp.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.Emptyadapter;
import com.fpoly.foodapp.adapters.recommended_adapter;
import com.fpoly.foodapp.adapters.viewpager2_adapter;
import com.fpoly.foodapp.modules.Category;
import com.fpoly.foodapp.modules.Food;
import com.fpoly.foodapp.modules.photo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment {


    RecyclerView recyclerView  , recyclerView2;
    List<Category> list;
    recommended_adapter adapter ;
    List<Food> foodList;
    Emptyadapter emptyadapter ;



    BottomNavigationView viewbottom ;
    private ViewPager2 viewPager2 ;
    private CircleIndicator3 indicator3;
    private List<photo> listphoto;
    private Handler handler =new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem()==list.size()-1){
                viewPager2.setCurrentItem(0);
            }else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }

        }
    };
    public HomeFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);

        recyclerView2 = view.findViewById(R.id.Main_popular);
        recyclerView = view.findViewById(R.id.MainView);
        list = new ArrayList<>();
        list.add(new Category(R.drawable.cat_1, "Pizza"));
        list.add(new Category(R.drawable.cat_2, "Burger"));
        list.add(new Category(R.drawable.cat_3, "Hotdog"));
        list.add(new Category(R.drawable.cat_4, "Drink"));
        list.add(new Category(R.drawable.cat_5, "Donut"));
        emptyadapter = new Emptyadapter(list, getContext());

        recyclerView.setAdapter(emptyadapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        listRecommended();

        viewPager2 = view.findViewById(R.id.viewpager);
        indicator3 = view.findViewById(R.id.indicator);

        listphoto = new ArrayList<>();
        listphoto.add(new photo(R.drawable.banner));
        listphoto.add(new photo(R.drawable.monan1));
        listphoto.add(new photo(R.drawable.mon2));
        listphoto.add(new photo(R.drawable.mon3));
        listphoto.add(new photo(R.drawable.mon4));


        viewpager2_adapter adapter = new viewpager2_adapter(listphoto);
        viewPager2.setAdapter(adapter);

        indicator3.setViewPager(viewPager2);

        //lắng nghe sự kiện auto run
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });
//        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        return view;
    }

    private void listRecommended() {
        foodList = new ArrayList<>();
        foodList.add(new Food(R.drawable.pizza1 , "pepeperoni pizza" , 13.0 , R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.burger , "cheese burger" , 15.2 , R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.pizza3 , "vegetable pizza" , 11.0 , R.drawable.plus_circle));
        adapter = new recommended_adapter(getContext() , foodList);
        recyclerView2.setAdapter(adapter);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.HORIZONTAL , false));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setNestedScrollingEnabled(false);

    }


    public void replace(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_layout , fragment).commit();
    }


}