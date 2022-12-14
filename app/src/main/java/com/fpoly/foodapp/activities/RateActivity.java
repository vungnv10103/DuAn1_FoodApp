package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.modules.UsersModule;
import com.fpoly.foodapp.ui.account.AccountManagerFragment;

public class RateActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button btnSend, btnCancel;
    EditText txtRecommend;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    static UsersDAO usersDAO;
    UsersModule item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ratingBar = findViewById(R.id.ratingFoodApp);
        btnSend = findViewById(R.id.btn_Rating_send);
        btnCancel = findViewById(R.id.btn_Cancel);
        txtRecommend = findViewById(R.id.rate_Recommend);
        usersDAO = new UsersDAO(getApplicationContext());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRate(ratingBar, txtRecommend);
            }
        });
    }

    // Check sao
    private void validateRate(RatingBar stars, EditText txtRecommend) {
        int starCount = (int)stars.getRating();
        String inRecommend = txtRecommend.getText().toString();

        setDiscuss(starCount, inRecommend);
        if (validate()>0){
            SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String email = pref.getString("EMAIL", "");
            item = new UsersModule();
            item.email = email;
            item.feedback = starCount + "-" + inRecommend;
            if (usersDAO.updateFeedBack(item) > 0){
                Toast.makeText(this, "???? g???i ph???n h???i !", Toast.LENGTH_SHORT).show();
            }
        }


    }
    
    // Output ph???n h???i l???i t???i kh??ch h??ng
    private void setDiscuss(int starCount, String inRecommend) {
        String reply = "";

        switch (starCount){
            case 0:
                if(inRecommend.isEmpty()){
                    reply = "Ooooohhh, h??nh nh?? b???n ??i???n thi???u th??ng tin r??i. B???n ??i???n ?????y ????? th??ng tin gi??p m??nh nh?? <3";
                }
                else {
                    reply = "C???m ??n b???n ???? ????ng g??p ?? ki???n cho s???n ph???m c???a ch??ng t??i, " +
                            "ch??ng t??i s??? c??? g???ng ph??t tri???n h??n n???a ????? ??em ?????n s??? tr???i nghi???m t???t nh???t cho c??c b???n <3 ";
                }
                break;

            case 1:
                reply = "Xin l???i b???n v?? ch??ng t??i ???? ????? cho b???n c?? m???t s??? tr???i nghi???m kh??ng t???t v???i app n??y, " +
                        "ch??ng t??i s??? c??? g???ng kh???c ph???c ???ng d???ng n??y ????? c?? th??? cho b???n m???t s??? tr???i nghi???m t???t nh???t c?? th???";
                break;

            case 2:
                reply = "Xin l???i b???n v?? ch??ng t??i ???? ????? cho b???n c?? m???t s??? tr???i nghi???m kh??ng t???t v???i app n??y , " +
                        "ch??ng t??i s??? c??? g???ng kh???c ph???c ???ng d???ng n??y ????? c?? th??? cho b???n m???t s??? tr???i nghi???m t???t nh???t c?? th???";
                break;

            default:
                reply = "C???m ??n b???n ???? ????nh gi?? cao v??? s???n ph???m c???a ch??ng t??i, " +
                        "ch??ng t??i s??? c??? g???ng ph??t tri???n h??n n???a ????? ??em ?????n s??? tr???i nghi???m t???t nh???t cho c??c b???n <3";
                break;
        }

        Toast.makeText(this, reply, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();

    }
    private int validate(){
        int check = -1;
        if (ratingBar.getRating() ==0 || txtRecommend.getText().toString().trim().isEmpty()){
            check = -1;
        }
        else {
            check = 1;
        }
        return check;
    }
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}