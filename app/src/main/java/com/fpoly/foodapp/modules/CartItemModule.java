package com.fpoly.foodapp.modules;

public class CartItemModule {
    public int id;
    public int idRecommend;
    public int check;
    public String name;
    public Double cost;
    public int idUser;
    public int quantities;

    public CartItemModule(int id, int idRecommend, int idUser, int check, String name, Double cost, int quantities) {
        this.id = id;
        this.idRecommend = idRecommend;
        this.idUser = idUser;
        this.check = check;
        this.name = name;
        this.cost = cost;
        this.quantities = quantities;
    }


    public CartItemModule() {
    }
}
