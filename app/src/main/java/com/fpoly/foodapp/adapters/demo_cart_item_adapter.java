package com.fpoly.foodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.demo_item_cart_dao;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.Category;
import com.fpoly.foodapp.modules.demo_cart_item;
import com.fpoly.foodapp.ui.cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class demo_cart_item_adapter extends RecyclerView.Adapter<demo_cart_item_adapter.ViewHolder> {
    private List<demo_cart_item> list;
    private Context context;

    public demo_cart_item_adapter(List<demo_cart_item> list, Context context) {
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

        holder.tvName.setText(list.get(position).name);
        holder.tvCost.setText("" + list.get(position).cost);
        holder.tvQuantity.setText("" + list.get(position).quantities);
        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = holder.cbCheck.isChecked();
                if (check) {     // true

                } else {

                }
            }
        });

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
