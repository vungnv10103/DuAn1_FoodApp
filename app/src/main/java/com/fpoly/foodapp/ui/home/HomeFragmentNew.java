package com.fpoly.foodapp.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.CategoriesAdapter;
import com.fpoly.foodapp.adapters.RecommendAdapter;
import com.fpoly.foodapp.adapters.SlideShowAdapter;
import com.fpoly.foodapp.modules.Category;
import com.fpoly.foodapp.modules.Food;
import com.fpoly.foodapp.modules.photo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment {


    RecyclerView recyclerViewMain, recyclerViewMainPopular;
    List<Category> list;
    RecommendAdapter adapter ;
    List<Food> foodList;
    CategoriesAdapter categoriesAdapter;

    BottomNavigationView viewBottom;
    private ViewPager2 viewPager2 ;
    private CircleIndicator3 indicator3;
    private List<photo> listPhotos;
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

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);

        recyclerViewMainPopular = view.findViewById(R.id.Main_popular);
        recyclerViewMain = view.findViewById(R.id.MainView);
        list = new ArrayList<>();
        list.add(new Category(R.drawable.cat_1, "Pizza"));
        list.add(new Category(R.drawable.cat_2, "Burger"));
        list.add(new Category(R.drawable.cat_3, "Hot dog"));
        list.add(new Category(R.drawable.cat_4, "Drink"));
        list.add(new Category(R.drawable.cat_5, "Donut"));
        categoriesAdapter = new CategoriesAdapter(list, getContext());

        recyclerViewMain.setAdapter(categoriesAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setNestedScrollingEnabled(false);

        listSlideShow();
        listRecommended();

        viewPager2 = view.findViewById(R.id.viewpager_slideshow);
        indicator3 = view.findViewById(R.id.indicator);
        SlideShowAdapter adapterSlideShow = new SlideShowAdapter(listPhotos);
        viewPager2.setAdapter(adapterSlideShow);
        indicator3.setViewPager(viewPager2);

        // lắng nghe sự kiện auto run
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });
        // viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        return view;
    }

    private void listSlideShow() {
        listPhotos = new ArrayList<>();
        listPhotos.add(new photo(R.drawable.fast_food));
        listPhotos.add(new photo(R.drawable.monan1));
        listPhotos.add(new photo(R.drawable.mon2));
        listPhotos.add(new photo(R.drawable.mon3));
        listPhotos.add(new photo(R.drawable.mon4));
        listPhotos.add(new photo(R.drawable.fruit_juice_banner));

    }

    private void listRecommended() {
        foodList = new ArrayList<>();
        foodList.add(new Food(R.drawable.pizza1 , "Pepperoni Pizza" , 13.0 , R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.burger , "Cheese Burger" , 15.2 , R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.pizza3 , "Vegetable Pizza" , 11.0 , R.drawable.plus_circle));
        adapter = new RecommendAdapter(getContext() , foodList);
        recyclerViewMainPopular.setAdapter(adapter);

        recyclerViewMainPopular.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.HORIZONTAL , false));
        recyclerViewMainPopular.setHasFixedSize(true);
        recyclerViewMainPopular.setNestedScrollingEnabled(false);

    }

}
