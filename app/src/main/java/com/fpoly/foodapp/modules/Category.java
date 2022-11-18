package com.fpoly.foodapp.modules;



public class Category {
    private int image;
    private String name;

    public Category(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public Category() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

