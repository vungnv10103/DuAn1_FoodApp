package com.fpoly.foodapp.modules;

public class CartItemModule {
    public int id;
    public int idUser;
    public int check;
    public String name;
    public Double cost;
    public int quantities;

    public CartItemModule(int id, int idUser, int check, String name, Double cost, int quanti) {
        this.id = id;
        this.idUser = idUser;
        this.check = check;
        this.name = name;
        this.cost = cost;
        this.quantities = quanti;
    }

    public CartItemModule() {
    }
}
