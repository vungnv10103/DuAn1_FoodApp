package com.fpoly.foodapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fpoly.foodapp.DAO.CategoryDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.adapters.category.ItemCategory;

import java.io.IOException;

public class AddItemCategoryActivity extends AppCompatActivity {
    EditText edName;
    ImageView imgAvatar, imgDeleteNameCate;
    Button btnOpenGallery, btnSave, btnCancel;
    public static final int PICK_IMAGE = 1;
    public static final int KITKAT_VALUE = 1002;
    String realPath = "";
    public static final int GALLERY_INTENT_CALLED = 1;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 2;

    static CategoryDAO categoryDAO;
    ItemCategory item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_category);
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
                String defaultAvatar = imgAvatar.getTag().toString();

                if (validate() > 0) {
                    if (defaultAvatar.equals("default_avatar")) {
                        Toast.makeText(AddItemCategoryActivity.this, "Chưa chọn ảnh !", Toast.LENGTH_SHORT).show();
                    } else {
                        // code
                        item = new ItemCategory();
                        SharedPreferences pref = getSharedPreferences("URI_IMG", MODE_PRIVATE);
                        String uri = pref.getString("img", "");
                        item.setImg(uri);
                        item.setName(edName.getText().toString().trim());
                        if (categoryDAO.insert(item) > 0) {
                            Toast.makeText(AddItemCategoryActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddItemCategoryActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }

                        startActivity(new Intent(AddItemCategoryActivity.this, MainActivity.class));
                        finishAffinity();
                    }

                } else {
                    Toast.makeText(AddItemCategoryActivity.this, "Nhập tên loại sản phẩm !", Toast.LENGTH_SHORT).show();
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

//                Intent intent;
//
//                if (Build.VERSION.SDK_INT < 19) {
//                    intent = new Intent();
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, KITKAT_VALUE);
//                } else {
//                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, KITKAT_VALUE);
//                }

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

    public void init() {
        edName = findViewById(R.id.edNameItemCategory);
        imgAvatar = findViewById(R.id.imgAvatarItemRecommend);
        btnOpenGallery = findViewById(R.id.btnOpenCamera);
        btnSave = findViewById(R.id.btnSaveItem);
        btnCancel = findViewById(R.id.btnCancel);
        categoryDAO = new CategoryDAO(getApplication());
        imgDeleteNameCate = findViewById(R.id.imgDeleteNameCate);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == KITKAT_VALUE && resultCode == Activity.RESULT_OK) {
//            if (data == null) {
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
}