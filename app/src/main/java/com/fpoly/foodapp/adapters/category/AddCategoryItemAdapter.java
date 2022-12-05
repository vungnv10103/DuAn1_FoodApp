package com.fpoly.foodapp.adapters.category;


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
import com.fpoly.foodapp.modules.AddCategoryModule;
import com.fpoly.foodapp.modules.AddRecommendModule;

import java.util.List;

public class AddCategoryItemAdapter extends RecyclerView.Adapter<AddCategoryItemAdapter.viewHolder> {

    private Context context;
    private List<AddCategoryModule> list;




    public AddCategoryItemAdapter(Context context, List<AddCategoryModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_category, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imgAdd.setImageResource(R.drawable.plus_circle);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), AddItemCategoryActivity.class));
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
            imgAdd = itemView.findViewById(R.id.img_add_item_cate);


        }
    }


}

