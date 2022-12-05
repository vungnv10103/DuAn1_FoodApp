package com.fpoly.foodapp.adapters;


import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.MainActivity;
import com.fpoly.foodapp.activities.ShowDetailActivity;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.modules.UsersModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.viewHolder> implements Filterable {
    ImageView imgZoomIn;
    private Context context;
    static UsersDAO usersDAO;
    private List<UsersModule> list;
    private List<UsersModule> listOld;
    UsersModule item;


    public ListUserAdapter(Context context, List<UsersModule> list) {
        this.context = context;
        this.list = list;
        this.listOld = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        usersDAO = new UsersDAO(context);
        item = new UsersModule();

        String path = usersDAO.getUriImg(list.get(position).email);
        if (!path.equals("null")) {
            holder.img.setImageBitmap(convert(path));
        }


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

        holder.tvName.setText(list.get(position).name);
        holder.tvEmail.setText(list.get(position).email);
        holder.tvPass.setText(list.get(position).pass);
        holder.tvPhoneNumber.setText(list.get(position).phoneNumber);
        holder.tvAddress.setText(list.get(position).address);
        holder.tvFeedback.setText(list.get(position).feedback);


//        SharedPreferences pref = context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
//        String email = pref.getString("EMAIL", "");
//        int begin_index = email.indexOf("@");
//        int end_index = email.indexOf(".");
//        String domain_name = email.substring(begin_index + 1, end_index);
//        if (domain_name.toLowerCase(Locale.ROOT).equals("merchant")){
//            holder.imgDelete.setVisibility(View.VISIBLE);
//        }
        holder.tvPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvPass.getTag().equals("default")) {
                    holder.tvPass.setInputType(InputType.TYPE_CLASS_TEXT);
                    holder.tvPass.setTag("oke");
                } else {
                    holder.tvPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    holder.tvPass.setTag("default");
                }
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Deleted: " + list.get(position).name, Toast.LENGTH_SHORT).show();
                usersDAO.delete(list.get(position).id);
                list.remove(position);
                notifyItemChanged(position);
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

        TextView tvName, tvEmail, tvPass, tvPhoneNumber, tvAddress, tvFeedback;
        ImageView img, imgDelete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemNameUser);
            tvEmail = itemView.findViewById(R.id.tvEmailUser);
            tvPass = itemView.findViewById(R.id.tvPass);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            img = itemView.findViewById(R.id.imgAvatarUser);
            imgDelete = itemView.findViewById(R.id.imgDeleteUser);


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
                    List<UsersModule> mlist = new ArrayList<>();
                    for (UsersModule item : listOld) {
                        if (item.name.toLowerCase().contains(str.toLowerCase())) {
                            mlist.add(item);
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
                list = (List<UsersModule>) results.values;
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

