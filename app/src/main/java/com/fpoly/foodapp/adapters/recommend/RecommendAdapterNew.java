package com.fpoly.foodapp.adapters.recommend;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.AddItemRecommendActivity;
import com.fpoly.foodapp.activities.MainActivity;
import com.fpoly.foodapp.activities.ShowDetailActivity;
import com.fpoly.foodapp.activities.UpdateItemRecommendActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecommendAdapterNew extends RecyclerView.Adapter<RecommendAdapterNew.viewHolder> implements Filterable {
    ImageView imgZoomIn;
    private Context context;
    static RecommendDAO recommendDAO;
    private List<ItemRecommend> list;
    private List<ItemRecommend> listOld;
    ItemRecommend item;

    String mLocation = "";

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;


    public RecommendAdapterNew(Context context, List<ItemRecommend> list) {
        this.context = context;
        this.list = list;
        this.listOld = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        recommendDAO = new RecommendDAO(context);
        item = new ItemRecommend();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        holder.imgAdd.setImageResource(R.drawable.plus_circle);

        int favourite = recommendDAO.getFavourite(list.get(position).title);
        if (favourite == 1) {
            holder.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            holder.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        String path = recommendDAO.getUriImg(list.get(position).title);
        holder.img.setImageBitmap(convert(path));

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation aniSlide = AnimationUtils.loadAnimation(v.getContext(), R.anim.zoom_in);
//                v.startAnimation(aniSlide);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_image_zoom);

                imgZoomIn = dialog.findViewById(R.id.imgZoomIn);
                imgZoomIn.setImageBitmap(convert(path));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setAttributes(lp);
                dialog.show();
            }
        });
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(path));
            holder.img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.tvTitle.setText(list.get(position).title);
        holder.tvPrice.setText(String.valueOf(list.get(position).price));
        SharedPreferences pref = context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int begin_index = email.indexOf("@");
        int end_index = email.indexOf(".");
        String domain_name = email.substring(begin_index + 1, end_index);
        if (domain_name.toLowerCase(Locale.ROOT).equals("merchant")) {
            holder.imgDelete.setVisibility(View.VISIBLE);
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Deleted: " + list.get(position).title, Toast.LENGTH_SHORT).show();
                recommendDAO.delete(list.get(position).title);
                v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });
        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.imgFavourite.getTag().equals("default")) {
                    holder.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                    holder.imgFavourite.setTag("oke");
                    item.favourite = 1;
                    item.title = list.get(position).title;
                    if (recommendDAO.updateFavourite(item) > 0) {
                        Toast.makeText(context, "Đã thêm vào yêu thích !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    holder.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    holder.imgFavourite.setTag("default");
                    item.favourite = 0;
                    item.title = list.get(position).title;
                    if (recommendDAO.updateFavourite(item) > 0) {
                        Toast.makeText(context, "Đã bỏ yêu thích !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDetailActivity.class);
                context.startActivity(intent);
                Intent intent1 = new Intent(context, ShowDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("image_resource", list.get(position).img_resource);
                bundle.putDouble("price", list.get(position).price);
                bundle.putString("title", list.get(position).title);
                intent1.putExtra("data", bundle);
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvPrice;
        ImageView img, imgAdd, imgFavourite, imgDelete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleRecommended);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            img = itemView.findViewById(R.id.imgRecommended);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            imgDelete = itemView.findViewById(R.id.imgDeleteItemRecommend);
            imgFavourite = itemView.findViewById(R.id.imgFavourite);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SharedPreferences pref = v.getContext().getSharedPreferences("INFO_ITEM", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    // lưu dữ liệu
                    editor.putInt("ID", list.get(getLayoutPosition()).id);
                    editor.putString("NAME", list.get(getLayoutPosition()).title);
                    editor.putString("PRICE", list.get(getLayoutPosition()).price + "");
                    editor.putString("IMG", list.get(getLayoutPosition()).img_resource);

                    // lưu lại
                    editor.commit();
                    v.getContext().startActivity(new Intent(context, UpdateItemRecommendActivity.class));
                    return false;

                }
            });


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
                    List<ItemRecommend> mlist = new ArrayList<>();
                    for (ItemRecommend food : listOld) {
                        if (food.title.toLowerCase().contains(str.toLowerCase())) {
                            mlist.add(food);
                        }
                    }
                    list = mlist;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<ItemRecommend>) results.values;
                notifyDataSetChanged();
            }
        };
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

