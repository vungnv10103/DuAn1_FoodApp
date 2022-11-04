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
import com.fpoly.foodapp.modules.HomeVerModule;

import java.util.ArrayList;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {
    Context context;
    ArrayList<HomeVerModule> list;

    public HomeVerAdapter(Context context, ArrayList<HomeVerModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeVerAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.tvName.setText(list.get(position).getName());
        holder.tvTime.setText(list.get(position).getTiming());
        holder.tvRate.setText(list.get(position).getRating());
        holder.tvPrice.setText(list.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName , tvTime, tvRate, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ver_img);
            tvName = itemView.findViewById(R.id.name);
            tvTime = itemView.findViewById(R.id.timing);
            tvRate = itemView.findViewById(R.id.rating);
            tvPrice = itemView.findViewById(R.id.price);
        }
    }
}
