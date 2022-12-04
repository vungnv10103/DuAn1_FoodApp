package com.fpoly.foodapp.adapters.item_product;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.ShowDetailActivity;
import com.fpoly.foodapp.adapters.product.ListProduct;
import com.fpoly.foodapp.modules.RecommendedModule;

import java.util.ArrayList;
import java.util.List;

public class ItemProductAdapter extends RecyclerView.Adapter<ItemProductAdapter.viewHolder> implements Filterable {
    Context context;
    ImageView imgZoomIn;
    ArrayList<ItemProduct> list;
    ArrayList<ItemProduct> listOld;

    public ItemProductAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<ItemProduct> list) {
        this.listOld = list;
        this.list = list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemProduct item = list.get(position);
        if (item == null) {
            return;
        }
        holder.img.setImageResource(item.getImg());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation aniSlide = AnimationUtils.loadAnimation(v.getContext(), R.anim.zoom_in);
//                v.startAnimation(aniSlide);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_image_zoom);

                imgZoomIn = dialog.findViewById(R.id.imgZoomIn);
                imgZoomIn.setImageResource(list.get(position).getImg());

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setAttributes(lp);
                dialog.show();
            }
        });
        holder.imgAdd.setImageResource(item.getResource_image());
        holder.tvPrice.setText("" + item.getMoney());
        holder.tvTitle.setText(item.getTitle());
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), ShowDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("image", list.get(position).getImg());
                bundle.putDouble("price", list.get(position).getMoney());
                bundle.putString("title", list.get(position).getTitle());
                intent1.putExtra("data", bundle);
                v.getContext().startActivity(intent1);
            }
        });




    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice;
        ImageView img, imgAdd;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            img = itemView.findViewById(R.id.imgProducts);
            imgAdd = itemView.findViewById(R.id.imgAdd);

        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if (str.isEmpty()) {
                    list = listOld;
                } else {
                    List<ItemProduct> mlist = new ArrayList<>();
                    for (ItemProduct food : listOld) {
                        if (food.getTitle().toLowerCase().contains(str.toLowerCase())) {
                            mlist.add(food);
                        }
                    }
                    list = (ArrayList<ItemProduct>) mlist;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<ItemProduct>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}

