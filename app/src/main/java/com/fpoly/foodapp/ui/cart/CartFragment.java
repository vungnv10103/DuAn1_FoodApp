package com.fpoly.foodapp.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.CartSystemDAO;
import com.fpoly.foodapp.DAO.OderDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.DAO.CartItemDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.IOnBackPressed;
import com.fpoly.foodapp.activities.DealsActivity;
import com.fpoly.foodapp.activities.MainActivity;
import com.fpoly.foodapp.activities.PaymentActivity;
import com.fpoly.foodapp.activities.UpdateItemRecommendActivity;
import com.fpoly.foodapp.activities.UserUpdateInfoActivity;
import com.fpoly.foodapp.adapters.Billdetails_adapter;
import com.fpoly.foodapp.adapters.CartItemAdapter;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.CartSystemModule;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.fpoly.foodapp.modules.UsersModule;
import com.fpoly.foodapp.modules.billdetailmodel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class CartFragment extends Fragment implements IOnBackPressed {
    private Toolbar toolbar;
    private ConstraintLayout checkOut, titleCart;
    RecyclerView recyclerView, RCL;
    private ArrayList<CartItemModule> listCartItem, listCartItem1;
    CartItemModule itemCart;
    CartItemAdapter CartItemAdapter;
    static CartItemDAO CartItemDAO;
    static CartSystemDAO cartSystemDAO;
    private ArrayList<CartSystemModule> listCartSystem;
    static UsersDAO usersDAO;
    UsersModule itemUser;
    ArrayList<UsersModule> listUser;
    public String date;
    private String chuoi = "";
    public ArrayList<billdetailmodel> moduleArrayList;
    static OderDAO oderDAO;



    private static final String TAG = "test";

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
        toolbar = view.findViewById(R.id.toolBarCart);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Gi??? h??ng c???a t??i");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        oderDAO = new OderDAO(getContext());
        CartItemDAO = new CartItemDAO(getContext());
        cartSystemDAO = new CartSystemDAO(getContext());
        usersDAO = new UsersDAO(getContext());

        titleCart = view.findViewById(R.id.title_cart);
        checkOut = view.findViewById(R.id.checkOut);
        recyclerView = view.findViewById(R.id.rec_cart);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listCartItem.size() == 0) {
                    Toast.makeText(getContext(), "Ch??a c?? s???n ph???m", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkInfo() > 0) {
                        startActivity(new Intent(getContext(), PaymentActivity.class));
                    }
                }
            }
        });

        moduleArrayList = new ArrayList<>();


        listData();
        return view;
    }

    private void listData() {
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int idUser = usersDAO.getIDUser(email);
        listCartItem = (ArrayList<CartItemModule>) CartItemDAO.getALLSelected(0);
        if (listCartItem.size() == 0) {
            titleCart.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Gi??? h??ng tr???ng. Quay l???i mua h??ng.", Toast.LENGTH_SHORT).show();
        } else {
            CartItemAdapter = new CartItemAdapter(listCartItem, getContext());
            recyclerView.setAdapter(CartItemAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            swipeToDelete();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("object_cart");
            reference.setValue(listCartItem, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                    Toast.makeText(getContext(), "Th??ng c??ng", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    // vu???t ????? xo??
    public void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                CartItemModule itemDelete = listCartItem.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                int id = itemDelete.id;
                itemCart = new CartItemModule();
                itemCart.check = 1;
                itemCart.name = itemDelete.name;
                if (CartItemDAO.updateStatus(itemCart) > 0) {
                    Log.d(TAG, "onSwiped: " + "update check success");
                }
                listCartItem1 = (ArrayList<CartItemModule>) CartItemDAO.getALLSelected(0);
                CartItemAdapter = new CartItemAdapter(listCartItem1, getContext());
                recyclerView.setAdapter(CartItemAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

                Snackbar snackbar = Snackbar.make(recyclerView, "Deleted " + itemDelete.name, Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemCart = new CartItemModule();
                        itemCart.check = 0;
                        itemCart.name = itemDelete.name;
                        if (CartItemDAO.updateStatus(itemCart) > 0) {
                            Log.d(TAG, "onSwiped: " + "rollback check success");
                            listCartItem = (ArrayList<CartItemModule>) CartItemDAO.getALLSelected(0);
                            CartItemAdapter = new CartItemAdapter(listCartItem, getContext());
                            recyclerView.setAdapter(CartItemAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        }

                    }
                });
                Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                snackBarLayout.setPadding(0, 0, 0, 110);
                snackbar.show();

            }
        }).attachToRecyclerView(recyclerView);


    }



    private int checkInfo() {
        int check = -1;
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        listUser = (ArrayList<UsersModule>) usersDAO.getALLByEmail(email);
        itemUser = listUser.get(0);
        String name = itemUser.name;
        String phoneNumber = itemUser.phoneNumber;
        String address = itemUser.address;

        if (name.equals("null") || phoneNumber.equals("null") || address.equals("null")) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Warning")
                    .setMessage("????? mua h??ng b???n c???n c???p nh???t ?????y ????? th??ng tin t??i kho???n ?")
                    .setIcon(R.drawable.ic_baseline_warning_24)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            startActivity(new Intent(getContext(), UserUpdateInfoActivity.class));
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setCancelable(false)
                    .show();
        } else {
            check = 1;
        }

        return check;

    }




    @Override
    public void onStart() {
        super.onStart();
        for (int i = 0; i < listCartItem.size(); i++) {
            chuoi += "??? " + listCartItem.get(i).name + "   --   SL: " + listCartItem.get(i).quantities + "\n";
        }

    }


    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getContext(), MainActivity.class));
        return true;
    }
}
