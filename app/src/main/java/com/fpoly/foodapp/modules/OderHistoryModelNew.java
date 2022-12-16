package com.fpoly.foodapp.modules;

public class OderHistoryModelNew {
    public int id;
    public int code;
    public int idUser;
    public int checkStatus;
    public String status;
    public String dateTime;
    public String listProduct;
    public double feeTransport;
    public double totalItem;
    public double totalFinal;

    public OderHistoryModelNew() {
    }

    public OderHistoryModelNew(int id, int code, int idUser, int checkStatus, String status, String dateTime, String listProduct, double feeTransport, double totalItem, double totalFinal) {
        this.id = id;
        this.code = code;
        this.idUser = idUser;
        this.checkStatus = checkStatus;
        this.status = status;
        this.dateTime = dateTime;
        this.listProduct = listProduct;
        this.feeTransport = feeTransport;
        this.totalItem = totalItem;
        this.totalFinal = totalFinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getListProduct() {
        return listProduct;
    }

    public void setListProduct(String listProduct) {
        this.listProduct = listProduct;
    }

    public double getFeeTransport() {
        return feeTransport;
    }

    public void setFeeTransport(double feeTransport) {
        this.feeTransport = feeTransport;
    }

    public double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(double totalItem) {
        this.totalItem = totalItem;
    }


    public double getTotalFinal() {
        return totalFinal;
    }

    public OderHistoryModelNew(int code, String status, String dateTime, String listProduct, double feeTransport, double totalItem, double totalFinal) {
        this.code = code;
        this.status = status;
        this.dateTime = dateTime;
        this.listProduct = listProduct;
        this.feeTransport = feeTransport;
        this.totalItem = totalItem;
        this.totalFinal = totalFinal;
    }

    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    }
}