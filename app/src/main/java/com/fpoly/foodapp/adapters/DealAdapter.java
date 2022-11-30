package com.fpoly.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.Voucher;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder>{
    private Context context;
    private List<Voucher> voucherList;

    public DealAdapter(Context context, List<Voucher> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.voucher_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voucher newOb = voucherList.get(position);
        
        holder.outTitle.setText(newOb.getVoucherTitle());
        holder.outDeadline.setText(newOb.getVoucherDeadline());
        holder.outImg.setImageResource(newOb.getImg());
        
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Đã Lưu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setData(List<Voucher> voucherList){
        this.voucherList = voucherList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView outTitle, outDeadline;
        Button btnSave;
        ImageView outImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            outTitle = itemView.findViewById(R.id.dealsItem_Voucher_Title);
            outDeadline = itemView.findViewById(R.id.dealsItem_Voucher_Deadline);
            outImg = itemView.findViewById(R.id.imgVoucher);

            btnSave = itemView.findViewById(R.id.deals_Voucher_Save);
        }
    }
}
