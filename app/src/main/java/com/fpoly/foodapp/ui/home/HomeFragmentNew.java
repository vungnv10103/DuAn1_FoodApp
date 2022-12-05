package com.fpoly.foodapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
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
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.AddRecommendedItemAdapter;
import com.fpoly.foodapp.adapters.CategoriesAdapter;
import com.fpoly.foodapp.adapters.RecommendAdapter;
import com.fpoly.foodapp.adapters.SlideShowAdapter;
import com.fpoly.foodapp.adapters.UpdateVerticalRec;
import com.fpoly.foodapp.modules.AddRecommendModule;
import com.fpoly.foodapp.modules.CategoryModule;
import com.fpoly.foodapp.modules.RecommendedModule;
import com.fpoly.foodapp.modules.HomeVerModule;
import com.fpoly.foodapp.modules.photo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment implements UpdateVerticalRec {


    RecyclerView recyclerCategory, recyclerViewRecommend;
    ArrayList<CategoryModule> listCategories;
    ArrayList<AddRecommendModule> listAddRecommend;
    ArrayList<RecommendedModule> listRecommend;
    RecommendAdapter recommendAdapter;
    AddRecommendedItemAdapter addRecommendedItemAdapter;

    CategoriesAdapter categoriesAdapter;
    EditText edSearch;
    TextView tvUserName;
    ImageView imgAvatar, imgDeleteSearch;
    static UsersDAO usersDAO;


    BottomNavigationView viewBottom;
    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator3;
    private List<photo> listPhotos;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == listCategories.size() - 1) {
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

        usersDAO = new UsersDAO(getActivity());
        imgAvatar = view.findViewById(R.id.imgAvatar);

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        tvUserName = view.findViewById(R.id.tvUserNameHome);
        String userName = pref.getString("name", "");
        if (userName.isEmpty() || userName.equals("null")) {
            tvUserName.setText("Username");
        } else {
            tvUserName.setText(userName);
        }

        String email = pref.getString("EMAIL", "");

        try {
            String path = usersDAO.getUriImg(email);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(path));
            imgAvatar.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        recyclerViewRecommend = view.findViewById(R.id.rcvRecommend);
        recyclerCategory = view.findViewById(R.id.rcvCategory);
        edSearch = view.findViewById(R.id.edSearchHome);
        imgDeleteSearch = view.findViewById(R.id.imgDeleteSearch);


        listCategories = new ArrayList<>();
        listCategories.add(new CategoryModule(R.drawable.cat_1, "Pizza"));
        listCategories.add(new CategoryModule(R.drawable.cat_2, "Burger"));
        listCategories.add(new CategoryModule(R.drawable.fried_potatoes, "Fried"));
        listCategories.add(new CategoryModule(R.drawable.ice_cream, "Ice_cream"));
        listCategories.add(new CategoryModule(R.drawable.sandwich, "Sandwich"));
        listCategories.add(new CategoryModule(R.drawable.fruit_juice, "Fruit Juice"));
        categoriesAdapter = new CategoriesAdapter(this, getActivity(), listCategories);

        recyclerCategory.setAdapter(categoriesAdapter);

        recyclerCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerCategory.setHasFixedSize(true);
        recyclerCategory.setNestedScrollingEnabled(false);

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
                } else {
                    imgDeleteSearch.setVisibility(View.INVISIBLE);
                }
                recommendAdapter.getFilter().filter(s.toString());
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
        listRecommend = new ArrayList<>();
        listAddRecommend = new ArrayList<>();

        listRecommend.add(new RecommendedModule(R.drawable.pizza1, "Pepperoni Pizza", 13.0, R.drawable.plus_circle));
        listRecommend.add(new RecommendedModule(R.drawable.pizza3, "Vegetable Pizza", 11.0, R.drawable.plus_circle));
        listRecommend.add(new RecommendedModule(R.drawable.pizza5, "Tomato Pizza", 12.6, R.drawable.plus_circle));
        listRecommend.add(new RecommendedModule(R.drawable.pizza6, "Cheese Pizza", 10.5, R.drawable.plus_circle));
        listRecommend.add(new RecommendedModule(R.drawable.pizza7, "Lamacun Pizza", 15.2, R.drawable.plus_circle));
        listRecommend.add(new RecommendedModule(R.drawable.pizza8, "Beef Pizza", 14.7, R.drawable.plus_circle));
        listAddRecommend.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));

        recommendAdapter = new RecommendAdapter(getContext(), listRecommend);
        addRecommendedItemAdapter = new AddRecommendedItemAdapter(getContext(), listAddRecommend);

        recyclerViewRecommend.setAdapter(addRecommendedItemAdapter);
        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewRecommend.setHasFixedSize(true);
        recyclerViewRecommend.setNestedScrollingEnabled(false);



    }


    public void listUpdate() {
        listRecommend = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(getActivity(), listRecommend);
        recyclerViewRecommend.setAdapter(recommendAdapter);
        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }


    @Override
    public void callBack(int pos, ArrayList<HomeVerModule> list) {

    }

    @Override
    public void callBackNew(int pos, ArrayList<RecommendedModule> list, ArrayList<AddRecommendModule> listNew) {
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int begin_index = email.indexOf("@");
        int end_index = email.indexOf(".");
        String domain_name = email.substring(begin_index + 1, end_index);
        recommendAdapter = new RecommendAdapter(getContext(), list);
        addRecommendedItemAdapter = new AddRecommendedItemAdapter(getContext(),listNew);
        recommendAdapter.notifyDataSetChanged();
        recyclerViewRecommend.setAdapter(recommendAdapter);

        if (domain_name.equals("merchant")) {
            ConcatAdapter concatAdapter = new ConcatAdapter(recommendAdapter, addRecommendedItemAdapter);
            recommendAdapter.notifyDataSetChanged();
            recyclerViewRecommend.setAdapter(concatAdapter);
        }
    }
}
