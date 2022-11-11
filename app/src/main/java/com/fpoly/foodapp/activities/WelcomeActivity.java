package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fpoly.foodapp.R;

public class WelcomeActivity extends AppCompatActivity {

    TextView txtlogin;
    Button btnres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        txtlogin = findViewById(R.id.txtlogin);
        btnres = findViewById(R.id.btnres);
        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this , ReginstrationActivity.class);
                startActivity(intent);

            }
        });
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this , Login.class);
                startActivity(intent);
            }
        });


    }
}