package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.Admin.AdminActivity;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.DAO.demo_item_cart_dao;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.modules.UsersModule;
import com.fpoly.foodapp.modules.demo_cart_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    EditText edEmail, edPass;
    Button btnLogin;
    ImageView imgShowHidePwd;
    private CheckBox chbRemember;
    private ProgressDialog progressDialog;
    static UsersDAO usersDAO;
    UsersModule item;
    ArrayList<UsersModule> list;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        usersDAO = new UsersDAO(getApplicationContext());


        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edEmail.setText(pref.getString("EMAIL", ""));
        edPass.setText(pref.getString("PASSWORD", ""));
        chbRemember.setChecked(pref.getBoolean("REMEMBER", false));
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


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list = (ArrayList<UsersModule>) usersDAO.getALL();
                String email = edEmail.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                int begin_index = email.indexOf("@");
                int end_index = email.indexOf(".");
                String domain_name = email.substring(begin_index + 1, end_index);
                if (email.equals("foodapp@admin.com") && pass.equals("vung123")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    progressDialog.show();
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    int temp = 0;


                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        if (email.equals("admin@gmail.com")) {
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            rememberUser(email, pass, chbRemember.isChecked());
                                            item = new UsersModule();
                                            item.bitmap = "null";
                                            item.name = "null";
                                            item.email = email;
                                            item.pass = pass;
                                            item.address = "null";
                                            item.phoneNumber = "null";
                                            for (UsersModule item : list) {
                                                if (email.toLowerCase(Locale.ROOT).equals(item.email.toLowerCase(Locale.ROOT))) {
                                                    temp++;
                                                }
                                            }
                                            // check duplicate data
                                            if (temp > 0) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finishAffinity();
                                            } else {
                                                if (usersDAO.insert(item) > 0) {
                                                    // thêm lần đầu
//                                                    Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Thêm thất bại.", Toast.LENGTH_SHORT).show();
                                                }
                                            }


                                        }
                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }


            }
        });

    }

    public void init() {
        chbRemember = findViewById(R.id.checkBoxRemember);
        edEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btnSignIn);
        imgShowHidePwd = findViewById(R.id.img_show_hide_pwd);
        progressDialog = new ProgressDialog(this);
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            editor.clear();
        } else {
            // lưu dữ liệu
            editor.putString("EMAIL", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        // lưu lại
        editor.commit();
    }
    // phân quyền
    public boolean phanQuyen() {
        if (Build.VERSION.SDK_INT >= 23) {
            // 1. Nếu các quyền đã đc gán thì return true
            if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            // 2. Nếu các quyền chưa đc gán thì cần xin cấp quyền
            else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                        }, 1);
                return false;
            }
        } else {
            return true;
        }
    }
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }




//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce ) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 3000);
//    }
}