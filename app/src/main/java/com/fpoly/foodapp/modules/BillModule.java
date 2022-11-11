package com.fpoly.foodapp.modules;

public class BillModule {
    private int billID;
    private String billUserName;
    private String billSalerName;
    private String billProductName;
    private String billDate;

    public BillModule(int billID, String billUserName, String billSalerName, String billProductName, String billDate) {
        this.billID = billID;
        this.billUserName = billUserName;
        this.billSalerName = billSalerName;
        this.billProductName = billProductName;
        this.billDate = billDate;
    }

    public BillModule() {
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getBillUserName() {
        return billUserName;
    }

    public void setBillUserName(String billUserName) {
        this.billUserName = billUserName;
    }

    public String getBillSalerName() {
        return billSalerName;
    }

    public void setBillSalerName(String billSalerName) {
        this.billSalerName = billSalerName;
    }

    public String getBillProductName() {
        return billProductName;
    }

    public void setBillProductName(String billProductName) {
        this.billProductName = billProductName;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
