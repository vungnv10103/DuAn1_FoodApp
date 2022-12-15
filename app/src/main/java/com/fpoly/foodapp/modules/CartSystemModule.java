package com.fpoly.foodapp.modules;

public class CartSystemModule {
    public int id;
    public int idRecommend;
    public String img;
    public int check;
    public String name;
    public Double cost;
    public int idUser;
    public int quantities;

    public CartSystemModule(int id, int idRecommend, String img, int idUser, int check, String name, Double cost, int quantities) {
        this.id = id;
        this.idRecommend = idRecommend;
        this.img = img;
        this.idUser = idUser;
        this.check = check;
        this.name = name;
        this.cost = cost;
        this.quantities = quantities;
    }


    public CartSystemModule() {
    }
}
