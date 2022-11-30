package com.fpoly.foodapp.modules;

public class demo_cart_item {
    public int id;
    public String name;
    public Double cost;
    public int quantities;

    public demo_cart_item(int id, String name, Double cost, int quanti) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantities = quanti;
    }

    public demo_cart_item() {
    }
}
