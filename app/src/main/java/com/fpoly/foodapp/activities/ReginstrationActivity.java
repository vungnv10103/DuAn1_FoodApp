package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class ReginstrationActivity extends AppCompatActivity {

    EditText edtemail , edtpass ,confirmpass;
    Button btnsingup;
    ImageView showpass;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reginstration);
        edtemail = findViewById(R.id.resedtemail);
        edtpass = findViewById(R.id.resedtpass);
        confirmpass = findViewById(R.id.resconfirmpass);
        showpass = findViewById(R.id.show_pass);
        btnsingup = findViewById(R.id.regiter);
        btnsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSingUp();
            }
        });
    }



    private void onClickSingUp() {
        String Email = edtemail.getText().toString().trim();
        String pass  = edtpass.getText().toString().trim();
        String confirm  = confirmpass.getText().toString();
        if(edtemail.length()==0){
            Toast.makeText(this, "không để trống", Toast.LENGTH_SHORT).show();
        }
        if(edtpass.length()==0){
            Toast.makeText(this, "không được đẻ trống pass", Toast.LENGTH_SHORT).show();
        }
        if(confirm.equals(pass)){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(Email , pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(ReginstrationActivity.this , MainActivity.class);
                        startActivity(intent);
                        //đóng tất cả các activity trước main
                        finishAffinity();
                    }else {
                        Toast.makeText(ReginstrationActivity.this, "đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(this, "mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }

    }

    public void login(View view) {
        startActivity(new Intent(ReginstrationActivity.this , Login.class));

    }
}