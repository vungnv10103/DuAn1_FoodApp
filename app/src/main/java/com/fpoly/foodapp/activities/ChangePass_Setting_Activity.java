package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.foodapp.R;

public class ChangePass_Setting_Activity extends AppCompatActivity {
    private EditText edCurrentPass, edNewPass, edConfirmPass;
    private Button btnChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_setting);
        init();
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0){
                    // code
                }
                else {
                    Toast.makeText(ChangePass_Setting_Activity.this, "Vui lòng điền đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void init(){
        edCurrentPass = findViewById(R.id.changePass_currentPass);
        edNewPass = findViewById(R.id.changePass_newPass);
        edConfirmPass = findViewById(R.id.changePass_checkPass);
        btnChangePass = findViewById(R.id.btn_ChangePass_setting);
    }
    private int validate(){
        int check = -1;
        String currentPass = edCurrentPass.getText().toString().trim();
        String newPass = edNewPass.getText().toString().trim();
        String confirmPass = edConfirmPass.getText().toString().trim();

        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()){
            check = -1;
        }else {
            check = 1;
        }
        return check;
    }
}
