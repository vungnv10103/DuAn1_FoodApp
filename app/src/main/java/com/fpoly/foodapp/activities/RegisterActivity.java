package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.modules.UsersModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText edEmail, edPass, edConfirmPass;
    Button btnSignUp;
    ImageView imgShowHidePwd, imgShowConfirmPass;
    public FirebaseAuth auth = FirebaseAuth.getInstance();
    public int dem = auth.getCurrentUser().getProviderData().size();
     int count = 24;
    UsersModule item;
    static UsersDAO usersDAO;

    public static final String SHARED_PREFERENCES_NAME = "dem";
    SharedPreferences sharedPreferences;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edEmail = findViewById(R.id.edEmailRegister);
        edPass = findViewById(R.id.edPassWord);
        edConfirmPass = findViewById(R.id.edConfirmPass);
        imgShowHidePwd = findViewById(R.id.img_show_hide_pwd);
        imgShowConfirmPass = findViewById(R.id.img_show_hide_confirm_pwd);
        usersDAO = new UsersDAO(getApplicationContext());


        btnSignUp = findViewById(R.id.register);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSingUp();
            }
        });

        imgShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    edPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgShowHidePwd.setImageResource(R.drawable.ic_baseline_eye_off_24);
                } else {
                    edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgShowHidePwd.setImageResource(R.drawable.ic_baseline_eye_on_24);
                }

            }
        });
        imgShowConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edConfirmPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    edConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgShowConfirmPass.setImageResource(R.drawable.ic_baseline_eye_off_24);
                } else {
                    edConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgShowConfirmPass.setImageResource(R.drawable.ic_baseline_eye_on_24);
                }

            }
        });
    }


    private void onClickSingUp() {
        String email = edEmail.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String confirm = edConfirmPass.getText().toString();



        if (validate() > 0) {
            if (confirm.equals(pass)) {

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            rememberUser(email, pass, true);
                            item = new UsersModule();
                            item.bitmap = "null";
                            item.name = "null";
                            item.email = email;
                            item.pass = pass;
                            item.address = "null";
                            item.phoneNumber = "null";
                            item.feedback = "null";
                            //????ng t???t c??? c??c activity tr?????c main

                            if (usersDAO.insert(item) > 0) {
                                Toast.makeText(getApplicationContext(), "????ng nh???p th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                                Toast.makeText(RegisterActivity.this, "????ng k?? th??nh  c??ng !", Toast.LENGTH_SHORT).show();
                                startActivity(intent1);
                                finishAffinity();
                            } else {
                                Toast.makeText(getApplicationContext(), "????ng nh???p th???t b???i !", Toast.LENGTH_SHORT).show();
                            }
                            count++;
                            remember();
                            Toast.makeText(RegisterActivity.this, "c?? " + count + " t??i kho???n", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "????ng k?? th???t b???i.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            } else {
                Toast.makeText(this, "M???t kh???u kh??ng tr??ng kh???p.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "??i???n ????? th??ng tin.", Toast.LENGTH_SHORT).show();
        }

    }

    public void login(View view) {
//        startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public int validate() {
        int check = -1;
        String email = edEmail.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String confirm = edConfirmPass.getText().toString();
        if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            check = -1;
        } else {
            check = 1;
        }
        return check;
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            editor.clear();
        } else {
            // l??u d??? li???u
            editor.putString("EMAIL", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        // l??u l???i
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("soluongtaikhoan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value = snapshot.getValue(Integer.class);
                count = value;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }

    public void remember() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("soluongtaikhoan");
        reference.setValue(count, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(RegisterActivity.this, "push thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}