package com.fpoly.foodapp.ui.account;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.account_load_image.APIUtils;
import com.fpoly.foodapp.account_load_image.DataClient;
import com.fpoly.foodapp.activities.DealsActivity;
import com.fpoly.foodapp.activities.LoginActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountManagerFragment extends Fragment {
    LinearLayout linearLayout, btnDeals;
    ImageView imgProfile;
    String realPath = "";
    public static final int REQUEST_CODE_IMG = 100;

    private ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent == null) {
                    return;
                }
                Uri uri = intent.getData();
                realPath = getRealPathFromURI(uri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    imgProfile.setImageBitmap(bitmap);
                    File file = new File(realPath);
                    String file_path = file.getAbsolutePath();

                    String [] nameFile = file_path.split("\\.");
                    file_path = nameFile[0] + System.currentTimeMillis() + "." + nameFile[1];

                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("upload_files", file_path, requestBody);

                    DataClient dataClient = APIUtils.dataClient();
                    retrofit2.Call<String> callBack = dataClient.upload_photo(part);
                    callBack.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                            if (response != null){
                                String message = response.body();
//                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
//                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        linearLayout = root.findViewById(R.id.Logout);
        btnDeals = root.findViewById(R.id.btn_Account_Deals);

        // Log out
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        imgProfile = root.findViewById(R.id.profile_image);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();

            }
        });

        // Deals
        btnDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DealsActivity.class));
            }
        });

        return root;
    }

    //getRealPathFromURI
    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    //onClickRequestPermission
    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission, REQUEST_CODE_IMG);
        }
    }

    //openGallery
    public void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        intentActivityResultLauncher.launch(pickIntent);
    }
}
