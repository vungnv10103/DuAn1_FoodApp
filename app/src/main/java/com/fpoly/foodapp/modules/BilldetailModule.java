package com.fpoly.foodapp.modules;

public class BilldetailModule {
    private int id ;
    private int billId;
    private String productID;
    private String dateTime;
    private int quantities;
    private String note;

    public BilldetailModule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BilldetailModule(int id, int billId, String productID, String dateTime, int quantities, String note) {
        this.id = id;
        this.billId = billId;
        this.productID = productID;
        this.dateTime = dateTime;
        this.quantities = quantities;
        this.note = note;
    }
}
