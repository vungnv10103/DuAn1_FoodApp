package com.fpoly.foodapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fpoly.foodapp.DAO.LocationDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.LocationAdapter;
import com.fpoly.foodapp.adapters.item_product.ItemProduct;
import com.fpoly.foodapp.modules.LocationModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddLocationActivity extends AppCompatActivity implements Runnable {
    private Toolbar toolbar;
    ImageView imgRefresh;
    private EditText edName, edPhone, edAddess;
    Button btnSave, btnCancel, btnRefresh;
    public static final int KITKAT_VALUE = 1002;
    private final static int REQUEST_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    String location = "";
    LocationAdapter locationAdapter;
    static LocationDAO locationDAO;
    List<LocationModule> listLocal;
    LocationModule item;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        init();
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL","");
        String location = edAddess.getText().toString().trim();
        toolbar = findViewById(R.id.toolBarCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Địa chỉ mới");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRefresh.clearAnimation();
                RotateAnimation anim = new RotateAnimation(30, 360, imgRefresh.getWidth() / 2, imgRefresh.getHeight() / 2);
                anim.setFillAfter(true);
                anim.setRepeatCount(0);

                anim.setDuration(1000);
                imgRefresh.startAnimation(anim);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getLastLocation();
                    }
                }, 1000);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() > 0){
                    item = new LocationModule();
                    item.name = edName.getText().toString().trim();
                    item.phone = edPhone.getText().toString().trim();
                    item.location = edAddess.getText().toString().trim();
                    item.email = email;
                    if (locationDAO.insert(item) > 0){
                        Toast.makeText(AddLocationActivity.this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        onStateNotSaved();
                        startActivity(new Intent(AddLocationActivity.this, SelectLocationActivity.class));
                    }
                    else {
                        Toast.makeText(AddLocationActivity.this, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddLocationActivity.this, "Điền đủ thông tin !", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public void init() {
        imgRefresh = findViewById(R.id.imgRefresh);
        edName = findViewById(R.id.edFullName);
        edPhone = findViewById(R.id.edPhone);
        edAddess = findViewById(R.id.edAddLocation);
        btnSave = findViewById(R.id.btnSaveLocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationDAO = new LocationDAO(getApplicationContext());
    }


    public int validate() {
        int check = -1;
        String name = edName.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String location = edAddess.getText().toString().trim();
        if ( name.isEmpty() || phone.isEmpty() || location.isEmpty()){
            check = -1;
        }
        else {
            check = 1;
        }
        return check;
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(AddLocationActivity.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    edAddess.setText("" + addresses.get(0).getAddressLine(0));
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
        ActivityCompat.requestPermissions(AddLocationActivity.this, new String[]
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

    @Override
    public void run() {
        if (edName.getText().toString().isEmpty() || edPhone.getText().toString().isEmpty() || location.isEmpty()) {
            btnSave.setBackgroundColor(Color.GRAY);
            btnSave.setOnClickListener(null);
        } else {
            btnSave.setBackgroundColor(Color.RED);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(AddLocationActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}