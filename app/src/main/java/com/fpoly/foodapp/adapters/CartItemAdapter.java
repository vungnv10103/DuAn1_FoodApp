package com.fpoly.foodapp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.CartItemTempDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.CartTempModule;
import com.fpoly.foodapp.ui.cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<CartItemModule> list;
    private Context context;
    static CartItemTempDAO cartItemTempDAO;
    CartTempModule item;

    static UsersDAO usersDAO;
    private static final String TAG = "test";

    public CartItemAdapter(List<CartItemModule> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_current, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        cartItemTempDAO = new CartItemTempDAO(context);
        usersDAO = new UsersDAO(context);
        item = new CartTempModule();
        holder.tvName.setText(list.get(position).name);
        holder.tvCost.setText("" + list.get(position).cost);
        holder.tvQuantity.setText("" + list.get(position).quantities);
        SharedPreferences pref = context.getSharedPreferences("cache", MODE_PRIVATE);
        holder.cbCheck.setChecked(pref.getBoolean("check", false));
        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = holder.cbCheck.isChecked();
                SharedPreferences settings = context.getSharedPreferences("cache", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("check", check);
                editor.commit();;

                if (check) {     // true
                    SharedPreferences pref = context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
                    String email = pref.getString("EMAIL", "");
                    item.idUser = usersDAO.getIDUser(email);
                    item.idRecommend = list.get(position).idRecommend;
                    item.img = list.get(position).img;
                    item.check = 0;
                    item.name = list.get(position).name;
                    item.cost = list.get(position).cost;
                    item.quantities = list.get(position).quantities;

                    if (cartItemTempDAO.insert(item) > 0){
                        Log.d(TAG, "onClick: " + "save cart temporary");
                    }

                } else {
                    if (cartItemTempDAO.delete(list.get(position).name) > 0){
                        Log.d(TAG, "onClick: " + "removed cart temporary");
                    }
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
