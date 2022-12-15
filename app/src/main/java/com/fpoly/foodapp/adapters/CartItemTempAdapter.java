package com.fpoly.foodapp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.CartItemTempDAO;
import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.DAO.StatisticalDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.CartTempModule;

import java.io.IOException;
import java.util.List;

public class CartItemTempAdapter extends RecyclerView.Adapter<CartItemTempAdapter.ViewHolder> {
    private List<CartTempModule> list;
    private Context context;
    static CartItemTempDAO cartItemTempDAO;

    static UsersDAO usersDAO;
    static RecommendDAO recommendDAO;
    private static final String TAG = "test";

    public CartItemTempAdapter(List<CartTempModule> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemTempAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemTempAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        cartItemTempDAO = new CartItemTempDAO(context);
        recommendDAO = new RecommendDAO(context);
        usersDAO = new UsersDAO(context);
        String uri = list.get(position).img;
        if (!(uri.isEmpty() || uri.equals("null"))){
            holder.imgAvatar.setImageBitmap(convert(uri));
        }

        holder.tvName.setText(list.get(position).name);
        holder.tvCost.setText("Giá: " + list.get(position).cost/list.get(position).quantities);
        holder.tvQuantity.setText("x" + list.get(position).quantities);
        holder.tvNumberProduct.setText("" + list.get(position).quantities);

        holder.imgDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantities = Integer.parseInt(holder.tvNumberProduct.getText().toString());
                quantities--;
                if (quantities != 0) {
                    holder.tvNumberProduct.setText("" + quantities);
                    holder.tvQuantity.setText("x" + quantities);
                }
            }
        });
        holder.imgIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantities = Integer.parseInt(holder.tvNumberProduct.getText().toString());
                quantities++;
                holder.tvNumberProduct.setText("" + quantities);
                holder.tvQuantity.setText("x" + quantities);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvCost, tvQuantity;
        TextView tvNumberProduct;
        ImageView imgIncrement, imgDecrement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgDetailCart);
            tvName = itemView.findViewById(R.id.tvNameProductDetail);
            tvCost = itemView.findViewById(R.id.tvPriceProductDetail);
            tvQuantity = itemView.findViewById(R.id.tvQuantityProductDetail);
            tvNumberProduct = itemView.findViewById(R.id.tvNumberProduct);
            imgIncrement = itemView.findViewById(R.id.imgIncrement);
            imgDecrement = itemView.findViewById(R.id.imgDecrement);
        }
    }

    public Bitmap convert(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
