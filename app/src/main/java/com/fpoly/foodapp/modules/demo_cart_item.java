package com.fpoly.foodapp.modules;

public class demo_cart_item {
    public int id;
    public int idUser;
    public int check;
    public String name;
    public Double cost;
    public int quantities;

    public demo_cart_item(int id, int idUser, int check, String name, Double cost, int quanti) {
        this.id = id;
        this.idUser = idUser;
        this.check = check;
        this.name = name;
        this.cost = cost;
        this.quantities = quanti;
    }

    public demo_cart_item() {
    }
}
