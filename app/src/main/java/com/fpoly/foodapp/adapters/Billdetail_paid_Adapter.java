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
import com.fpoly.foodapp.modules.billdetail_paid_model;

import java.util.ArrayList;

public class Billdetail_paid_Adapter extends  RecyclerView.Adapter<Billdetail_paid_Adapter.ViewHolder> {
    ArrayList<billdetail_paid_model> list ;
    Context context ;

    public Billdetail_paid_Adapter(ArrayList<billdetail_paid_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billdetail_paid , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtmadonhang.setText("" + list.get(position).getMadonhang());
        holder.txttrangthai.setText(list.get(position).getTrangthai());
        holder.ngaymua.setText(list.get(position).getNgaymua());
        holder.txttongtien.setText(String.format("%.2f", list.get(position).getTongtien()) + " $");
        holder.txttiensanpham.setText(String.format("%.2f", list.get(position).getTongtiensanpham()) + " $");
        holder.txtdeliverybill.setText(String.format("%.2f", list.get(position).getDalivery()) + " $");
        holder.txttensanphamdamua.setText(list.get(position).getSoluongsanphan());
        holder.txttrangthai.setTextColor(Color.GREEN);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmadonhang, ngaymua, txttongtien, txttiensanpham, txtdeliverybill, txttensanphamdamua, txttrangthai;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmadonhang = itemView.findViewById(R.id.txtmadonhangpaid);
            ngaymua = itemView.findViewById(R.id.txtnaymuapaid);
            txttongtien = itemView.findViewById(R.id.total_price_billpaid);
            txttiensanpham = itemView.findViewById(R.id.price_billpaid);
            txtdeliverybill = itemView.findViewById(R.id.delivery_billpaid);

            txttensanphamdamua = itemView.findViewById(R.id.txtcacsanphamdadatpaid);
            txttrangthai = itemView.findViewById(R.id.txttrangthaipaid);
        }
    }
}
