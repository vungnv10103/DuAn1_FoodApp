package com.fpoly.foodapp.adapters;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.ShowdetailActivity;
import com.fpoly.foodapp.modules.Food;

import java.util.List;

public class recommended_adapter extends RecyclerView.Adapter<recommended_adapter.viewHolder> {
    private Context context ;
    private List<Food> list ;


    public recommended_adapter(Context context, List<Food> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_recommended , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageanh.setImageResource(list.get(position).getImge());
        holder.imagebutton.setImageResource(list.get(position).getResource_image());
        holder.txttitle.setText(list.get(position).getTitle());
        holder.txtmoney.setText(String.valueOf(list.get(position).getMoney()));
        holder.imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(context , ShowdetailActivity.class);
                        context.startActivity(intent);
                        Intent intent1 = new Intent(context , ShowdetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("anh" ,list.get(position).getImge());
                        bundle.putDouble("money_piza1" , list.get(position).getMoney());
                        bundle.putInt("anh" , list.get(position).getImge());
                        intent1.putExtra("dulieu",bundle);

                        context.startActivity(intent1);
                        Toast.makeText(context, list.get(position).getImge(), Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Intent intent2 = new Intent(context , ShowdetailActivity.class);
                        context.startActivity(intent2);
                        Intent intent3 = new Intent(context , ShowdetailActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("anh" , list.get(position).getImge());
                        bundle1.putDouble("money_piza1" , list.get(position).getMoney());
                        intent3.putExtra("dulieu",bundle1);
                        context.startActivity(intent3);
//                        Toast.makeText(context, list.get(position).getImge(), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Intent intent4 = new Intent(context , ShowdetailActivity.class);
                        context.startActivity(intent4);
                        Intent intent5 = new Intent(context , ShowdetailActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("anh" ,list.get(position).getImge());
                        bundle2.putDouble("money_piza1" , list.get(position).getMoney());
                        bundle2.putInt("anh" , list.get(position).getImge());
                        intent5.putExtra("dulieu",bundle2);

                        context.startActivity(intent5);
                        break;
                    default:
                        Toast.makeText(context, "khog co ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView txttitle  , txtmoney;
        ImageView imageanh , imagebutton;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtmoney = itemView.findViewById(R.id.txtmoney);
            imageanh = itemView.findViewById(R.id.image_recommended);
            imagebutton = itemView.findViewById(R.id.float_add);


        }
    }
}

