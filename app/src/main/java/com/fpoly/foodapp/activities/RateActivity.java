package com.fpoly.foodapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.fpoly.foodapp.R;

public class RateActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button btnSend;
    EditText txtRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ratingBar = findViewById(R.id.ratingFoodApp);
        btnSend = findViewById(R.id.btn_Rating_send);
        txtRecommend = findViewById(R.id.rate_Recommend);

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
                reply = "Xin lỗi bạn vì chúng tôi đã để cho bạn có một sự trải nghiệm không tốt với app này, " +
                        "chúng tôi sẽ cố gắng khắc phục ứng dụng này để có thể cho bạn một sự trải nghiệm tốt nhất có thể";
                break;

            default:
                reply = "Cảm ơn bạn đã đánh giá cao về sản phẩm của chúng tôi, " +
                        "chúng tôi sẽ cố gắng phát triển hơn nữa để đem đến sự trải nghiệm tốt nhất cho các bạn <3";
                break;
        }

        Toast.makeText(this, reply, Toast.LENGTH_SHORT).show();
    }
}