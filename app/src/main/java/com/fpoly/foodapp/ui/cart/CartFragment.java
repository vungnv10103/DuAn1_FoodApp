package com.fpoly.foodapp.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.DAO.CartItemDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.DealsActivity;
import com.fpoly.foodapp.adapters.CartItemAdapter;
import com.fpoly.foodapp.modules.CartItemModule;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private ConstraintLayout checkOut, titleCart;
    RecyclerView recyclerView;
    private ArrayList<CartItemModule> list;
    CartItemAdapter CartItemAdapter;
    static CartItemDAO CartItemDAO;
    static UsersDAO usersDAO;
    int temp = 0;
    int temp2 = 0;


    TextView tvToTal, tvDelivery, tvTax, tvCouponCost, tvCoupon;
    TextView tvToTalPriceFinal;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String TOTAL_KEY = "doanhthu";
    public static final String TOTAL_FINAL_KEY = "tongdoanhthu";


    public CartFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        CartItemDAO = new CartItemDAO(getContext());
        usersDAO = new UsersDAO(getContext());
        titleCart = view.findViewById(R.id.title_cart);
        checkOut = view.findViewById(R.id.checkOut);
        tvToTal = view.findViewById(R.id.total_price_item);
        tvDelivery = view.findViewById(R.id.delivery_price);
        tvTax = view.findViewById(R.id.tax_price);
        tvToTalPriceFinal = view.findViewById(R.id.total_price_cart);
        recyclerView = view.findViewById(R.id.rec_cart);
        tvCouponCost = view.findViewById(R.id.tvCouponCost);
        tvCouponCost.setText("Chọn voucher");
        tvCoupon = view.findViewById(R.id.tvCoupon);
        tvCoupon.setText("Voucher: ");
        SharedPreferences pref = getContext().getSharedPreferences("VOUCHER", MODE_PRIVATE);
        int discount = pref.getInt("DISCOUNT", 0);
        tvCouponCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp2++;
                startActivity(new Intent(getContext(), DealsActivity.class));


            }
        });


        checkItemSelected(1, discount);


        listData();


        return view;
    }

    private void listData() {
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int idUser = usersDAO.getIDUser(email);
        list = (ArrayList<CartItemModule>) CartItemDAO.getALL(idUser);
        if (list.size() == 0) {
            titleCart.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Giỏ hàng trống. Quay lại mua hàng.", Toast.LENGTH_SHORT).show();
        }
        CartItemAdapter = new CartItemAdapter(list, getContext());
        recyclerView.setAdapter(CartItemAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        swipeToDelete();


    }


    public Double total() {
        double price = 0;
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int idUser = usersDAO.getIDUser(email);
        list = (ArrayList<CartItemModule>) CartItemDAO.getALL(idUser);
        for (int i = 0; i < list.size(); i++) {
            price += list.get(i).cost;
        }
        return price;
    }

    public void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                CartItemModule delete = list.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                int id = delete.id;
//                Toast.makeText(getContext(), "before "+ id, Toast.LENGTH_SHORT).show();
                list.remove(viewHolder.getAdapterPosition());
                CartItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                CartItemDAO.delete(delete.id);

                Snackbar snackbar = Snackbar.make(recyclerView, "Deleted " + delete.name, Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getContext(), "after " + delete.id, Toast.LENGTH_SHORT).show();
                        list.add(position, delete);
                        CartItemAdapter.notifyItemInserted(position);
                        if (id == delete.id && temp == 0) {
                            CartItemDAO.insert(delete);
                            CartItemAdapter.notifyDataSetChanged();

                            temp++;
                        }
                    }
                });
                Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                snackBarLayout.setPadding(0, 0, 0, 110);
                snackbar.show();

            }
        }).attachToRecyclerView(recyclerView);

    }

    public void checkItemSelected(int mCheck, int discount) {
        SharedPreferences pref = getContext().getSharedPreferences("TOTAL_PRICE", MODE_PRIVATE);


        if (mCheck != 0 ) {
//            Double total = Double.valueOf(pref.getString("COST", ""));
            CartItemDAO = new CartItemDAO(getContext());
            double totalPriceItem = total();
            tvToTal.setText(String.format("%.2f", totalPriceItem) + " $");
            tvTax.setText(String.format("%.2f", totalPriceItem * 0.1) + " $");
            Double coupon = discount * totalPriceItem / 100;
            tvCouponCost.setText(coupon + " $");
            tvCoupon.setText("Voucher: " + discount + "%");
            tvDelivery.setText(String.format("%.2f", totalPriceItem * 0.05) + " $");
            double totalFinal = totalPriceItem + totalPriceItem * 0.1 + totalPriceItem * 0.05 - coupon;
            tvToTalPriceFinal.setText(String.format("%.2f", totalFinal) + " $");
            checkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences = getContext().getSharedPreferences(TOTAL_KEY, MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putFloat(TOTAL_FINAL_KEY, (float) totalFinal);
                    editor.commit();


                }
            });
        }


        }



}
