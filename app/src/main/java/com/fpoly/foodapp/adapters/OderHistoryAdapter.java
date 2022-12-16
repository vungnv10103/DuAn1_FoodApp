package com.fpoly.foodapp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.OderDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.UpdateItemRecommendActivity;
import com.fpoly.foodapp.activities.UserUpdateInfoActivity;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.fpoly.foodapp.modules.OderHistoryModelNew;
import com.fpoly.foodapp.modules.billdetailmodel;
import com.fpoly.foodapp.ui.cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class OderHistoryAdapter extends RecyclerView.Adapter<OderHistoryAdapter.ViewHolder> {
    List<OderHistoryModelNew> list;
    Context context;
    static OderDAO oderDAO;
    OderHistoryModelNew item;



    public OderHistoryAdapter(List<OderHistoryModelNew> list, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvCodeOder.setText("" + list.get(position).code);
        holder.tvStatus.setText(list.get(position).status);
        holder.tvDateTime.setText(list.get(position).dateTime);
        double feeTransport = list.get(position).feeTransport;
        double totalItem = list.get(position).totalItem;
        double toTal = list.get(position).totalFinal;
        holder.tvVoucher.setText( "-$ " +String.format("%.2f", (totalItem + feeTransport-toTal)));


        holder.tvTotalOder.setText(String.format("%.2f", toTal) + " $");
        holder.tvTotalItem.setText("  $ " +String.format("%.2f", totalItem));
        holder.tvFeeTransport.setText("  $ " + String.format("%.2f", feeTransport));
//        holder.tvTax.setText(String.format("%.2f", list.get(position).getTax()) + " $");
        holder.tvListItemProduct.setText(list.get(position).listProduct);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodeOder, tvDateTime, tvTotalOder, tvTotalItem, tvFeeTransport , tvListItemProduct, tvStatus ,tvVoucher;
        //TextView , tvTax;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodeOder = itemView.findViewById(R.id.tvCodeOder);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTotalOder = itemView.findViewById(R.id.total_price_bill);
            tvTotalItem = itemView.findViewById(R.id.price_bill);
            tvFeeTransport = itemView.findViewById(R.id.tvFeeTransport);
            tvVoucher = itemView.findViewById(R.id.tvVoucher);
            tvListItemProduct = itemView.findViewById(R.id.tvListItemProduct);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    oderDAO = new OderDAO(v.getContext());

                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Warning")
                            .setMessage("Bạn có chắc chắn muốn huỷ đơn hàng ?")
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Cancel Order
                                    int id = list.get(getLayoutPosition()).id;
                                    item = new OderHistoryModelNew();

                                    item.id = id;
                                    item.checkStatus = 1;
                                    item.status = "Đã huỷ";
                                    if (oderDAO.updateStatus(item)>0){
                                        Toast.makeText(context, "Đã huỷ đơn hàng !" , Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }

                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setCancelable(false)
                            .show();

                    return false;

                }
            });


        }
    }


}