package com.fpoly.foodapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UpdateItemRecommendActivity extends AppCompatActivity {
    EditText edNameDetail, edCostDetail, edDescription, edTimeDelay, edCalo;
    ImageView imgAvatar, imgDeleteNameRecommend, imgDeleteCost;
    Button btnOpenGallery, btnSave, btnCancel, btnDelete;
    public static final int PICK_IMAGE = 1;
    String realPath = "";
    String mLocation = "";

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;

    static RecommendDAO recommendDAO;
    ItemRecommend item;

    static UsersDAO usersDAO;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item_recommend);

        init();
        SharedPreferences pref = getSharedPreferences("INFO_ITEM", MODE_PRIVATE);
        int id = pref.getInt("ID", 0);
        String name = pref.getString("NAME", "");
        edNameDetail.setText(name);
        edCostDetail.setText(pref.getString("PRICE", ""));
        imgAvatar.setImageBitmap(convert(pref.getString("IMG", "")));
        edDescription.setText(pref.getString("DESCRIPTION", ""));
        edTimeDelay.setText(pref.getString("TIMEDELAY", ""));
        edCalo.setText(pref.getString("CALO", ""));
        edNameDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edNameDetail.getText().toString().trim().isEmpty()) {
                    imgDeleteNameRecommend.setVisibility(View.VISIBLE);
                    imgDeleteNameRecommend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edNameDetail.setText("");
                        }
                    });
                } else {
                    imgDeleteNameRecommend.setVisibility(View.INVISIBLE);
                }

            }
        });
        edCostDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edCostDetail.getText().toString().trim().isEmpty()) {
                    imgDeleteCost.setVisibility(View.VISIBLE);
                    imgDeleteCost.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edCostDetail.setText("");
                        }
                    });
                } else {
                    imgDeleteCost.setVisibility(View.INVISIBLE);
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAvatar.setImageResource(R.drawable.avatar_default);
                imgAvatar.setTag("default_avatar");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finishAffinity();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate() > 0) {

                    // code
                    item = new ItemRecommend();
                    SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
                    String uri = pref.getString("img", "");
                    SharedPreferences pref1 = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                    String email = pref1.getString("EMAIL", "");
                    int idUser = usersDAO.getIDUser(email);
                    getLastLocation();

                    item.id = id;
                    item.idUser = idUser;
                    item.img_resource = uri;
                    item.title = edNameDetail.getText().toString().trim();
                    item.price = Double.parseDouble(edCostDetail.getText().toString().trim());
                    item.favourite = 0;
                    item.description = edDescription.getText().toString().trim();
                    item.timeDelay = edTimeDelay.getText().toString().trim();
                    item.calo = Double.valueOf(edCalo.getText().toString().trim());
                    item.location = mLocation;
                    if (recommendDAO.updateAll(item) > 0) {
                        Toast.makeText(UpdateItemRecommendActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateItemRecommendActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(UpdateItemRecommendActivity.this, MainActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(UpdateItemRecommendActivity.this, "Nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                intentActivityResultLauncher.launch(pickIntent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UpdateItemRecommendActivity.this)
                        .setTitle("Warning")
                        .setMessage("Do you really want to delete ?")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                recommendDAO.delete(name);
                                Toast.makeText(UpdateItemRecommendActivity.this, "Đã xoá " + name, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateItemRecommendActivity.this, MainActivity.class));
                                finishAffinity();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        .show();

            }
        });
    }

    public void init() {
        edNameDetail = findViewById(R.id.edNameItemRecommendDetail);
        edCostDetail = findViewById(R.id.edCostItemRecommendDetail);
        edDescription = findViewById(R.id.edDescriptionDetail);
        edTimeDelay = findViewById(R.id.edTimeDelayDetail);
        edCalo = findViewById(R.id.edCaloDetail);
        imgAvatar = findViewById(R.id.imgAvatarItemRecommendDetail);
        btnOpenGallery = findViewById(R.id.btnOpenGalleryDetail);
        btnSave = findViewById(R.id.btnSaveItemDetail);
        btnCancel = findViewById(R.id.btnCancelDetail);
        btnDelete = findViewById(R.id.btnDeleteItemRecommend);
        recommendDAO = new RecommendDAO(getApplication());
        imgDeleteNameRecommend = findViewById(R.id.imgDeleteNameRecommendDetail);
        imgDeleteCost = findViewById(R.id.imgDeleteCostDetail);
        usersDAO = new UsersDAO(getApplication());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public int validate() {
        int check = -1;
        String name = edNameDetail.getText().toString().trim();
        String cost = edCostDetail.getText().toString().trim();
        String description = edDescription.getText().toString().trim();
        String time = edTimeDelay.getText().toString().trim();
        String calo = edCalo.getText().toString().trim();
        if (name.isEmpty() || cost.isEmpty() || description.isEmpty() || time.isEmpty() || calo.isEmpty()) {
            check = -1;
        } else {
            check = 1;
        }
        return check;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private ActivityResultLauncher<Intent> intentActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {

                            if (result.getResultCode() == RESULT_OK) {
                                Intent intent = result.getData();
                                if (intent == null) {
                                    return;
                                }
                                Uri uri = intent.getData();
                                realPath = getRealPathFromURI(uri);
                                Bitmap bitmap = null;
                                try {
                                    item = new ItemRecommend();
                                    bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
                                    imgAvatar.setImageBitmap(bitmap);
                                    imgAvatar.setTag("ok");
                                    SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("img", "" + uri);
                                    editor.commit();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(UpdateItemRecommendActivity.this, Locale.getDefault());
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

    public Bitmap convert(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(UpdateItemRecommendActivity.this, new String[]
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