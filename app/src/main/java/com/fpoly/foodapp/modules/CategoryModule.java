package com.fpoly.foodapp.modules;



public class CategoryModule {
    private int image;
    private String name;

    public CategoryModule(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public CategoryModule() {
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

