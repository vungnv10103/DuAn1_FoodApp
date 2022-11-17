package com.fpoly.foodapp.modules;

import java.util.Date;

public class BillModule {
    private int id;
    private int userID;
    private int merchantID;
    private int productID;
    private Date date;

    public BillModule(int id, int userID, int merchantID, int productID, Date date) {
        this.id = id;
        this.userID = userID;
        this.merchantID = merchantID;
        this.productID = productID;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
