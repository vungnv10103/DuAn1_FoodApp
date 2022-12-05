package com.fpoly.foodapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
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

import java.io.IOException;

public class AddItemRecommendActivity extends AppCompatActivity {
    EditText edName, edCost;
    ImageView imgAvatar, imgDeleteNameRecommend, imgDeleteCost;
    Button btnOpenGallery, btnSave, btnCancel;
    public static final int PICK_IMAGE = 1;
    String realPath = "";

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

                        item.idUser = idUser;
                        item.img_resource = uri;
                        item.title = edName.getText().toString().trim();
                        item.price = Double.parseDouble(edCost.getText().toString().trim());
                        item.favourite = 0;
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
}