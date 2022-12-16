package com.fpoly.foodapp.modules;

public class OderHistoryModel {
    public int id ;

    private int madonhang ;
    private int idUser;
    private int checkStatus;
    private String soluongsanphan;
    private String trangthai;
    private String ngaymua;
    private double tongtiensanpham;
    private double tax;
    private double tvFeeTransport;
    private double tongtien;




    public OderHistoryModel() {
    }

    public OderHistoryModel(int id,int checkStatus, int idUser,int madonhang, String soluongsanphan, String trangthai, String ngaymua, double tongtiensanpham, double tax, double tvFeeTransport, double tongtien) {
        this.id = id;
        this.checkStatus = checkStatus;
        this.idUser = idUser;
        this.madonhang = madonhang;
        this.soluongsanphan = soluongsanphan;
        this.trangthai = trangthai;
        this.ngaymua = ngaymua;
        this.tongtiensanpham = tongtiensanpham;
        this.tax = tax;
        this.tvFeeTransport = tvFeeTransport;
        this.tongtien = tongtien;
    }

    public void setMadonhang(int madonhang) {
        this.madonhang = madonhang;
    }
    public int getMadonhang() {
        return madonhang;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getSoluongsanphan() {
        return soluongsanphan;
    }

    public void setSoluongsanphan(String soluongsanphan) {
        this.soluongsanphan = soluongsanphan;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public double getTongtiensanpham() {
        return tongtiensanpham;
    }

    public void setTongtiensanpham(double tongtiensanpham) {
        this.tongtiensanpham = tongtiensanpham;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTvFeeTransport() {
        return tvFeeTransport;
    }

    public void setTvFeeTransport(double tvFeeTransport) {
        this.tvFeeTransport = tvFeeTransport;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public OderHistoryModel(int madonhang,int checkStatus,int idUser, String soluongsanphan, String trangthai, String ngaymua, double tongtiensanpham, double tax, double tvFeeTransport, double tongtien) {
        this.madonhang = madonhang;
        this.checkStatus = checkStatus;
        this.idUser = idUser;
        this.soluongsanphan = soluongsanphan;
        this.trangthai = trangthai;
        this.ngaymua = ngaymua;
        this.tongtiensanpham = tongtiensanpham;
        this.tax = tax;
        this.tvFeeTransport = tvFeeTransport;
        this.tongtien = tongtien;
    }
}