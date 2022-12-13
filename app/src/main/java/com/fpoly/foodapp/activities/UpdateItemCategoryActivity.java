package com.fpoly.foodapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
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

import com.fpoly.foodapp.DAO.CategoryDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.category.ItemCategory;

import java.io.IOException;

public class UpdateItemCategoryActivity extends AppCompatActivity {
    EditText edName;
    ImageView imgAvatar, imgDeleteNameCate;
    Button btnOpenGallery, btnSave, btnCancel, btnDelete;
    public static final int PICK_IMAGE = 1;
    String realPath = "";

    static CategoryDAO categoryDAO;
    ItemCategory item;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item_category);

        init();
        SharedPreferences pref = getSharedPreferences("INFO_ITEM", MODE_PRIVATE);
        int id = pref.getInt("ID",0);
        String name = pref.getString("NAME", "");
        String img = pref.getString("IMG", "");
        edName.setText(name);
        imgAvatar.setImageBitmap(convert(img));


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
                    imgDeleteNameCate.setVisibility(View.VISIBLE);
                    imgDeleteNameCate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edName.setText("");
                        }
                    });
                } else {
                    imgDeleteNameCate.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.setText("");
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
                    item = new ItemCategory();
                    SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
                    String uri = pref.getString("img", "");
                    SharedPreferences pref1 = v.getContext().getSharedPreferences("INFO_ITEM", MODE_PRIVATE);
                    int id = pref1.getInt("ID", 0);

                    item.setId(id);
                    item.setImg(uri);
                    item.setName(edName.getText().toString().trim());
                    if (categoryDAO.updateAll(item) > 0) {
                        Toast.makeText(UpdateItemCategoryActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateItemCategoryActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }

                    startActivity(new Intent(UpdateItemCategoryActivity.this, MainActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(UpdateItemCategoryActivity.this, "Nhập tên loại sản phẩm !", Toast.LENGTH_SHORT).show();
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
                new AlertDialog.Builder(UpdateItemCategoryActivity.this)
                        .setTitle("Warning")
                        .setMessage("Do you really want to delete ?")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                categoryDAO.delete(name);
                                Toast.makeText(UpdateItemCategoryActivity.this, "Đã xoá " + name  + "", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateItemCategoryActivity.this, MainActivity.class));
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
        edName = findViewById(R.id.edNameItemCategoryDetail);
        imgAvatar = findViewById(R.id.imgAvatarItemCateDetail);
        btnOpenGallery = findViewById(R.id.btnOpenCameraDetail);
        btnSave = findViewById(R.id.btnSaveItemCateDetail);
        btnDelete = findViewById(R.id.btnDeleteItemCategory);
        btnCancel = findViewById(R.id.btnCancelCateDetail);
        categoryDAO = new CategoryDAO(getApplication());
        imgDeleteNameCate = findViewById(R.id.imgDeleteNameCateDetail);
    }


    public int validate() {
        int check = -1;
        String name = edName.getText().toString().trim();
        if (name.isEmpty()) {
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
                                    item = new ItemCategory();
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
    public Bitmap convert(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
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