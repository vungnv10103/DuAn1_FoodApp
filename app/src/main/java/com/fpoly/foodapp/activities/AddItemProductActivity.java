package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.CategoryDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.category.ItemCategory;
import com.fpoly.foodapp.adapters.item_product.ItemProduct;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddItemProductActivity extends AppCompatActivity {
    private EditText edName, edCost, edQuantity, edLocation, edType;
    ImageView imgAvatar;
    Button btnOpenGallery, btnSave, btnCancel, btnRefresh;
    public static final int KITKAT_VALUE = 1002;
    private final static int REQUEST_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;

//    static ProductDAO productDAO;
    ItemProduct item;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_product);

        init();
        getLastLocation();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.setText("");
                imgAvatar.setImageResource(R.drawable.avatar_default);
                imgAvatar.setTag("default_avatar");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String defaultAvatar = imgAvatar.getTag().toString();

                if (validate() > 0) {
                    if (defaultAvatar.equals("default_avatar")) {
                        Toast.makeText(AddItemProductActivity.this, "Chưa chọn ảnh !", Toast.LENGTH_SHORT).show();
                    } else {
                        // code
                        item = new ItemProduct();
                        SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
                        String bitmap = pref.getString("img", "");
//                        item.setResource_image(bitmap);
//                        item.setTitle(edName.getText().toString().trim());
//                        item.setQuantities(Integer.parseInt(edQuantity.getText().toString().trim()));
//                        item.setMoney(Double.parseDouble(edCost.getText().toString().trim()));
//                        item.setLocation(edLocation.getText().toString().trim());
//                        item.setType(edType.getText().toString().trim());

//                        if (productDAO.insert(item) > 0) {
//                            Toast.makeText(AddItemProductActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(AddItemProductActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
//                        }

                        startActivity(new Intent(AddItemProductActivity.this, MainActivity.class));
                        finishAffinity();
                    }

                } else {
                    Toast.makeText(AddItemProductActivity.this, "Nhập tên loại sản phẩm !", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                Intent intent;

                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, KITKAT_VALUE);
                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(intent, KITKAT_VALUE);
                }

            }
        });


    }

    public void init() {
        edName = findViewById(R.id.edNameItemProduct);
        edCost = findViewById(R.id.edCostItemProduct);
        edQuantity = findViewById(R.id.edQuantity);
        edLocation = findViewById(R.id.edLocation);
        edType = findViewById(R.id.edType);
        imgAvatar = findViewById(R.id.imgAvatarItemProduct);
        btnOpenGallery = findViewById(R.id.btnOpenCamera);
        btnSave = findViewById(R.id.btnSaveItem);
        btnCancel = findViewById(R.id.btnCancel);
        btnRefresh = findViewById(R.id.btnRefresh);
//        productDAO = new ProductDAO(getApplication());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KITKAT_VALUE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
                imgAvatar.setImageBitmap(bitmap);
                imgAvatar.setTag("ok");
                SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("img", "" + bitmap);

                // lưu lại
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public int validate() {
        int check = -1;
        String name = edName.getText().toString().trim();
        String quantity = edQuantity.getText().toString().trim();
        String cost = edCost.getText().toString().trim();
        String type = edType.getText().toString().trim();
        if (name.isEmpty() || quantity.isEmpty() || cost.isEmpty() || type.isEmpty()) {
            check = -1;
        } else {
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
                                Geocoder geocoder = new Geocoder(AddItemProductActivity.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    edLocation.setText("" + addresses.get(0).getAddressLine(0));


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
        ActivityCompat.requestPermissions(AddItemProductActivity.this, new String[]
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
}