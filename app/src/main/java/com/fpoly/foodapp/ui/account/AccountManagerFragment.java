package com.fpoly.foodapp.ui.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.database.Helper;
import com.fpoly.foodapp.modules.CategoriesModule;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AccountManagerFragment extends Fragment {
    Button button;
    EditText edID, edName;
    int count = 0;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_acc, container, false);
        button = root.findViewById(R.id.btnPushData);
        edID = root.findViewById(R.id.edID);
        edName = root.findViewById(R.id.edName);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("categories");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<CategoriesModule> list = new ArrayList<>();
                list.add(new CategoriesModule(Integer.parseInt(edID.getText().toString().trim()), edName.getText().toString().trim()));
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("categories");
                databaseReference.child(""+count).setValue(list, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "Push data success", Toast.LENGTH_SHORT).show();
                        count++;
                    }
                });

            }
        });



        return root;
    }

}
