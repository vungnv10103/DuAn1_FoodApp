package com.fpoly.foodapp.ui.account;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fpoly.foodapp.DAO.UsersDAO;
import com.fpoly.foodapp.R;
import com.fpoly.foodapp.activities.DealsActivity;
import com.fpoly.foodapp.activities.LoginActivity;
import com.fpoly.foodapp.activities.RateActivity;
import com.fpoly.foodapp.activities.SettingActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.fpoly.foodapp.modules.UsersModule;


import java.io.IOException;
import java.util.ArrayList;


public class AccountManagerFragment extends Fragment {
    LinearLayout linearLayout, btnDeals, btnSetting, btnRating;
    TextView tvNameUser, tvEmail;
    ImageView imgProfile;
    String realPath = "";
    public static final int REQUEST_CODE_IMG = 100;
    static UsersDAO usersDAO;
    UsersModule item;
    ArrayList<UsersModule> list;

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
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    imgProfile.setImageBitmap(bitmap);
                    item.bitmap = "" + uri;
                    item.name = "null";
                    item.email = pref.getString("EMAIL", "");
                    item.pass = pref.getString("PASSWORD", "");
                    item.phoneNumber = "null";
                    item.address = "null";

                    if (usersDAO.updateImg(item) > 0) {
                        Toast.makeText(getActivity(), "Lưu ảnh thành công !", Toast.LENGTH_SHORT).show();
                    }
//                    rememberImg(uri);
//                    Toast.makeText(getActivity(), "" + realPath, Toast.LENGTH_SHORT).show();

//                    File file = new File(realPath);
//                    String file_path = file.getAbsolutePath();
//
//                    String [] nameFile = file_path.split("\\.");
//                    file_path = nameFile[0] + System.currentTimeMillis() + "." + nameFile[1];
//
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
//                    MultipartBody.Part part = MultipartBody.Part.createFormData("upload_files", file_path, requestBody);

//                    DataClient dataClient = APIUtils.dataClient();
//                    retrofit2.Call<String> callBack = dataClient.upload_photo(part);
//                    callBack.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
//                            if (response != null){
//                                String message = response.body();
////                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
////                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });

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

        usersDAO = new UsersDAO(getActivity());
        item = new UsersModule();
        imgProfile = root.findViewById(R.id.profile_image);
        tvNameUser = root.findViewById(R.id.tvNameUser);
        tvEmail = root.findViewById(R.id.tvEmail);
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String email = pref.getString("EMAIL", "");
        tvEmail.setText(email);
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imgPhoto;
                EditText edFullName, edPhoneNumber, edAddress;
                Button btnSave;

                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.profile_detail);

                imgPhoto = dialog.findViewById(R.id.imgProfileEdit);
                edFullName = dialog.findViewById(R.id.edEditName);
                edPhoneNumber = dialog.findViewById(R.id.edEditPhoneNumber);
                edAddress = dialog.findViewById(R.id.edEditAddress);

                try {
                    String path = usersDAO.getUriImg(email);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(path));
                    imgPhoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fullName = usersDAO.getNameUser(email);
                String phoneNumber = usersDAO.getPhone(email);
                String address = usersDAO.getAddress(email);
                if (!(fullName.equals("null") && phoneNumber.equals("null") && address.equals("null"))){
                    edFullName.setText(fullName);
                    edPhoneNumber.setText(phoneNumber);
                    edAddress.setText(address);
                }

                btnSave = dialog.findViewById(R.id.btnSave);


                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fullName = edFullName.getText().toString().trim();
                        String phoneNumber = edPhoneNumber.getText().toString().trim();
                        String address = edAddress.getText().toString().trim();
                        item.email = tvEmail.getText().toString();
                        item.name = fullName;
                        item.phoneNumber = phoneNumber;
                        item.address = address;

                        if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng điền đủ thông tin !", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (usersDAO.updateProfile(item) > 0) {
                            Toast.makeText(getContext(), "Update Success !", Toast.LENGTH_SHORT).show();

                        }
                        getSetOtherData(email);
                        dialog.dismiss();
                    }
                });



                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                dialog.getWindow().setAttributes(lp);
                dialog.show();
            }
        });
        getSetOtherData(email);
        linearLayout = root.findViewById(R.id.Logout);
        btnDeals = root.findViewById(R.id.btn_Account_Deals);
        btnSetting = root.findViewById(R.id.btn_Account_setting);
        btnRating = root.findViewById(R.id.btn_Account_rating);

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

        // Setting
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        // Rating
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RateActivity.class));
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

    public void getSetOtherData(String email) {

        usersDAO = new UsersDAO(getContext());
        list = (ArrayList<UsersModule>) usersDAO.getALL();
        if (list.size() == 0) {
            return;
        }
        String name = usersDAO.getNameUser(email);
        if (!name.equals("null") || name.isEmpty()){
            tvNameUser.setText(usersDAO.getNameUser(email));
        }
        try {
            String path = usersDAO.getUriImg(email);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(path));
            imgProfile.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", usersDAO.getNameUser(email));
        // lưu lại
        editor.commit();


    }
//    public void rememberImg(Uri uri) {
//        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        // lưu dữ liệu
//        editor.putString("IMG", uri.toString());
//        // lưu lại
//        editor.commit();
//    }
}
