package com.fpoly.foodapp.ui.daily_meal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.DailyMealAdapter;
import com.fpoly.foodapp.databinding.FragmentDailyMealBinding;
import com.fpoly.foodapp.modules.DailyMealModule;

import java.util.ArrayList;
import java.util.List;

public class DailyMealFragment extends Fragment {
    RecyclerView recyclerView;
    List<DailyMealModule> dailyMealModules;
    DailyMealAdapter dailyMealAdapter;
    private FragmentDailyMealBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDailyMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView =root.findViewById(R.id.daily_meal_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyMealModules =new ArrayList<>();
        dailyMealModules.add(new DailyMealModule(R.drawable.breakfast,"Breakfast","30% OFF","Description Description","breakfast"));
        dailyMealModules.add(new DailyMealModule(R.drawable.lunch,"Lunch","20% OFF","Description Description","lunch"));
        dailyMealModules.add(new DailyMealModule(R.drawable.dinner,"Dinner","50% OFF","Description Description","dinner"));
        dailyMealModules.add(new DailyMealModule(R.drawable.sweets,"Sweets","25% OFF","Description Description","sweets"));
        dailyMealModules.add(new DailyMealModule(R.drawable.coffe,"Coffee","10% OFF","Description Description","coffee"));

        dailyMealAdapter =new DailyMealAdapter(getContext(),dailyMealModules);
        recyclerView.setAdapter(dailyMealAdapter);
        dailyMealAdapter.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}