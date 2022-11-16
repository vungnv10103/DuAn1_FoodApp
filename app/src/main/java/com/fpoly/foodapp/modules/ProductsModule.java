package com.fpoly.foodapp.modules;

public class ProductsModule {
    private int id;
    private String typeID;
    private String name;
    private int prices;
    private int quantities;
    private int time;
    private int rate;

    public ProductsModule(int id, String typeID, String name, int prices, int quantities, int time, int rate) {
        this.id = id;
        this.typeID = typeID;
        this.name = name;
        this.prices = prices;
        this.quantities = quantities;
        this.time = time;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrices() {
        return prices;
    }

    public void setPrices(int prices) {
        this.prices = prices;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}