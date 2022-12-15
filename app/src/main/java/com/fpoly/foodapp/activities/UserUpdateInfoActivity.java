package com.fpoly.foodapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.UsersModule;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUpdateInfoActivity extends AppCompatActivity {
    ImageView imgPhoto;
    EditText edFullName, edPhoneNumber, edAddress;
    Button btnSave;
    public static final int REQUEST_CODE_IMG = 100;
    static UsersDAO usersDAO;
    UsersModule item;
    ImageView imgProfile;
    String realPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_info);
        init();
        item = new UsersModule();
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });

        SharedPreferences pref = getApplication().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");

        try {
            String path = usersDAO.getUriImg(email);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), Uri.parse(path));
            imgPhoto.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String fullName = usersDAO.getNameUser(email);
        String phoneNumber = usersDAO.getPhone(email);
        String address = usersDAO.getAddress(email);
        edAddress.setText(address);
        if (!(fullName.equals("null") && phoneNumber.equals("null"))) {
            edFullName.setText(fullName);
            edPhoneNumber.setText(phoneNumber);

        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edFullName.getText().toString().trim();
                String phoneNumber = edPhoneNumber.getText().toString().trim();
                String address = edAddress.getText().toString().trim();
                if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đủ thông tin !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String rePhone = "^0\\d{9}$";
                    Pattern pattern = Pattern.compile(rePhone);
                    Matcher matcher = pattern.matcher(phoneNumber);
                    if (!matcher.find()) {
                        Toast.makeText(getApplicationContext(), "Nhập đúng định dạng số điện thoại !", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    item.email = email;
                    item.name = fullName;
                    item.phoneNumber = phoneNumber;
                    item.address = address;
                    if (usersDAO.updateProfile(item) > 0) {
                        Toast.makeText(getApplicationContext(), "Update Success !", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }
        });

    }

    private void init() {
        usersDAO = new UsersDAO(getApplicationContext());
        imgPhoto = findViewById(R.id.imgProfileEdit);
        edFullName = findViewById(R.id.edEditName);
        edPhoneNumber = findViewById(R.id.edEditPhoneNumber);
        edAddress = findViewById(R.id.edEditAddress);
        btnSave = findViewById(R.id.btnSave);
        imgProfile = findViewById(R.id.imgProfileEdit);
    }

    //getRealPathFromURI
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
                                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                                Bitmap bitmap = null;
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    imgProfile.setImageBitmap(bitmap);
                                    item.bitmap = "" + uri;
                                    item.name = "null";
                                    item.email = pref.getString("EMAIL", "");
                                    item.pass = pref.getString("PASSWORD", "");
                                    item.phoneNumber = "null";
                                    item.address = "null";
                                    if (usersDAO.updateImg(item) > 0) {
                                        Log.d("path", String.valueOf(uri));
                                        Toast.makeText(getApplicationContext(), "Lưu ảnh thành công !", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

    //onClickRequestPermission
    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, REQUEST_CODE_IMG);
        }
    }

    //openGallery
    public void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        intentActivityResultLauncher.launch(pickIntent);
    }
}