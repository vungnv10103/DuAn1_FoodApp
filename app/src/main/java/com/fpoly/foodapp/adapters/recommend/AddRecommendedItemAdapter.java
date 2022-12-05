package com.fpoly.foodapp.adapters.recommend;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.AddItemCategoryActivity;
import com.fpoly.foodapp.activities.AddItemRecommendActivity;
import com.fpoly.foodapp.modules.AddRecommendModule;
import com.fpoly.foodapp.ui.account.AccountManagerFragment;

import java.util.List;

public class AddRecommendedItemAdapter extends RecyclerView.Adapter<AddRecommendedItemAdapter.viewHolder> {

    private Context context;
    private List<AddRecommendModule> list;


    public AddRecommendedItemAdapter(Context context, List<AddRecommendModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_recommended, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imgAdd.setImageResource(R.drawable.plus_circle);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), AddItemRecommendActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgAdd;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgAdd = itemView.findViewById(R.id.imgAddItemRecommend);


        }
    }


}

