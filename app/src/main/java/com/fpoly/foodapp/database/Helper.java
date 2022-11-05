package com.fpoly.foodapp.database;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fpoly.foodapp.modules.CategoriesModule;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference =firebaseDatabase.getReference("categories");
    public void insertTableCategories(){
        List<CategoriesModule> list = new ArrayList<>();
        list.add(new CategoriesModule(1,"food"));
        list.add(new CategoriesModule(2,"drink"));

        reference.setValue(list, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }


}
