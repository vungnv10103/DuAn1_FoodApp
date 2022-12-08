package com.fpoly.foodapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.UsersDAO;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.BilldetailModule;
import com.fpoly.foodapp.modules.billdetailmodel;

import com.fpoly.foodapp.ui.cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class Billdetails_adapter extends RecyclerView.Adapter<Billdetails_adapter.ViewHolder> {
    private ArrayList<billdetailmodel> list;
    private Context context;


    CartFragment cartFragment = new CartFragment();


    public Billdetails_adapter(ArrayList<billdetailmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_detail, parent, false);

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
        holder.txttaxbill.setText(String.format("%.2f", list.get(position).getTax()) + " $");
        holder.txttensanphamdamua.setText(list.get(position).getSoluongsanphan());
        holder.btnthnahtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txttrangthai.setText("Đã thanh toán");
                holder.txttrangthai.setTextColor(Color.GREEN);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmadonhang, ngaymua, txttongtien, txttiensanpham, txtdeliverybill, txttaxbill, txttensanphamdamua, txttrangthai;

        ConstraintLayout btnthnahtoan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmadonhang = itemView.findViewById(R.id.txtmadonhang1);
            ngaymua = itemView.findViewById(R.id.txtnaymua);
            txttongtien = itemView.findViewById(R.id.total_price_bill);
            txttiensanpham = itemView.findViewById(R.id.price_bill);
            txtdeliverybill = itemView.findViewById(R.id.delivery_bill);
            txttaxbill = itemView.findViewById(R.id.tax_bill);
            txttensanphamdamua = itemView.findViewById(R.id.txtcacsanphamdadat);
            txttrangthai = itemView.findViewById(R.id.txttrangthai);
            btnthnahtoan = itemView.findViewById(R.id.thanhtoan);


        }
    }


}