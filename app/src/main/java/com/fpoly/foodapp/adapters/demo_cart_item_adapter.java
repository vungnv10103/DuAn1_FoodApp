package com.fpoly.foodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.demo_cart_item;

import java.util.ArrayList;
import java.util.List;

public class demo_cart_item_adapter extends RecyclerView.Adapter<demo_cart_item_adapter.ViewHolder> {
    private ArrayList<demo_cart_item> list;
    private Context context;

    public demo_cart_item_adapter(ArrayList<demo_cart_item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public demo_cart_item_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_cart_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull demo_cart_item_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(list.get(position).getName());
        holder.tvCost.setText("" + list.get(position).getCost());
        holder.tvQuantity.setText("" + list.get(position).getQuantities());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCost;
        TextView tvQuantity;
        CheckBox cbCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvName = itemView.findViewById(R.id.tvName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            cbCheck = itemView.findViewById(R.id.rdCheck);
        }
    }

}
