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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }

    public CartItemModule() {
    }
}
