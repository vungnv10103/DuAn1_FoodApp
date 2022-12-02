package com.fpoly.foodapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.AddRecommendModule;
import com.fpoly.foodapp.modules.CategoryModule;
import com.fpoly.foodapp.modules.RecommendedModule;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.viewHolder> {
    UpdateVerticalRec updateVerticalRec;
    ArrayList<CategoryModule> list ;
    ArrayList<CategoryModule> listOld;
    Context context;
    boolean check = true;
    boolean selected = true;
    int row_index = -1;
    Activity activity;

    public CategoriesAdapter(ArrayList<CategoryModule> list, Context context) {
        this.list = list;
        this.context = context;
        this.listOld = list;
    }

    public CategoriesAdapter(UpdateVerticalRec updateVerticalRec, Activity activity, ArrayList<CategoryModule> list) {
        this.updateVerticalRec = updateVerticalRec;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());

        if (check) {
            ArrayList<RecommendedModule> foodList = new ArrayList<>();
            ArrayList<AddRecommendModule> list = new ArrayList<>();
            list.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));
            foodList.add(new RecommendedModule(R.drawable.pizza1 , "Pepperoni Pizza" , 13.0 , R.drawable.plus_circle));
            foodList.add(new RecommendedModule(R.drawable.pizza3 , "Vegetable Pizza" , 11.0 , R.drawable.plus_circle));
            foodList.add(new RecommendedModule(R.drawable.pizza5 , "Tomato Pizza" , 12.6 , R.drawable.plus_circle));
            foodList.add(new RecommendedModule(R.drawable.pizza6 , "Cheese Pizza" , 10.5 , R.drawable.plus_circle));
            foodList.add(new RecommendedModule(R.drawable.pizza7 , "Lamacun Pizza" , 15.2 , R.drawable.plus_circle));
            foodList.add(new RecommendedModule(R.drawable.pizza8 , "Beef Pizza" , 14.7 , R.drawable.plus_circle));

            updateVerticalRec.callBackNew(position, foodList, list);
            check = false;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

                if (position == 0) {
                    ArrayList<RecommendedModule> foodList = new ArrayList<>();
                    ArrayList<AddRecommendModule> list = new ArrayList<>();
                    list.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));
                    foodList.add(new RecommendedModule(R.drawable.pizza1 , "Pepperoni Pizza" , 13.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.pizza3 , "Vegetable Pizza" , 11.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.pizza5 , "Tomato Pizza" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.pizza6 , "Cheese Pizza" , 10.5 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.pizza7 , "Lamacun Pizza" , 15.2 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.pizza8 , "Beef Pizza" , 14.7 , R.drawable.plus_circle));

                    updateVerticalRec.callBackNew(position, foodList, list);
                } else if (position == 1) {
                    ArrayList<RecommendedModule> foodList = new ArrayList<>();
                    ArrayList<AddRecommendModule> list = new ArrayList<>();
                    list.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));
                    foodList.add(new RecommendedModule(R.drawable.burger1 , "Pepperoni Burger" , 13.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.burger2 , "Vegetable Burger" , 11.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.burger4 , "Tomato Burger" , 12.6 , R.drawable.plus_circle));

                    updateVerticalRec.callBackNew(position, foodList, list);
                } else if (position == 2) {
                    ArrayList<RecommendedModule> foodList = new ArrayList<>();
                    ArrayList<AddRecommendModule> list = new ArrayList<>();
                    list.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));
                    foodList.add(new RecommendedModule(R.drawable.fries1 , "Pepperoni Fries" , 13.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.fries2 , "Vegetable Fries" , 11.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.fries3 , "Tomato Fries" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.fries4 , "Cheese Fries" , 10.5 , R.drawable.plus_circle));

                    updateVerticalRec.callBackNew(position, foodList, list);
                } else if (position == 3) {
                    ArrayList<RecommendedModule> foodList = new ArrayList<>();
                    ArrayList<AddRecommendModule> list = new ArrayList<>();
                    list.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));
                    foodList.add(new RecommendedModule(R.drawable.icecream1 , "Pepperoni Ice-cream" , 13.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.icecream2 , "Vegetable Ice-cream" , 11.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.icecream3 , "Tomato Ice-cream" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.icecream4 , "Cheese Ice-cream" , 10.5 , R.drawable.plus_circle));

                    updateVerticalRec.callBackNew(position, foodList, list);
                } else if (position == 4) {
                    ArrayList<RecommendedModule> foodList = new ArrayList<>();
                    ArrayList<AddRecommendModule> list = new ArrayList<>();
                    list.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));
                    foodList.add(new RecommendedModule(R.drawable.sandwich1 , "Pepperoni Sandwich" , 13.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.sandwich2 , "Vegetable Sandwich" , 11.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.sandwich3 , "Tomato Sandwich" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.sandwich4 , "Cheese Sandwich" , 10.5 , R.drawable.plus_circle));

                    updateVerticalRec.callBackNew(position, foodList, list);
                }
                else if (position == 5) {
                    ArrayList<RecommendedModule> foodList = new ArrayList<>();
                    ArrayList<AddRecommendModule> list = new ArrayList<>();
                    list.add(new AddRecommendModule(R.drawable.ic_baseline_add_24));
                    foodList.add(new RecommendedModule(R.drawable.strawberry , "Strawberry" , 13.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.mango , "Mango" , 11.0 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.guava , "Guava" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.grapefruit , "Grapefruit" , 10.5 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.pineapple , "Pineapple" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.tomato , "Tomato" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.carrot , "Carrot" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.watermelon , "Watermelon" , 12.6 , R.drawable.plus_circle));
                    foodList.add(new RecommendedModule(R.drawable.lemon , "Lemon" , 12.6 , R.drawable.plus_circle));

                    updateVerticalRec.callBackNew(position, foodList, list);
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


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView name;
        ConstraintLayout cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_cat1);
            name  =itemView.findViewById(R.id.tvNameItem);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

}


