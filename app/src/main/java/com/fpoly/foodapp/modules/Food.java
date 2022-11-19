package com.fpoly.foodapp.modules;

public class Food {
    private int imge ;
    private String title ;
    private double money;
    private int resource_image;

    public Food(int imge, String title, double money, int resource_image) {
        this.imge = imge;
        this.title = title;
        this.money = money;
        this.resource_image = resource_image;
    }

    public int getImge() {
        return imge;
    }

    public void setImge(int imge) {
        this.imge = imge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getResource_image() {
        return resource_image;
    }

    public void setResource_image(int resource_image) {
        this.resource_image = resource_image;
    }

    public Food() {
    }
}

