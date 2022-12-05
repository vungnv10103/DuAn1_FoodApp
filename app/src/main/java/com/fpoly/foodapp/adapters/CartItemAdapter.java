package com.fpoly.foodapp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.CartItemModule;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<CartItemModule> list;
    private Context context;
    Double totalPrice = 0.0;

    public CartItemAdapter(List<CartItemModule> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_cart_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(list.get(position).name);
        holder.tvCost.setText("" + list.get(position).cost);
        holder.tvQuantity.setText("" + list.get(position).quantities);
        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = holder.cbCheck.isChecked();
                if (check) {     // true

                    totalPrice += list.get(position).cost;

                } else {

                }
//                Toast.makeText(context, "" + totalPrice, Toast.LENGTH_SHORT).show();
                SharedPreferences pref = v.getContext().getSharedPreferences("TOTAL_PRICE", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (!check) {
                    editor.clear();
                } else {
                    // lưu dữ liệu
                    editor.putString("COST", String.format("%.2f", totalPrice));
                }
                // lưu lại
                editor.commit();

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
