package com.fpoly.foodapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

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

import com.fpoly.foodapp.DAO.CategoryDAO;
//import com.fpoly.foodapp.DAO.ProductDAO;
import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.AddItemCategoryActivity;
import com.fpoly.foodapp.activities.AddItemRecommendActivity;
import com.fpoly.foodapp.adapters.SlideShowAdapter;
import com.fpoly.foodapp.adapters.category.AddCategoryItemAdapter;
import com.fpoly.foodapp.adapters.category.ItemCategory;
import com.fpoly.foodapp.adapters.category.ItemCategoryAdapter;
import com.fpoly.foodapp.adapters.item_product.ItemProduct;
import com.fpoly.foodapp.adapters.item_product.ItemProductAdapter;
import com.fpoly.foodapp.adapters.product.ListProduct;
import com.fpoly.foodapp.adapters.product.ListProductsAdapter;
import com.fpoly.foodapp.adapters.recommend.AddRecommendedItemAdapter;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.adapters.recommend.RecommendAdapterNew;
import com.fpoly.foodapp.modules.AddCategoryModule;
import com.fpoly.foodapp.modules.AddRecommendModule;
import com.fpoly.foodapp.modules.photo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment {


    RecyclerView recyclerCategory, recyclerViewRecommend, recyclerViewProduct;
    ListProductsAdapter listProductsAdapter;

    ItemCategoryAdapter categoryAdapter;
    CategoryDAO categoryDAO;
    List<ItemCategory> listCate;

//    static ProductDAO productDAO;
    List<ItemProduct> listProduct;
    ItemProductAdapter itemProductAdapter;

    RecommendAdapterNew recommendAdapterNew;
    ArrayList<ItemRecommend> listRecommend;
    static RecommendDAO recommendDAO;

    AddRecommendedItemAdapter addRecommendedItemAdapter;
    List<AddRecommendModule> addRecommend;

    AddCategoryItemAdapter addCategoryItemAdapter;
    List<AddCategoryModule> addCategory;


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
            if (viewPager2.getCurrentItem() == listPhotos.size() - 1) {
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
        categoryDAO = new CategoryDAO(getContext());
        edSearch = view.findViewById(R.id.edSearchHome);
        imgDeleteSearch = view.findViewById(R.id.imgDeleteSearch);
        itemProductAdapter = new ItemProductAdapter(getContext());

        recyclerViewRecommend = view.findViewById(R.id.rcvRecommend);
        recyclerCategory = view.findViewById(R.id.rcvCategory);
        recyclerViewProduct = view.findViewById(R.id.rcvProduct);
        addCategory = new ArrayList<>();
        addRecommend = new ArrayList<>();
        recommendDAO = new RecommendDAO(getContext());
        usersDAO = new UsersDAO(getActivity());
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code
                v.getContext().startActivity(new Intent(getContext(), AddItemCategoryActivity.class));
            }
        });

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        tvUserName = view.findViewById(R.id.tvUserNameHome);
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
            }
        });
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
        listProductsAdapter = new ListProductsAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewProduct.setLayoutManager(linearLayoutManager);
        listProductsAdapter.setData(getListProduct());
        recyclerViewProduct.setAdapter(listProductsAdapter);
        listSlideShow();
        listCategory();
        listRecommend();
        int begin_index = email.indexOf("@");
        int end_index = email.indexOf(".");
        String domain_name = email.substring(begin_index + 1, end_index);
        if (domain_name.equals("merchant")){

            showMenuAddItemRecommend();
            showMenuAddItemCategory();
        }

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
                recommendAdapterNew.getFilter().filter(s.toString());
            }
        });
        return view;
    }
    private ArrayList<ListProduct> getListProduct() {
        ArrayList<ListProduct> list = new ArrayList<>();
        ArrayList<ItemProduct> list1 = new ArrayList<>();
        ArrayList<ItemProduct> list2 = new ArrayList<>();
        list1.add(new ItemProduct(R.drawable.pizza1_new, "Pizza", 13.5, R.drawable.plus_circle));
        list1.add(new ItemProduct(R.drawable.burger1, "Burger", 15.7, R.drawable.plus_circle));
        list1.add(new ItemProduct(R.drawable.fries1, "Fried", 16.2, R.drawable.plus_circle));
        list1.add(new ItemProduct(R.drawable.strawberry, "Strawberry", 13.5, R.drawable.plus_circle));
        list1.add(new ItemProduct(R.drawable.watermelon, "Watermelon", 15.7, R.drawable.plus_circle));
        list1.add(new ItemProduct(R.drawable.burger2, "Burger", 16.2, R.drawable.plus_circle));
        list1.add(new ItemProduct(R.drawable.pizza2, "Pizza", 13.5, R.drawable.plus_circle));
        list1.add(new ItemProduct(R.drawable.icecream2, "Ice cream", 13.5, R.drawable.plus_circle));

        list2.add(new ItemProduct(R.drawable.burger4, "Burger", 13.5, R.drawable.plus_circle));
        list2.add(new ItemProduct(R.drawable.icecream4, "Ice cream", 15.7, R.drawable.plus_circle));
        list2.add(new ItemProduct(R.drawable.mango, "Mango", 16.2, R.drawable.plus_circle));
        list2.add(new ItemProduct(R.drawable.carrot, "Carrot", 13.5, R.drawable.plus_circle));
        list2.add(new ItemProduct(R.drawable.pizza3_new, "Pizza", 15.7, R.drawable.plus_circle));
        list2.add(new ItemProduct(R.drawable.fries4, "Fried", 16.2, R.drawable.plus_circle));
        list2.add(new ItemProduct(R.drawable.sandwich3, "Sandwich", 13.5, R.drawable.plus_circle));
        list2.add(new ItemProduct(R.drawable.pizza7, "Pizza", 14.6, R.drawable.plus_circle));


        list.add(new ListProduct("Món mới trong tuần", list1));
        list.add(new ListProduct("Được yêu thích", list2));



        return list;
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
    private void listCategory() {
        listCate =  categoryDAO.getALL();
        if (listCate.size() == 0) {
            //Toast.makeText(getContext(), "Bấm vào avatar góc trái để thêm item category.", Toast.LENGTH_SHORT).show();
        }
        else {
            categoryAdapter = new ItemCategoryAdapter(getContext(), listCate);
            recyclerCategory.setAdapter(categoryAdapter);

            recyclerCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            recyclerCategory.setHasFixedSize(true);
            recyclerCategory.setNestedScrollingEnabled(false);
        }



    }
    public void listRecommend() {
        listRecommend = (ArrayList<ItemRecommend>) recommendDAO.getALL();
        if (listRecommend.size() == 0) {
           // Toast.makeText(getContext(), "Bấm vào TextView User để thêm item recommend.", Toast.LENGTH_SHORT).show();
        }
        recommendAdapterNew = new RecommendAdapterNew(getContext(), listRecommend);
        recyclerViewRecommend.setAdapter(recommendAdapterNew);

        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewRecommend.setHasFixedSize(true);
        recyclerViewRecommend.setNestedScrollingEnabled(false);

    }
    private void showMenuAddItemCategory(){
        listCate = new ArrayList<>();
        listCate =  categoryDAO.getALL();
        categoryAdapter = new ItemCategoryAdapter(getContext(), listCate);
        addCategory.add(new AddCategoryModule(R.drawable.plus_circle));
        addCategoryItemAdapter = new AddCategoryItemAdapter(getContext(), addCategory);

        ConcatAdapter concatAdapter = new ConcatAdapter(categoryAdapter, addCategoryItemAdapter);
        recyclerCategory.setAdapter(concatAdapter);
    }
    private void showMenuAddItemRecommend(){
        listRecommend = (ArrayList<ItemRecommend>) recommendDAO.getALL();
        recommendAdapterNew = new RecommendAdapterNew(getContext(), listRecommend);
        addRecommend.add(new AddRecommendModule(R.drawable.plus_circle));
        addRecommendedItemAdapter = new AddRecommendedItemAdapter(getContext(), addRecommend);

        ConcatAdapter concatAdapter = new ConcatAdapter(recommendAdapterNew, addRecommendedItemAdapter);
        recyclerViewRecommend.setAdapter(concatAdapter);
    }




}
