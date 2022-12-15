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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.CartItemDAO;
import com.fpoly.foodapp.DAO.CartItemTempDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.CartTempModule;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<CartItemModule> list;
    private Context context;
    static CartItemTempDAO cartItemTempDAO;
    static CartItemDAO cartItemDAO;
    CartTempModule itemTemp;
    CartItemModule itemCart;

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
        cartItemDAO = new CartItemDAO(context);
        usersDAO = new UsersDAO(context);
        itemTemp = new CartTempModule();
        itemCart = new CartItemModule();
        holder.tvName.setText(list.get(position).name);
        holder.tvCost.setText("" + list.get(position).cost);
        holder.tvQuantity.setText("" + list.get(position).quantities);
        int check = list.get(position).checkSelected;
        if (check == 1){
            holder.cbCheck.setChecked(true);
        }
        else {
            holder.cbCheck.setChecked(false);
        }

        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = holder.cbCheck.isChecked();

                if (check) {     // true

                    // update cart temp
                    SharedPreferences pref = context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
                    String email = pref.getString("EMAIL", "");
                    itemTemp.idUser = usersDAO.getIDUser(email);
                    itemTemp.idRecommend = list.get(position).idRecommend;
                    itemTemp.img = list.get(position).img;
                    itemTemp.check = 0;
                    itemTemp.checkSelected = 1;
                    itemTemp.name = list.get(position).name;
                    itemTemp.cost = list.get(position).cost;
                    itemTemp.costNew = list.get(position).cost;
                    itemTemp.quantities = list.get(position).quantities;
                    itemTemp.quantitiesNew = 0;

                    // update current cart
                    itemCart.name = list.get(position).name;
                    itemCart.checkSelected = 1;
                    if (cartItemDAO.updateSelected(itemCart) > 0){
                        Log.d(TAG, "onClick: " + "update cart current success with 1");
                    }

                    if (cartItemTempDAO.insert(itemTemp) > 0) {
                        Log.d(TAG, "onClick: " + "save cart temporary");
                    }

                } else {
                    if (cartItemTempDAO.delete(list.get(position).name) > 0) {
                        Log.d(TAG, "onClick: " + "removed cart temporary");
                    }
                    itemCart.name = list.get(position).name;
                    itemCart.checkSelected = 0;
                    if (cartItemDAO.updateSelected(itemCart) > 0){
                        Log.d(TAG, "onClick: " + "update cart current success with 0");
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
