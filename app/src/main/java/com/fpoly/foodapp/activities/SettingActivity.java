package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.UsersModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingActivity extends AppCompatActivity {
    TextView btnChangePass, setting_Edit_Profile;
    static UsersDAO usersDAO;
    UsersModule item;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        usersDAO = new UsersDAO(getApplicationContext());
        item = new UsersModule();

        SharedPreferences pref = getApplication().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        progressDialog = new ProgressDialog(SettingActivity.this);
        btnChangePass = findViewById(R.id.setting_Edit_Pass);
        setting_Edit_Profile = findViewById(R.id.setting_Edit_Profile);
        setting_Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imgPhoto;
                EditText edFullName, edPhoneNumber, edAddress;
                Button btnSave;

                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.profile_detail);

                imgPhoto = dialog.findViewById(R.id.imgProfileEdit);
                edFullName = dialog.findViewById(R.id.edEditName);
                edPhoneNumber = dialog.findViewById(R.id.edEditPhoneNumber);
                edAddress = dialog.findViewById(R.id.edEditAddress);

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
                if (!(fullName.equals("null") && phoneNumber.equals("null") && address.equals("null"))) {
                    edFullName.setText(fullName);
                    edPhoneNumber.setText(phoneNumber);
                    edAddress.setText(address);
                }

                btnSave = dialog.findViewById(R.id.btnSave);


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
                            progressDialog.show();
                            if (usersDAO.updateProfile(item) > 0) {

                                Toast.makeText(getApplicationContext(), "Update Success !", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }


                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setAttributes(lp);
                dialog.show();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChangePass_Setting_Activity.class));
            }
        });
    }

    private int validate() {
        int check = -1;

        return check;
    }

}
