package com.fpoly.foodapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.R;

import java.io.IOException;

public class AddItemRecommendActivity extends AppCompatActivity {
    EditText edName, edCost;
    ImageView imgAvatar;
    Button btnOpenGallery, btnSave, btnCancel;
    public static final int PICK_IMAGE = 1;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_recommend);

        init();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.setText("");
                edCost.setText("");
                imgAvatar.setImageResource(R.drawable.avatar_default);
                imgAvatar.setTag("default_avatar");
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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
                imgAvatar.setImageBitmap(bitmap);
                imgAvatar.setTag("ok");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
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
}