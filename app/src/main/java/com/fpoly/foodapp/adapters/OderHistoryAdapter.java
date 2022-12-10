package com.fpoly.foodapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.fpoly.foodapp.modules.billdetailmodel;
import com.fpoly.foodapp.ui.cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class OderHistoryAdapter extends RecyclerView.Adapter<OderHistoryAdapter.ViewHolder> {
    List<OderHistoryModel> list;
    Context context;


    CartFragment cartFragment = new CartFragment();


    public OderHistoryAdapter(List<OderHistoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oder_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvCodeOder.setText("" + list.get(position).getMadonhang());
        holder.tvStatus.setText(list.get(position).getTrangthai());
        holder.tvDateTime.setText(list.get(position).getNgaymua());

        holder.tvTotalOder.setText(String.format("%.2f", list.get(position).getTongtien()) + " $");
        holder.tvTotalItem.setText(String.format("%.2f", list.get(position).getTongtiensanpham()) + " $");
        holder.tvDeliver.setText(String.format("%.2f", list.get(position).getDalivery()) + " $");
        holder.tvTax.setText(String.format("%.2f", list.get(position).getTax()) + " $");
        holder.tvListItemProduct.setText(list.get(position).getSoluongsanphan());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodeOder, tvDateTime, tvTotalOder, tvTotalItem, tvDeliver, tvTax, tvListItemProduct, tvStatus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodeOder = itemView.findViewById(R.id.tvCodeOder);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTotalOder = itemView.findViewById(R.id.total_price_bill);
            tvTotalItem = itemView.findViewById(R.id.price_bill);
            tvDeliver = itemView.findViewById(R.id.delivery_bill);
            tvTax = itemView.findViewById(R.id.tax_bill);
            tvListItemProduct = itemView.findViewById(R.id.tvListItemProduct);
            tvStatus = itemView.findViewById(R.id.tvStatus);


        }
    }


}