package com.fpoly.foodapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.modules.UsersModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateInfoUserByAdminActivity extends AppCompatActivity {
    static UsersDAO usersDAO;
    private UsersModule item;
    private ArrayList<UsersModule> listUser;
    private EditText edEmail, edPass, edName, edPhone, edAddress, edFeedback;
    private Button btnSave, btnCancel;
    private TextView tvDelete;
    private ImageView imgAvatar;
    private String uri_main = "";

    String realPath = "";

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info_user_by_admin);
        init();

        SharedPreferences pref = getSharedPreferences("INFO_USER", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");

        int id = pref.getInt("ID", 0);
        listUser = (ArrayList<UsersModule>) usersDAO.getALLByEmail(email);
//        String feedBack = usersDAO.getFeedback(email);
        item = listUser.get(0);
        imgAvatar.setImageBitmap(convert(item.bitmap));
        edEmail.setText(email);
        edEmail.setEnabled(false);
        edPass.setText(item.pass);
        if (item.bitmap.equals("null")){
            imgAvatar.setImageResource(R.drawable.user);
        }
        if (item.name.equals("null")) {
            edName.setText("");
        } else {
            edName.setText(item.name);
        }
        if (item.phoneNumber.equals("null")) {
            edPhone.setText("");
        } else {
            edPhone.setText(item.phoneNumber);
        }
        if (item.address.equals("null")){
            edAddress.setText("");
        }
        edAddress.setText(item.address);
        if (!(item.feedback.equals("null") || item.feedback.isEmpty())){
            edFeedback.setText(item.feedback);
        }




        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                intentActivityResultLauncher.launch(pickIntent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate() > 0) {
                    item = new UsersModule();
                    if (! uri_main.equals("")){
                        item.bitmap = uri_main;
                    }
                    else {
                        item.bitmap = "null";
                    }

                    item.email = edEmail.getText().toString().trim();
                    item.name = edName.getText().toString().trim();
                    item.pass = edPass.getText().toString().trim();
                    item.phoneNumber = edPhone.getText().toString().trim();
                    item.address = edAddress.getText().toString().trim();
                    if (usersDAO.updateAll(item) > 0) {
                        Toast.makeText(UpdateInfoUserByAdminActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateInfoUserByAdminActivity.this, ListUserActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(UpdateInfoUserByAdminActivity.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(UpdateInfoUserByAdminActivity.this, "Điền đủ thông tin !", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateInfoUserByAdminActivity.this, ListUserActivity.class));
                finishAffinity();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UpdateInfoUserByAdminActivity.this)
                        .setTitle("Warning")
                        .setMessage("Do you really want to delete ?")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                item = new UsersModule();
                                if (usersDAO.delete(id) > 0) ;
                                {
                                    Toast.makeText(UpdateInfoUserByAdminActivity.this, "Đã xoá " + edName.getText().toString(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UpdateInfoUserByAdminActivity.this, ListUserActivity.class));
                                    finishAffinity();
                                }


                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        .show();
            }
        });

    }

    private void init() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        usersDAO = new UsersDAO(getApplicationContext());
        listUser = new ArrayList<>();
        edEmail = findViewById(R.id.edEmailUser);
        edPass = findViewById(R.id.edPassUser);
        edName = findViewById(R.id.edNameUser);
        edPhone = findViewById(R.id.edPhoneNumberUser);
        edAddress = findViewById(R.id.edAddress);
        edFeedback = findViewById(R.id.edFeedback);
        btnSave = findViewById(R.id.btnSaveInfoUser);
        btnCancel = findViewById(R.id.btnCancelUser);
        tvDelete = findViewById(R.id.tvDeleteUser);
        imgAvatar = findViewById(R.id.imgUser);

    }

    private int validate() {
        int check = -1;
        String name = edName.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        if (name.isEmpty() || pass.isEmpty() || phone.isEmpty() || address.isEmpty()) {
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
                                uri_main = uri +"";
                                realPath = getRealPathFromURI(uri);
                                Bitmap bitmap = null;
                                try {
                                    item = new UsersModule();
                                    bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
                                    imgAvatar.setImageBitmap(bitmap);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

    public Bitmap convert(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(UpdateInfoUserByAdminActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
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