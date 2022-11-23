package com.fpoly.foodapp.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @Multipart
    @POST("upload_img.php")
    Call<String> upload_photo(@Part MultipartBody.Part photo);
}
