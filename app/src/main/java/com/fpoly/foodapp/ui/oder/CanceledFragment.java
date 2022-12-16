package com.fpoly.foodapp.ui.oder;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.foodapp.DAO.OderDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.OderHistoryAdapter;
import com.fpoly.foodapp.modules.OderHistoryModel;
import com.fpoly.foodapp.modules.OderHistoryModelNew;

import java.util.ArrayList;
import java.util.Collections;

public class CanceledFragment extends Fragment {
    private RecyclerView rcvOder;
    static OderDAO oderDAO;
    static UsersDAO usersDAO;
    OderHistoryAdapter oderHistoryAdapter;
    ArrayList<OderHistoryModelNew> listOder;


    public CanceledFragment() {
        // Required empty public constructor
    }

    public static CanceledFragment newInstance() {
        CanceledFragment fragment = new CanceledFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_canceled, container, false);
        rcvOder = view.findViewById(R.id.rcvCancelOder);
        oderDAO = new OderDAO(view.getContext());
        usersDAO = new UsersDAO(view.getContext());
        SharedPreferences pref = view.getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int id = usersDAO.getIDUser(email);

        listOder = (ArrayList<OderHistoryModelNew>) oderDAO.getAllByStatus(id,1);
        oderHistoryAdapter = new OderHistoryAdapter(listOder, getContext());
        rcvOder.setAdapter(oderHistoryAdapter);

        rcvOder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvOder.setHasFixedSize(true);
        rcvOder.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int id = usersDAO.getIDUser(email);
        listOder = (ArrayList<OderHistoryModelNew>) oderDAO.getAllByStatus(id,1);
        Collections.reverse(listOder);
        oderHistoryAdapter = new OderHistoryAdapter(listOder, getContext());
        rcvOder.setAdapter(oderHistoryAdapter);

        rcvOder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvOder.setHasFixedSize(true);
        rcvOder.setNestedScrollingEnabled(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        int id = usersDAO.getIDUser(email);
        listOder = (ArrayList<OderHistoryModelNew>) oderDAO.getAllByStatus(id,1);
        Collections.reverse(listOder);
        oderHistoryAdapter = new OderHistoryAdapter(listOder, getContext());
        rcvOder.setAdapter(oderHistoryAdapter);

        rcvOder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvOder.setHasFixedSize(true);
        rcvOder.setNestedScrollingEnabled(false);
    }
}