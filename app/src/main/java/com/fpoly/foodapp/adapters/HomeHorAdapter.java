package com.fpoly.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.HomeHorModule;

import java.util.ArrayList;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {
    Context context;
    ArrayList<HomeHorModule> list;

    public HomeHorAdapter(Context context, ArrayList<HomeHorModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHorAdapter.ViewHolder holder, int position) {
        holder.imgView.setImageResource(list.get(position).getImage());
        holder.tvName.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_hor);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
