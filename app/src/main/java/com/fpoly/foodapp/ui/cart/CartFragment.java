package com.fpoly.foodapp.ui.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.demo_item_cart_dao;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.demo_cart_item_adapter;
import com.fpoly.foodapp.modules.demo_cart_item;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private ConstraintLayout checkOut;
    RecyclerView recyclerView;
    private ArrayList<demo_cart_item> list;
    demo_cart_item_adapter demo_cart_item_adapter;
    static com.fpoly.foodapp.DAO.demo_item_cart_dao demo_item_cart_dao;

    TextView tvToTal, tvDelivery, tvTax;
    TextView tvToTalPriceFinal;



    public CartFragment() {
        // Required empty public constructor
    }
    @NonNull
    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        checkOut = view.findViewById(R.id.checkOut);
        tvToTal = view.findViewById(R.id.total_price_item);
        tvDelivery = view.findViewById(R.id.delivery_price);
        tvTax = view.findViewById(R.id.tax_price);
        tvToTalPriceFinal = view.findViewById(R.id.total_price_cart);

        double totalPriceItem = total();
        tvToTal.setText(totalPriceItem + " $");
        tvTax.setText(totalPriceItem * 0.1 + " $");
        tvDelivery.setText(totalPriceItem * 0.05 + " $");
        double totalFinal = totalPriceItem + totalPriceItem * 0.1 + totalPriceItem * 0.05;

        tvToTalPriceFinal.setText( String.format("%.2f", totalFinal) + " $");
        recyclerView = view.findViewById(R.id.rec_cart);
        listData();
        tvToTal.setText(total() + " $");
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Updating...", Toast.LENGTH_SHORT).show();



            }
        });
        return view;
    }
    private void listData() {
        demo_item_cart_dao = new demo_item_cart_dao(getContext());
        list = (ArrayList<demo_cart_item>) demo_item_cart_dao.getALL();
        demo_cart_item_adapter = new demo_cart_item_adapter(list, getContext());
        recyclerView.setAdapter(demo_cart_item_adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

    }
    public Double total(){
        double price = 0;
        demo_item_cart_dao = new demo_item_cart_dao(getContext());
        list = (ArrayList<demo_cart_item>) demo_item_cart_dao.getALL();
        for (int i = 0; i < list.size(); i++){
            price += list.get(i).cost;
        }
        return price;
    }


}