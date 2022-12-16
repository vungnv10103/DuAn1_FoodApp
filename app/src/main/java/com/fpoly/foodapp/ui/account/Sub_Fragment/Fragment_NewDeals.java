package com.fpoly.foodapp.ui.account.Sub_Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.VoucherDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.DealAdapter;
import com.fpoly.foodapp.modules.VoucherModule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Fragment_NewDeals extends Fragment {
    RecyclerView recyclerView;
    DealAdapter dealAdapter;
    List<VoucherModule> voucherList = new ArrayList<>();
    static VoucherDAO voucherDAO;
    VoucherModule item;
    FloatingActionButton btnAddVoucher;
    SwipeRefreshLayout swipeRefreshLayout;

    public Fragment_NewDeals() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__new_deals, container, false);
        recyclerView = view.findViewById(R.id.deals_List_NewDeals);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_voucher);
        swipeRefreshLayout.setEnabled(true);
        swipe();
        listData();

        btnAddVoucher = view.findViewById(R.id.btnAddVoucher);
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int begin_index = email.indexOf("@");
        int end_index = email.indexOf(".");
        String domain_name = email.substring(begin_index + 1, end_index);
//        if (domain_name.toLowerCase(Locale.ROOT).equals("merchant")){
//            btnAddVoucher.setVisibility(View.VISIBLE);
//        }

        btnAddVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_add_coupon);
                dialog.setCancelable(false);

                EditText edDiscount, edDeadline;
                ImageView imgChooseDate;
                DatePicker datePicker;
                Button btnSave, btnCancel;

                edDiscount = dialog.findViewById(R.id.edDiscount);
                edDeadline = dialog.findViewById(R.id.edDeadline);
                imgChooseDate = dialog.findViewById(R.id.imgChooseDate);
                datePicker = dialog.findViewById(R.id.date);
                btnSave = dialog.findViewById(R.id.btnSaveCoupon);
                btnCancel = dialog.findViewById(R.id.btnCancelAddVoucher);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                edDeadline.setEnabled(false);

                imgChooseDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                                @Override
                                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    int currentMonth = monthOfYear + 1;
                                    edDeadline.setText(year + "-" + currentMonth + "-" + dayOfMonth);
                                    datePicker.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }
                });
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edDiscount.getText().toString().trim().isEmpty() || edDeadline.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getContext(), "Nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                        } else {
                            int discount = 0;
                            try {
                                discount = Integer.valueOf(edDiscount.getText().toString().trim());
                                if (discount < 1 || discount > 100) {
                                    Toast.makeText(getContext(), "Voucher từ 1 - 100%", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Nhập số", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String time = edDeadline.getText().toString().trim();
                            addData(R.drawable.coupon, discount, time);
                            listData();
                            dialog.dismiss();
                        }

                    }
                });


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setAttributes(lp);
                dialog.show();


            }
        });


        return view;
    }

    public void swipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        voucherDAO = new VoucherDAO(getContext());
                        voucherList = (ArrayList<VoucherModule>) voucherDAO.getALLValid();
                        if (voucherList.size() == 0) {
                            Toast.makeText(getContext(), "Voucher tạm hết. Vui lòng quay lại sau !.", Toast.LENGTH_SHORT).show();
                        }

                        dealAdapter = new DealAdapter(getContext(), voucherList);
                        recyclerView.setAdapter(dealAdapter);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setNestedScrollingEnabled(false);

                    }
                }, 2000);
            }
        });
    }

    private void listData() {
        voucherDAO = new VoucherDAO(getContext());
        voucherList = (ArrayList<VoucherModule>) voucherDAO.getALL();
        if (voucherList.size() == 0) {
            Toast.makeText(getContext(), "Voucher tạm hết. Vui lòng quay lại sau !.", Toast.LENGTH_SHORT).show();
        }

        dealAdapter = new DealAdapter(getContext(), voucherList);
        recyclerView.setAdapter(dealAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void addData(int img, int discount, String time) {
        voucherDAO = new VoucherDAO(getContext());
        voucherList = (ArrayList<VoucherModule>) voucherDAO.getALL();
        item = new VoucherModule();

        dealAdapter = new DealAdapter(getContext(), voucherList);
        recyclerView.setAdapter(dealAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        item.img = img;
        item.discount = discount;
        item.voucherDeadline = time;
        if (voucherDAO.insert(item) > 0) {
            Toast.makeText(getContext(), "Thêm thành công !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Thêm thất bại !", Toast.LENGTH_SHORT).show();
        }
    }

}