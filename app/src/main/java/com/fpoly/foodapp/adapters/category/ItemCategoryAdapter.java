package com.fpoly.foodapp.adapters.category;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.CategoryDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.MainActivity;
import com.fpoly.foodapp.activities.UpdateItemCategoryActivity;
import com.fpoly.foodapp.activities.UpdateItemRecommendActivity;
import com.fpoly.foodapp.adapters.item_product.ItemProduct;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.adapters.recommend.RecommendAdapterNew;
import com.fpoly.foodapp.ui.home.HomeFragmentNew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.viewHolder> {
    Context context;
    List<ItemCategory> list;
    List<ItemRecommend> list1;
    CategoryDAO categoryDAO;
    ProgressDialog progressDialog;


    public ItemCategoryAdapter(Context context, List<ItemCategory> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        progressDialog = new ProgressDialog(context);
        ItemCategory item = list.get(position);
        categoryDAO = new CategoryDAO(context);
        if (item == null) {
            return;
        }


        String nameCategory = item.getName();
        String uri = categoryDAO.getUriImg(nameCategory);
        holder.img.setImageBitmap(convert(uri));

//        String[] listPath = {"content://media/external_primary/images/media/1000002401", "content://media/external_primary/images/media/1000002403", "content://media/external_primary/images/media/1000002405", "content://media/external_primary/images/media/1000002402", "content://media/external_primary/images/media/1000002404", "content://media/external_primary/images/media/1000002414"};

        SharedPreferences pref = context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int begin_index = email.indexOf("@");
        int end_index = email.indexOf(".");
        String domain_name = email.substring(begin_index + 1, end_index);
        if (domain_name.toLowerCase(Locale.ROOT).equals("merchant")) {
            holder.imgDelete.setVisibility(View.VISIBLE);
        }

        holder.tvName.setText(nameCategory);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setTitle("Deleted " + nameCategory);

                android.os.Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        categoryDAO.delete(nameCategory);

                        v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
                        progressDialog.dismiss();
                    }
                }, 500);

//                Toast.makeText(context, "Deleted " + nameCategory, Toast.LENGTH_SHORT).show();


            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + nameCategory, Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, ""+ item.getImg(), Toast.LENGTH_SHORT).show();

                // filter




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
        TextView tvName;
        ImageView img, imgDelete;
        ConstraintLayout cardView;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameItem);
            img = itemView.findViewById(R.id.img_item_cate);
            imgDelete = itemView.findViewById(R.id.imgDeleteItemCategory);
            cardView = itemView.findViewById(R.id.card_view);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SharedPreferences pref = v.getContext().getSharedPreferences("INFO_ITEM", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    // lưu dữ liệu
                    editor.putInt("ID", list.get(getLayoutPosition()).getId());
                    editor.putString("NAME", list.get(getLayoutPosition()).getName());
                    editor.putString("IMG", list.get(getLayoutPosition()).getImg());

                    // lưu lại
                    editor.commit();
                    v.getContext().startActivity(new Intent(context, UpdateItemCategoryActivity.class));
                    return false;
                }
            });

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

