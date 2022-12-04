package com.fpoly.foodapp.adapters.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.item_product.ItemProduct;
import com.fpoly.foodapp.adapters.item_product.ItemProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListProductsAdapter extends RecyclerView.Adapter<ListProductsAdapter.ProductViewHolder>  {

    private Context context;
    private ArrayList<ListProduct> list;


    public ListProductsAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<ListProduct> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent , false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ListProduct item = list.get(position);
        if (item == null){
            return;
        }
        holder.tvTitle.setText(item.getNameProduct());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcvProduct.setLayoutManager(linearLayoutManager);

        ItemProductAdapter itemProductAdapter = new ItemProductAdapter(context);
        itemProductAdapter.setData(item.getList());
        holder.rcvProduct.setAdapter(itemProductAdapter);


    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private RecyclerView rcvProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rcvProduct = itemView.findViewById(R.id.rcvProduct);
        }
    }


}
