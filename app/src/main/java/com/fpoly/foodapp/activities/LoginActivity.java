package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.Admin.AdminActivity;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.modules.UsersModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    EditText edPass;
    TextView tvTitleApp;
    Button btnLogin;
    //    ImageView imgShowHidePwd, imgDropDownAcc;
    private CheckBox chbRemember;
    private ProgressDialog progressDialog;
    static UsersDAO usersDAO;
    UsersModule item;
    ArrayList<UsersModule> list;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    boolean doubleBackToExitPressedOnce = false;

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    String mLocation = "";

    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTextViewEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        usersDAO = new UsersDAO(getApplicationContext());
        list = (ArrayList<UsersModule>) usersDAO.getALL();



        String[] listUser = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            String temp = list.get(i).email;
            listUser[i] = temp;
        }

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        autoCompleteTextViewEmail.setText(pref.getString("EMAIL", ""));
        edPass.setText(pref.getString("PASSWORD", ""));
        chbRemember.setChecked(pref.getBoolean("REMEMBER", false));
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_acc, listUser);
        autoCompleteTextViewEmail.setAdapter(adapterItems);
        autoCompleteTextViewEmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pass = usersDAO.autoFillPassWord(listUser[position]);
                edPass.setText(pass);
            }
        });
//        imgShowHidePwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (edPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
//                    edPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    imgShowHidePwd.setImageResource(R.drawable.ic_baseline_eye_off_24);
//                } else {
//                    edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    imgShowHidePwd.setImageResource(R.drawable.ic_baseline_eye_on_24);
//                }
//
//            }
//        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
                progressDialog.show();

                list = (ArrayList<UsersModule>) usersDAO.getALL();
                String email = autoCompleteTextViewEmail.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                int begin_index = email.indexOf("@");
                int end_index = email.indexOf(".");
                String domain_name = email.substring(begin_index + 1, end_index);
                if (email.equals("foodapp@admin.com") && pass.equals("vung123")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();


                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    int temp = 0;


                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        if (email.equals("admin@gmail.com")) {
                                            rememberUser(email, pass, chbRemember.isChecked());
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(intent);

                                        } else {
                                            progressDialog.dismiss();
                                            rememberUser(email, pass, chbRemember.isChecked());
                                            item = new UsersModule();
                                            item.bitmap = "null";
                                            item.name = "null";
                                            item.email = email;
                                            item.pass = pass;
                                            item.address = mLocation;
                                            item.phoneNumber = "null";
                                            item.feedback = "null";
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
                                                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                                                }
                                            }


                                        }
                                    } else {
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
        tvTitleApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usersDAO.checkLogin(autoCompleteTextViewEmail.getText().toString().trim(), edPass.getText().toString().trim()) > 0) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    rememberUser(autoCompleteTextViewEmail.getText().toString().trim(), edPass.getText().toString().trim(), chbRemember.isChecked());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void init() {
        chbRemember = findViewById(R.id.checkBoxRemember);
        tvTitleApp = findViewById(R.id.tvTitleApp);
        autoCompleteTextViewEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btnSignIn);
//        imgShowHidePwd = findViewById(R.id.img_show_hide_pwd);
        progressDialog = new ProgressDialog(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(LoginActivity.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                                    edLocation.setText("" + addresses.get(0).getAddressLine(0));
                                    mLocation = addresses.get(0).getAddressLine(0);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    });


        } else {

            askPermission();

        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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