package com.fpoly.foodapp.ui.account.Sub_Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.VoucherDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.DealAdapter;
import com.fpoly.foodapp.modules.VoucherModule;

import java.util.ArrayList;
import java.util.List;

public class Fragment_YourDeals extends Fragment {
    RecyclerView recyclerView;
    public Fragment_YourDeals() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__your_deals, container, false);
        recyclerView = view.findViewById(R.id.deals_List_YourDeals);


        return view;
    }

}