package com.fpoly.foodapp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.VoucherDAO;
import com.fpoly.foodapp.DAO.VoucherSystemDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.VoucherModule;
import com.fpoly.foodapp.modules.VoucherSystemModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DealSystemAdapter extends RecyclerView.Adapter<DealSystemAdapter.ViewHolder> {
    private Context context;
    private static List<VoucherSystemModule> voucherSystemList;
    static VoucherSystemDAO voucherSystemDAO;
    static VoucherDAO voucherDAO;
    VoucherModule item;


    public DealSystemAdapter(Context context, List<VoucherSystemModule> voucherSystemList) {
        this.context = context;
        this.voucherSystemList = voucherSystemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.voucher_item_system, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        voucherSystemDAO = new VoucherSystemDAO(context.getApplicationContext());
        voucherDAO = new VoucherDAO(context.getApplicationContext());
        VoucherSystemModule newOb = voucherSystemList.get(position);

        holder.imgVoucher.setImageResource(newOb.img);
        holder.tvID.setText("ID: " + newOb.id);
        holder.tvDiscount.setText(newOb.discount + "");
        holder.tvDeadlineVoucher.setText(newOb.voucherDeadline);


        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deadLine = voucherSystemDAO.getDeadLineVoucher(newOb.id);
                int discount = voucherSystemDAO.getDiscount(newOb.id);
                item = new VoucherModule();
                item.discount = discount;
                item.voucherDeadline = deadLine;
                item.img = R.drawable.coupon;
                if (voucherDAO.insert(item) > 0){
                    Toast.makeText(context, "Đã Lưu !", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                if (voucherSystemDAO.delete(newOb.id) > 0) {
                    voucherSystemList.remove(position);
                    notifyDataSetChanged();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return voucherSystemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgVoucher;
        TextView tvID, tvDiscount, tvDeadlineVoucher;
        Button btnSave;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.tvID);
            imgVoucher = itemView.findViewById(R.id.imgVoucher);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvDeadlineVoucher = itemView.findViewById(R.id.tvDeadlineVoucher);
            btnSave = itemView.findViewById(R.id.save_voucher);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), voucherSystemList.get(getLayoutPosition()).voucherDeadline, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void rememberVoucher(int id, int discount, String deadline) {
        SharedPreferences pref = context.getSharedPreferences("VOUCHER", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        // lưu dữ liệu
        editor.putInt("ID", id);
        editor.putInt("DISCOUNT", discount);
        editor.putString("DEADLINE", deadline);

        // lưu lại
        editor.commit();
    }


}
