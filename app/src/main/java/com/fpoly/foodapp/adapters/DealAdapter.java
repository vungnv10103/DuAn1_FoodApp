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
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.VoucherModule;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {
    private Context context;
    private List<VoucherModule> voucherList;
    static VoucherDAO voucherDAO;

    public DealAdapter(Context context, List<VoucherModule> voucherList) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        voucherDAO = new VoucherDAO(context.getApplicationContext());
        VoucherModule newOb = voucherList.get(position);

        holder.imgVoucher.setImageResource(newOb.img);
        holder.tvID.setText("ID: " + newOb.id);
        holder.tvDiscount.setText(newOb.voucherTitle);
        holder.tvDeadlineVoucher.setText(newOb.voucherDeadline);


        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rememberVoucher(newOb.id, newOb.voucherTitle, newOb.voucherDeadline);
                if (voucherDAO.delete(newOb.id) > 0) {
                    voucherList.remove(position);
                    notifyDataSetChanged();

                    Toast.makeText(context, "Đã lưu !", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return voucherList.size();
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

                }
            });
        }
    }

    public void rememberVoucher(int id, String discount, String deadline) {
        SharedPreferences pref = context.getSharedPreferences("VOUCHER", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        // lưu dữ liệu
        editor.putInt("ID", id);
        editor.putString("DISCOUNT", discount);
        editor.putString("DEADLINE", deadline);

        // lưu lại
        editor.commit();
    }
}
