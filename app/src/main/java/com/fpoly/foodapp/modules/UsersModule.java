package com.fpoly.foodapp.modules;

import android.graphics.Bitmap;

public class UsersModule {

    public int id;
    public String bitmap;
    public String name;
    public String email;
    public String pass;
    public String phoneNumber;
    public String address;
    public String feedback;


    public UsersModule(int id, String bitmap, String name, String email, String pass, String phoneNumber, String address) {
        this.id = id;
        this.bitmap = bitmap;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public UsersModule() {
    }


}