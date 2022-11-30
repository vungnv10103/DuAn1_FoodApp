package com.fpoly.foodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.ShowDetailActivity;
import com.fpoly.foodapp.modules.FeaturedModule;
import com.fpoly.foodapp.modules.FeaturedVerModule;
import com.fpoly.foodapp.modules.Food;

import java.util.ArrayList;
import java.util.List;

public class FeaturedVerAdapter extends RecyclerView.Adapter<FeaturedVerAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Food> list;
    private List<Food> listold;

    public FeaturedVerAdapter(Context context,List<Food> list) {
        this.context = context;
        this.list = list;
        this.listold = list;;
    }

    @NonNull
    @Override
    public FeaturedVerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_ver_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedVerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.img.setImageResource(list.get(position).getImg());
        holder.imgAdd.setImageResource(list.get(position).getResource_image());
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvPrice.setText(String.valueOf(list.get(position).getMoney()));
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDetailActivity.class);
                context.startActivity(intent);
                Intent intent1 = new Intent(context, ShowDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("image", list.get(position).getImg());
                bundle.putDouble("price", list.get(position).getMoney());
                bundle.putString("title", list.get(position).getTitle());
                intent1.putExtra("data", bundle);
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if(str.isEmpty()){
                    list = listold;

                }
                else {
                    List<Food> mlist = new ArrayList<>();
                    for( Food food :listold){
                        if(food.getTitle().toLowerCase().contains(str.toLowerCase())){
                            mlist.add(food);
                        }
                    }
                    list = mlist;
                }

                FilterResults  filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice;
        ImageView img, imgAdd;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleRecommended);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            img = itemView.findViewById(R.id.imgRecommended);
            imgAdd = itemView.findViewById(R.id.imgAdd);

        }
    }

}
