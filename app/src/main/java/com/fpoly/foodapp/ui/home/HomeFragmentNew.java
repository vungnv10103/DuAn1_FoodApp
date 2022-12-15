package com.fpoly.foodapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.foodapp.DAO.CategoryDAO;
import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.DAO.StatisticalDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.AddItemProductActivity;
import com.fpoly.foodapp.activities.FilterActivity;
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
import com.fpoly.foodapp.adapters.recommend.ItemStatisticalRecommend;
import com.fpoly.foodapp.adapters.recommend.RecommendAdapterNew;
import com.fpoly.foodapp.modules.AddCategoryModule;
import com.fpoly.foodapp.modules.AddRecommendModule;
import com.fpoly.foodapp.modules.photo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment {

    RecyclerView recyclerCategory, recyclerViewRecommend, recyclerViewProduct, recyclerViewRecommendMain;
    ListProductsAdapter listProductsAdapter;

    ItemCategoryAdapter categoryAdapter;
    CategoryDAO categoryDAO;
    List<ItemCategory> listCate;

    //    static ProductDAO productDAO;
    List<ItemProduct> listProduct;
    ItemProductAdapter itemProductAdapter;

    RecommendAdapterNew recommendAdapterNew;
    ArrayList<ItemRecommend> listRecommend;
    ArrayList<ItemRecommend> listRecommendOld;
    ArrayList<ItemRecommend> listRecommendNew;
    static RecommendDAO recommendDAO;

    static StatisticalDAO statisticalDAO;
    ArrayList<ItemStatisticalRecommend> listItemStatis;

    AddRecommendedItemAdapter addRecommendedItemAdapter;
    List<AddRecommendModule> addRecommend;

    AddCategoryItemAdapter addCategoryItemAdapter;
    List<AddCategoryModule> addCategory;


    EditText edSearch;
    TextView tvUserName, tvSeeMore, tvContent;
    ImageView imgAvatar, imgDeleteSearch;
    static UsersDAO usersDAO;

    String mLocation = "";

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;


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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        recyclerViewRecommend = view.findViewById(R.id.rcvRecommend);
        recyclerCategory = view.findViewById(R.id.rcvCategory);
        recyclerViewProduct = view.findViewById(R.id.rcvProduct);
        recyclerViewRecommendMain = view.findViewById(R.id.rcvRecommendMain);
        addCategory = new ArrayList<>();
        addRecommend = new ArrayList<>();
        recommendDAO = new RecommendDAO(getContext());
        statisticalDAO = new StatisticalDAO(getContext());
        usersDAO = new UsersDAO(getActivity());
        tvContent = view.findViewById(R.id.tvContent);
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code
//                v.getContext().startActivity(new Intent(getContext(), AddItemCategoryActivity.class));
            }
        });

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        tvUserName = view.findViewById(R.id.tvUserNameHome);
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
                startActivity(new Intent(getContext(), AddItemProductActivity.class));
            }
        });
        String userName = usersDAO.getNameUser(email);
        if (userName.isEmpty() || userName.equals("null")) {
            tvUserName.setText("Username");

        } else {
            tvUserName.setText(userName);
        }


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
        listRecommendMain();
        tvSeeMore = view.findViewById(R.id.tvSeeMore);
        tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FilterActivity.class));
            }
        });
        int begin_index = email.indexOf("@");
        int end_index = email.indexOf(".");
        String domain_name = email.substring(begin_index + 1, end_index);
        if (domain_name.equals("merchant")) {

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
        ItemCategory item = new ItemCategory();
        listCate = categoryDAO.getALL();
        if (listCate.size() == 0) {
            item.setImg("content://media/external_primary/images/media/1000002401");
            item.setName("Pizza");
            categoryDAO.insert(item);
            listCate = categoryDAO.getALL();
            //Toast.makeText(getContext(), "Bấm vào avatar góc trái để thêm item category.", Toast.LENGTH_SHORT).show();
        }
        categoryAdapter = new ItemCategoryAdapter(getContext(), listCate);
        recyclerCategory.setAdapter(categoryAdapter);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerCategory.setHasFixedSize(true);
        recyclerCategory.setNestedScrollingEnabled(false);


    }

    public void listRecommend() {
        ItemRecommend item = new ItemRecommend();
        listRecommend = (ArrayList<ItemRecommend>) recommendDAO.getALL(0);
        if (listRecommend.size() == 0) {
            SharedPreferences pref1 = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String email = pref1.getString("EMAIL", "");
            int idUser = usersDAO.getIDUser(email);

            item.idUser = idUser;
            item.img_resource = "content://media/external_primary/images/media/1000002433";
            item.title = "Pizza 1";
            item.price = 15.7;
            item.favourite = 0;
            item.check = 0;
            item.calo = 65.8;
            item.rate = 4.7;
            item.timeDelay = "15";
            item.description = "Pizza abc";
            item.quantity_sold = 0;
            item.location = mLocation;

            recommendDAO.insert(item);
            listRecommend = (ArrayList<ItemRecommend>) recommendDAO.getALL(0);
        }
        recommendAdapterNew = new RecommendAdapterNew(getContext(), listRecommend);
        recyclerViewRecommend.setAdapter(recommendAdapterNew);

        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewRecommend.setHasFixedSize(true);
        recyclerViewRecommend.setNestedScrollingEnabled(false);

    }

    private void listRecommendMain() {
        listRecommendNew = new ArrayList<>();
        listItemStatis = (ArrayList<ItemStatisticalRecommend>) statisticalDAO.getTop();
        if (listItemStatis.size() !=0 && listItemStatis !=null){
            for (int i = 0; i < listItemStatis.size(); i++) {
                listRecommendOld = (ArrayList<ItemRecommend>) recommendDAO.getALLByID(listItemStatis.get(i).idRecommend, 0);
                listRecommendNew.addAll(listRecommendOld);

            }
//        Log.d("size", listRecommendNew.size() + "");
            recommendAdapterNew = new RecommendAdapterNew(getContext(), listRecommendNew);
            recyclerViewRecommendMain.setAdapter(recommendAdapterNew);
            recyclerViewRecommendMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        }



    }

    private void showMenuAddItemCategory() {
        listCate = new ArrayList<>();
        listCate = categoryDAO.getALL();
        categoryAdapter = new ItemCategoryAdapter(getContext(), listCate);
        addCategory.add(new AddCategoryModule(R.drawable.plus_circle));
        addCategoryItemAdapter = new AddCategoryItemAdapter(getContext(), addCategory);

        ConcatAdapter concatAdapter = new ConcatAdapter(categoryAdapter, addCategoryItemAdapter);
        recyclerCategory.setAdapter(concatAdapter);
    }

    private void showMenuAddItemRecommend() {
        listRecommend = (ArrayList<ItemRecommend>) recommendDAO.getALL(0);
        recommendAdapterNew = new RecommendAdapterNew(getContext(), listRecommend);
        addRecommend.add(new AddRecommendModule(R.drawable.plus_circle));
        addRecommendedItemAdapter = new AddRecommendedItemAdapter(getContext(), addRecommend);

        ConcatAdapter concatAdapter = new ConcatAdapter(recommendAdapterNew, addRecommendedItemAdapter);
        recyclerViewRecommend.setAdapter(concatAdapter);
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                                    edLocation.setText("" + addresses.get(0).getAddressLine(0));
                                    mLocation = addresses.get(0).getAddressLine(0);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    });


        } else {

            askPermission();

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
