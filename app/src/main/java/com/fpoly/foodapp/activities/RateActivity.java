package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.modules.UsersModule;
import com.fpoly.foodapp.ui.account.AccountManagerFragment;

public class RateActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button btnSend;
    EditText txtRecommend;

    static UsersDAO usersDAO;
    UsersModule item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ratingBar = findViewById(R.id.ratingFoodApp);
        btnSend = findViewById(R.id.btn_Rating_send);
        txtRecommend = findViewById(R.id.rate_Recommend);
        usersDAO = new UsersDAO(getApplicationContext());


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
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        item = new UsersModule();
        item.email = email;
        item.feedback = starCount + "-" + inRecommend;
        if (usersDAO.updateFeedBack(item) > 0){
            Toast.makeText(this, "Đã lưu phản hồi !", Toast.LENGTH_SHORT).show();
        }

    }
    
    // Output phản hồi lại tới khách hàng
    private void setDiscuss(int starCount, String inRecommend) {
        String reply = "";

        switch (starCount){
            case 0:
                if(inRecommend.isEmpty()){
                    reply = "Ooooohhh, hình như bạn điền thiếu thông tin rùi. Bạn điền đầy đủ thông tin giúp mình nhé <3";
                }
                else {
                    reply = "Cảm ơn bạn đã đóng góp ý kiến cho sản phẩm của chúng tôi, " +
                            "chúng tôi sẽ cố gắng phát triển hơn nữa để đem đến sự trải nghiệm tốt nhất cho các bạn <3 ";
                }
                break;

            case 1:
                reply = "Xin lỗi bạn vì chúng tôi đã để cho bạn có một sự trải nghiệm không tốt với app này, " +
                        "chúng tôi sẽ cố gắng khắc phục ứng dụng này để có thể cho bạn một sự trải nghiệm tốt nhất có thể";
                break;

            case 2:
                reply = "Xin lỗi bạn vì chúng tôi đã để cho bạn có một sự trải nghiệm không tốt với app này , " +
                        "chúng tôi sẽ cố gắng khắc phục ứng dụng này để có thể cho bạn một sự trải nghiệm tốt nhất có thể";
                break;

            default:
                reply = "Cảm ơn bạn đã đánh giá cao về sản phẩm của chúng tôi, " +
                        "chúng tôi sẽ cố gắng phát triển hơn nữa để đem đến sự trải nghiệm tốt nhất cho các bạn <3";
                break;
        }

        Toast.makeText(this, reply, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();

    }
}