package com.fpoly.foodapp.ui.account.Sub_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.DealAdapter;
import com.fpoly.foodapp.modules.Voucher;

import java.util.ArrayList;
import java.util.List;

public class Fragment_NewDeals extends Fragment {
    RecyclerView recyclerView;
    DealAdapter dealAdapter;
    List<Voucher> voucherList = new ArrayList<>();

    public Fragment_NewDeals() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__new_deals, container, false);

        recyclerView = view.findViewById(R.id.deals_List_NewDeals);
        dealAdapter = new DealAdapter(getContext(), voucherList);

        for (int i = 0; i<=50; i++){
            voucherList.add(new Voucher(R.drawable.apple, "Giảm Giá 50%", "01/02/2022"));
        }

        dealAdapter.setData(voucherList);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(dealAdapter);

        return view;
    }
}