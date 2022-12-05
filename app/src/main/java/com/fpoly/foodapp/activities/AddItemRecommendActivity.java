package com.fpoly.foodapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.RecommendDAO;
import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.category.ItemCategory;
import com.fpoly.foodapp.adapters.recommend.ItemRecommend;
import com.fpoly.foodapp.modules.RecommendedModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddItemRecommendActivity extends AppCompatActivity {
    EditText edName, edCost;
    ImageView imgAvatar, imgDeleteNameRecommend, imgDeleteCost;
    Button btnOpenGallery, btnSave, btnCancel;
    public static final int PICK_IMAGE = 1;
    String realPath = "";
    String mLocation = "";
    private final static int REQUEST_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;

    static RecommendDAO recommendDAO;
    ItemRecommend item;

    static UsersDAO usersDAO;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_recommend);

        init();
       edName.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               if (!edName.getText().toString().trim().isEmpty()) {
                   imgDeleteNameRecommend.setVisibility(View.VISIBLE);
                   imgDeleteNameRecommend.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           edName.setText("");
                       }
                   });
               } else {
                   imgDeleteNameRecommend.setVisibility(View.INVISIBLE);
               }

           }
       });
       edCost.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               if (!edCost.getText().toString().trim().isEmpty()) {
                   imgDeleteCost.setVisibility(View.VISIBLE);
                   imgDeleteCost.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           edName.setText("");
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
                String defaultAvatar = imgAvatar.getTag().toString();

                if (validate() > 0){
                    if (defaultAvatar.equals("default_avatar")){
                        Toast.makeText(AddItemRecommendActivity.this, "Chưa chọn ảnh !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // code
                        item = new ItemRecommend();
                        SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
                        String uri = pref.getString("img", "");
                        SharedPreferences pref1 = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                        String email = pref1.getString("EMAIL", "");
                        int idUser = usersDAO.getIDUser(email);
                        getLastLocation();
                        item.idUser = idUser;
                        item.img_resource = uri;
                        item.title = edName.getText().toString().trim();
                        item.price = Double.parseDouble(edCost.getText().toString().trim());
                        item.favourite = 0;
                        item.location = mLocation;
                        if (recommendDAO.insert(item) > 0){
                            Toast.makeText(AddItemRecommendActivity.this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AddItemRecommendActivity.this, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(AddItemRecommendActivity.this, MainActivity.class));
                        finishAffinity();
                    }

                }
                else {
                    Toast.makeText(AddItemRecommendActivity.this, "Nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
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
    }
    public void init(){
        edName = findViewById(R.id.edNameItemRecommend);
        edCost = findViewById(R.id.edCostItemRecommend);
        imgAvatar = findViewById(R.id.imgAvatarItemRecommend);
        btnOpenGallery = findViewById(R.id.btnOpenCamera);
        btnSave = findViewById(R.id.btnSaveItem);
        btnCancel = findViewById(R.id.btnCancel);
        recommendDAO = new RecommendDAO(getApplication());
        imgDeleteNameRecommend = findViewById(R.id.imgDeleteNameRecommend);
        imgDeleteCost = findViewById(R.id.imgDeleteCost);
        usersDAO = new UsersDAO(getApplication());
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
//            if (data == null) {
//                //Display an error
//                return;
//            }
//            Uri uri = data.getData();
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
//                imgAvatar.setImageBitmap(bitmap);
//                imgAvatar.setTag("ok");
//                SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("img", "" + bitmap);
//
//                // lưu lại
//                editor.commit();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    public int validate(){
        int check = -1;
        String name = edName.getText().toString().trim();
        String cost = edCost.getText().toString().trim();
        if (name.isEmpty() || cost.isEmpty() ){
            check = -1;
        }else {
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
                                Geocoder geocoder = new Geocoder(AddItemRecommendActivity.this, Locale.getDefault());
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
        ActivityCompat.requestPermissions(AddItemRecommendActivity.this, new String[]
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
}