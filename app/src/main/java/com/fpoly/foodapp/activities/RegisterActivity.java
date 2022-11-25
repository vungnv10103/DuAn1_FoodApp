package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText edEmail, edPass, edConfirmPass;
    Button btnSignUp;
    ImageView imgShowHidePwd, imgShowConfirmPass;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edEmail = findViewById(R.id.edEmailRegister);
        edPass = findViewById(R.id.edPassWord);
        edConfirmPass = findViewById(R.id.edConfirmPass);
        imgShowHidePwd = findViewById(R.id.img_show_hide_pwd);
        imgShowConfirmPass = findViewById(R.id.img_show_hide_confirm_pwd);
        btnSignUp = findViewById(R.id.register);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSingUp();
            }
        });
        imgShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    edPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgShowHidePwd.setImageResource(R.drawable.ic_baseline_eye_off_24);
                }
                else {
                    edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgShowHidePwd.setImageResource(R.drawable.ic_baseline_eye_on_24);
                }

            }
        });
        imgShowConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edConfirmPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    edConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgShowConfirmPass.setImageResource(R.drawable.ic_baseline_eye_off_24);
                }
                else {
                    edConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgShowConfirmPass.setImageResource(R.drawable.ic_baseline_eye_on_24);
                }

            }
        });
    }



    private void onClickSingUp() {
        String email = edEmail.getText().toString().trim();
        String pass  = edPass.getText().toString().trim();
        String confirm  = edConfirmPass.getText().toString();
        if (validate()>0){
           if(confirm.equals(pass)){
               FirebaseAuth auth = FirebaseAuth.getInstance();
               auth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
//                           Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
//                           startActivity(intent);
//                           //đóng tất cả các activity trước main
//                           finishAffinity();
                           Toast.makeText(RegisterActivity.this, "đăng kí thành  công", Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(RegisterActivity.this, "Đăng kí thất bại.", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }else {
               Toast.makeText(this, "Mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
           }
        }else {
            Toast.makeText(this, "Điền đủ thông tin.", Toast.LENGTH_SHORT).show();
        }

    }

    public void login(View view) {
        startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
    }
    public int validate(){
        int check = -1;
        String email = edEmail.getText().toString().trim();
        String pass  = edPass.getText().toString().trim();
        String confirm  = edConfirmPass.getText().toString();
        if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty()){
            check = -1;
        }else {
            check = 1;
        }
        return check;
    }
}