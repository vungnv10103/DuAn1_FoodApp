package com.fpoly.foodapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.foodapp.Admin.AdminActivity;
import com.fpoly.foodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText edEmail, edPass;
    Button btnLogin;
    ImageView imgShowHidePwd;
    private CheckBox chbRemember;
    private ProgressDialog progressDialog;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        chbRemember = findViewById(R.id.checkBoxRemember);
        edEmail = findViewById(R.id.edEmail);
        edPass =findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btnSignIn);
        imgShowHidePwd = findViewById(R.id.img_show_hide_pwd);
        progressDialog = new ProgressDialog(this);

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edEmail.setText(pref.getString("EMAIL", ""));
        edPass.setText(pref.getString("PASSWORD", ""));
        chbRemember.setChecked(pref.getBoolean("REMEMBER", false));
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


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                progressDialog.show();
                auth.signInWithEmailAndPassword(email , pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    if(email.equals("admin@gmail.com".trim())){
                                        Intent intent = new Intent(LoginActivity.this , AdminActivity.class);
                                        startActivity(intent);
                                    }else {
                                        progressDialog.dismiss();
                                        rememberUser(email, pass, chbRemember.isChecked());
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(intent);
        finishAffinity();
    }
    public void rememberUser(String u , String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(!status){
            editor.clear();
        }else {
            // lưu dữ liệu
            editor.putString("EMAIL", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        // lưu lại
        editor.commit();
    }
    public void showInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null){

        }


    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce ) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
    }
}