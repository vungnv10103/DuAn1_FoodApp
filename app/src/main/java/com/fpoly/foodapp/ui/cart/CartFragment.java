package com.fpoly.foodapp.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.OderDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.DAO.CartItemDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.DealsActivity;
import com.fpoly.foodapp.adapters.Billdetails_adapter;
import com.fpoly.foodapp.adapters.CartItemAdapter;
import com.fpoly.foodapp.modules.CartItemModule;
import com.fpoly.foodapp.modules.OderHistoryModel;
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

public class CartFragment extends Fragment {

    private ConstraintLayout checkOut, titleCart;
    RecyclerView recyclerView, RCL;
    private ArrayList<CartItemModule> list;
    CartItemAdapter CartItemAdapter;
    static CartItemDAO CartItemDAO;
    static UsersDAO usersDAO;
    int temp = 0;
    int temp2 = 0;
    public String date;
    private String chuoi = "";
    Billdetails_adapter adapter;
    public ArrayList<billdetailmodel> moduleArrayList;
    public static int check = 0;

    static OderDAO oderDAO;
    ArrayList<OderHistoryModel> listOder;
    OderHistoryModel itemOder;


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


        oderDAO = new OderDAO(getContext());
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss ");
        date = format.format(calendar.getTime());

        moduleArrayList = new ArrayList<>();
        SharedPreferences pref1 = getContext().getSharedPreferences("VOUCHER", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref1.edit();
        editor.clear();

        tvCouponCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp2++;
                startActivity(new Intent(getContext(), DealsActivity.class));
                getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();


            }
        });
        listData();
        int discount = pref1.getInt("DISCOUNT", 0);
        checkItemSelected(check, discount);


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
        } else {

            CartItemAdapter = new CartItemAdapter(list, getContext());
            recyclerView.setAdapter(CartItemAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            swipeToDelete();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("object_cart");
            reference.setValue(list, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                    Toast.makeText(getContext(), "Thàng công", Toast.LENGTH_SHORT).show();
                }
            });
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
=======
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");



        if (mCheck != 0) {
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
                    listOder = new ArrayList<>();
                    itemOder = new OderHistoryModel();
                    Random random = new Random();
                    int id_randum =random.nextInt();
                    if(id_randum<0){
                        id_randum*=-1;
                    }
                    double tongtiensanpham = total();
                    double tax = tongtiensanpham * 0.1;
                    double delivery = tongtiensanpham * 0.05;
                    double total = tongtiensanpham + tongtiensanpham * 0.1 + tongtiensanpham * 0.05 - coupon;

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("objec_bill");
                    moduleArrayList.add(new billdetailmodel(id_randum, chuoi, "Chưa thanh toán", date, tongtiensanpham, taxi, delivery, total));
                    reference.setValue(moduleArrayList, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(getContext(), "push thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    });
=======

                    itemOder.setMadonhang(id_randum);
                    itemOder.setSoluongsanphan(chuoi);
                    itemOder.setTrangthai("Chưa thanh toán");
                    itemOder.setIdUser(usersDAO.getIDUser(email));
                    itemOder.setNgaymua(date);
                    itemOder.setTongtien(total);
                    itemOder.setTongtiensanpham(tongtiensanpham);
                    itemOder.setTax(tax);
                    itemOder.setDalivery(delivery);
                    if (oderDAO.insert(itemOder) > 0) {
                        Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                    if (oderDAO.getALL().size() > 0) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("objec_bill");
                        listOder = (ArrayList<OderHistoryModel>) oderDAO.getALL();
//                    moduleArrayList.add(new billdetailmodel(id_randum, chuoi, "Chưa thanh toán", date, tongtiensanpham, tax, delivery, total));
                        reference.setValue(listOder, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getContext(), "Đặt hàng thành công !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            });
        }


    }

    public void getdata() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("object_cart");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CartItemModule item = snapshot1.getValue(CartItemModule.class);
                    list.add(item);
                }
                CartItemAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        getdata();
        for (int i = 0; i < list.size(); i++) {
            chuoi += "● " + list.get(i).name + "   --   SL: " + list.get(i).quantities + "\n";
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("objec_bill");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    billdetailmodel billdetailmodel1 = snapshot1.getValue(billdetailmodel.class);
                    moduleArrayList.add(billdetailmodel1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
