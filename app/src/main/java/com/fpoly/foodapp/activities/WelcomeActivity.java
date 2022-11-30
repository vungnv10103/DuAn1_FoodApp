package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.R;

public class WelcomeActivity extends AppCompatActivity implements Runnable {

    TextView txtLogin;
    Button btnRegister;

    ConnectivityManager connectivityManager; // sử dụng service
    NetworkInfo myWifi, my4G;
    WifiManager wifiManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        txtLogin = findViewById(R.id.txt_login);
        btnRegister = findViewById(R.id.btn_register);
        phanQuyen();

        connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
        // xác định thông tin mạng
        myWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        my4G = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        if (myWifi.isConnected() || my4G.isConnected()) {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                    startActivity(intent);

                }
            });
            txtLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            run();
        }


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
                ActivityCompat.requestPermissions(WelcomeActivity.this,
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
    public void run() {
        Toast.makeText(this, "Vui lòng kết nối Internet để sử dụng ứng dụng !", Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(3000);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
