package com.fpoly.foodapp.ui.account.Sub_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.foodapp.R;

public class Fragment_NewDeals extends Fragment {

    public Fragment_NewDeals() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__new_deals, container, false);
    }
}