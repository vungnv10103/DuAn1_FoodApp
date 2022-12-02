package com.fpoly.foodapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.HomeHorModule;
import com.fpoly.foodapp.modules.HomeVerModule;

import java.util.ArrayList;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {
    UpdateVerticalRec updateVerticalRec;
    Context context;
    Activity activity;
    boolean check = true;
    boolean selected = true;
    int row_index = -1;
    ArrayList<HomeHorModule> list;

    public HomeHorAdapter(Context context, ArrayList<HomeHorModule> list) {
        this.context = context;
        this.list = list;
    }

    public HomeHorAdapter(UpdateVerticalRec updateVerticalRec, Activity activity, ArrayList<HomeHorModule> list) {
        this.updateVerticalRec = updateVerticalRec;
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imgView.setImageResource(list.get(position).getImage());
        holder.tvName.setText(list.get(position).getName());
        if (check) {
            ArrayList<HomeVerModule> homeVerModules = new ArrayList<>();
            homeVerModules.add(new HomeVerModule(R.drawable.pizza1_new, "Pizza 1", "10:00 - 23:00", "4.9", "Min - $40"));
            homeVerModules.add(new HomeVerModule(R.drawable.pizza2, "Pizza 2", "10:00 - 23:00", "4.9", "Min - $40"));
            homeVerModules.add(new HomeVerModule(R.drawable.pizza3_new, "Pizza 3", "10:00 - 23:00", "4.9", "Min - $40"));
            homeVerModules.add(new HomeVerModule(R.drawable.pizza4, "Pizza 4", "10:00 - 23:00", "4.9", "Min - $40"));
            homeVerModules.add(new HomeVerModule(R.drawable.pizza5, "Pizza 5", "10:00 - 23:00", "4.9", "Min - $40"));
            updateVerticalRec.callBack(position, homeVerModules);
            check = false;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

                if (position == 0) {

                    ArrayList<HomeVerModule> homeVerModules = new ArrayList<>();
                    homeVerModules.add(new HomeVerModule(R.drawable.pizza1_new, "Pizza 1", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.pizza2, "Pizza 2", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.pizza3_new, "Pizza 3", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.pizza4, "Pizza 4", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.pizza5, "Pizza 5", "10:00 - 23:00", "4.9", "Min - $40"));

                    updateVerticalRec.callBack(position, homeVerModules);
                } else if (position == 1) {
                    ArrayList<HomeVerModule> homeVerModules = new ArrayList<>();
                    homeVerModules.add(new HomeVerModule(R.drawable.burger1, "Burger 1", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.burger2, "Burger 2", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.burger4, "Burger 3", "10:00 - 23:00", "4.9", "Min - $40"));

                    updateVerticalRec.callBack(position, homeVerModules);
                } else if (position == 2) {
                    ArrayList<HomeVerModule> homeVerModules = new ArrayList<>();
                    homeVerModules.add(new HomeVerModule(R.drawable.fries1, "Fries 1", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.fries2, "Fries 2", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.fries3, "Fries 3", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.fries4, "Fries 4", "10:00 - 23:00", "4.9", "Min - $40"));

                    updateVerticalRec.callBack(position, homeVerModules);
                } else if (position == 3) {
                    ArrayList<HomeVerModule> homeVerModules = new ArrayList<>();
                    homeVerModules.add(new HomeVerModule(R.drawable.icecream1, "Ice-cream 1", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.icecream2, "Ice-cream 2", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.icecream3, "Ice-cream 3", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.icecream4, "Ice-cream 4", "10:00 - 23:00", "4.9", "Min - $40"));

                    updateVerticalRec.callBack(position, homeVerModules);
                } else if (position == 4) {
                    ArrayList<HomeVerModule> homeVerModules = new ArrayList<>();
                    homeVerModules.add(new HomeVerModule(R.drawable.sandwich1, "Sandwich 1", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.sandwich2, "Sandwich 2", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.sandwich3, "Sandwich 3", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.sandwich4, "Sandwich 4", "10:00 - 23:00", "4.9", "Min - $40"));

                    updateVerticalRec.callBack(position, homeVerModules);
                }
                else if (position == 5) {
                    ArrayList<HomeVerModule> homeVerModules = new ArrayList<>();
                    homeVerModules.add(new HomeVerModule(R.drawable.strawberry, "Strawberry", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.mango, "Mango", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.guava, "Guava", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.grape, "grape", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.pineapple, "Pineapple", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.grapefruit, "Grapefruit", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.tomato, "Tomato", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.carrot, "Carrot", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.watermelon, "Watermelon", "10:00 - 23:00", "4.9", "Min - $40"));
                    homeVerModules.add(new HomeVerModule(R.drawable.lemon, "Lemon", "10:00 - 23:00", "4.9", "Min - $40"));

                    updateVerticalRec.callBack(position, homeVerModules);
                }
            }
        });
        if (selected) {
            if (position == 0) {
                holder.cardView.setBackgroundResource(R.drawable.change_bg);
                selected = false;
            }
        } else {
            if (row_index == position) {
                holder.cardView.setBackgroundResource(R.drawable.change_bg);
            }else {
                holder.cardView.setBackgroundResource(R.drawable.default_bg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView tvName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_hor);
            tvName = itemView.findViewById(R.id.tvName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
