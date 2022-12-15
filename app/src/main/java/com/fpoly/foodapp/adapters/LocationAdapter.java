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
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.DAO.LocationDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.LocationModule;

import java.io.IOException;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.viewHolder> {
    Context context;
    List<LocationModule> list;

    private static final String TAG = "test";


    public LocationAdapter(Context context, List<LocationModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(list.get(position).name + " | ");
        holder.tvPhone.setText(list.get(position).phone);
        holder.tvLocation.setText(list.get(position).location);

        holder.rdoChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (holder.rdoChoose.isChecked()){
//                    holder.rdoChoose.setSelected(false);
//                }else {
//                    holder.rdoChoose.setSelected(true);
//                }
                Log.d(TAG, "onClick: " + holder.rdoChoose.isChecked()); //true
                Log.d(TAG, "onClick: " + holder.rdoChoose.isSelected()); //false
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
        TextView tvName, tvPhone, tvLocation;
        RadioButton rdoChoose;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            SharedPreferences pref = context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String email = pref.getString("EMAIL", "");
            tvName = itemView.findViewById(R.id.tvFullNameLocation);
            tvPhone = itemView.findViewById(R.id.tvPhoneNumberLocation);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            rdoChoose = itemView.findViewById(R.id.rdoChoose);

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

