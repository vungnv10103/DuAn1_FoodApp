package com.fpoly.foodapp.modules;

public class CartTempModule {
    public int id;
    public int idRecommend;
    public String img;
    public int check;
    public int checkSelected;
    public String name;
    public Double cost;
    public Double costNew;
    public int idUser;
    public int quantitiesNew;
    public int quantities;

    public CartTempModule(int id, int idRecommend,String img, int idUser, int check,int checkSelected, String name, Double cost,Double costNew,int quantitiesNew, int quantities) {
        this.id = id;
        this.idRecommend = idRecommend;
        this.img = img;
        this.idUser = idUser;
        this.check = check;
        this.checkSelected = checkSelected;
        this.name = name;
        this.cost = cost;
        this.costNew = costNew;
        this.quantitiesNew = quantitiesNew;
        this.quantities = quantities;
    }


    public CartTempModule() {
    }
}
