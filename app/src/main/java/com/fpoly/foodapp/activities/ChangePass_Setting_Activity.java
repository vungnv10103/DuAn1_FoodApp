package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.UsersModule;

public class ChangePass_Setting_Activity extends AppCompatActivity {
    private EditText edCurrentPass, edNewPass, edConfirmPass;
    private Button btnChangePass;
    static UsersDAO usersDAO;
    UsersModule item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_setting);
        init();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        usersDAO = new UsersDAO(getApplicationContext());
        String currentPass = usersDAO.getCurrentPass(email);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0) {
                    String passOld = edCurrentPass.getText().toString().trim();
                    String passNew = edNewPass.getText().toString().trim();
                    String passConfirm = edConfirmPass.getText().toString().trim();
                    if (!passOld.equals(currentPass)) {
                        Toast.makeText(ChangePass_Setting_Activity.this, "Mật khẩu cũ sai !", Toast.LENGTH_SHORT).show();

                    } else if (!passNew.equals(passConfirm)) {
                        Toast.makeText(ChangePass_Setting_Activity.this, "Mật khẩu mới phải trùng nhau !", Toast.LENGTH_SHORT).show();
                    } else {
                        item = new UsersModule();
                        item.email = email;
                        item.pass = passConfirm;
                        if (usersDAO.updatePass(item) > 0) {
                            Toast.makeText(ChangePass_Setting_Activity.this, "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finishAffinity();
                        }
                        else {
                            Toast.makeText(ChangePass_Setting_Activity.this, "Đổi mật khẩu thất bại !", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(ChangePass_Setting_Activity.this, "Vui lòng điền đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void init() {
        edCurrentPass = findViewById(R.id.changePass_currentPass);
        edNewPass = findViewById(R.id.changePass_newPass);
        edConfirmPass = findViewById(R.id.changePass_checkPass);
        btnChangePass = findViewById(R.id.btn_ChangePass_setting);
    }

    private int validate() {
        int check = -1;
        String currentPass = edCurrentPass.getText().toString().trim();
        String newPass = edNewPass.getText().toString().trim();
        String confirmPass = edConfirmPass.getText().toString().trim();

        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            check = -1;
        } else {
            check = 1;
        }
        return check;
    }
}
