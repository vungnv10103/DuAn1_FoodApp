package com.fpoly.foodapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.CategoriesAdapter;
import com.fpoly.foodapp.adapters.RecommendAdapter;
import com.fpoly.foodapp.adapters.SlideShowAdapter;
import com.fpoly.foodapp.adapters.UpdateVerticalRec;
import com.fpoly.foodapp.modules.Category;
import com.fpoly.foodapp.modules.Food;
import com.fpoly.foodapp.modules.HomeVerModule;
import com.fpoly.foodapp.modules.photo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment implements UpdateVerticalRec {


    RecyclerView recyclerViewMain, recyclerViewMainPopular;
    ArrayList<Category> list;
    RecommendAdapter adapter;
    ArrayList<Food> foodList;
    CategoriesAdapter categoriesAdapter;
    SearchView searchView;
    EditText edSearch;
    TextView tvUserName;
    ImageView imgAvatar, imgDeleteSearch;

    BottomNavigationView viewBottom;
    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator3;
    private List<photo> listPhotos;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == list.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    public HomeFragmentNew() {

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        tvUserName = view.findViewById(R.id.tvUserNameHome);
        tvUserName.setText(pref.getString("name", ""));
        imgAvatar = view.findViewById(R.id.imgAvatar);
        String path = pref.getString("IMG", "");
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgAvatar.setImageBitmap(bitmap);
        recyclerViewMainPopular = view.findViewById(R.id.Main_popular);
        recyclerViewMain = view.findViewById(R.id.MainView);
        edSearch = view.findViewById(R.id.edSearchHome);
        imgDeleteSearch = view.findViewById(R.id.imgDeleteSearch);


        list = new ArrayList<>();
        list.add(new Category(R.drawable.cat_1, "Pizza"));
        list.add(new Category(R.drawable.cat_2, "Burger"));
        list.add(new Category(R.drawable.fried_potatoes, "Fried"));
        list.add(new Category(R.drawable.ice_cream, "Ice_cream"));
        list.add(new Category(R.drawable.sandwich, "Sandwich"));
        list.add(new Category(R.drawable.fruit_juice, "Fruit Juice"));
        categoriesAdapter = new CategoriesAdapter(this, getActivity(), list);

        recyclerViewMain.setAdapter(categoriesAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setNestedScrollingEnabled(false);

        listSlideShow();
        listRecommended();
        listUpdate();

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

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edSearch.getText().toString().trim().isEmpty()) {
                    imgDeleteSearch.setVisibility(View.VISIBLE);
                    imgDeleteSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edSearch.setText("");
                        }
                    });
                }else {
                    imgDeleteSearch.setVisibility(View.INVISIBLE);
                }
                adapter.getFilter().filter(s.toString());
            }
        });
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
        foodList.add(new Food(R.drawable.pizza1, "Pepperoni Pizza", 13.0, R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.pizza3, "Vegetable Pizza", 11.0, R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.pizza5, "Tomato Pizza", 12.6, R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.pizza6, "Cheese Pizza", 10.5, R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.pizza7, "Lamacun Pizza", 15.2, R.drawable.plus_circle));
        foodList.add(new Food(R.drawable.pizza8, "Beef Pizza", 14.7, R.drawable.plus_circle));
        adapter = new RecommendAdapter(getContext(), foodList);
        recyclerViewMainPopular.setAdapter(adapter);

        recyclerViewMainPopular.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewMainPopular.setHasFixedSize(true);
        recyclerViewMainPopular.setNestedScrollingEnabled(false);

    }

    public void listUpdate() {
        foodList = new ArrayList<>();
        adapter = new RecommendAdapter(getActivity(), foodList);
        recyclerViewMainPopular.setAdapter(adapter);
        recyclerViewMainPopular.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }


    @Override
    public void callBack(int pos, ArrayList<HomeVerModule> list) {

    }

    @Override
    public void callBackNew(int pos, ArrayList<Food> list) {
        adapter = new RecommendAdapter(getContext(), list);
        adapter.notifyDataSetChanged();
        recyclerViewMainPopular.setAdapter(adapter);
    }
}
