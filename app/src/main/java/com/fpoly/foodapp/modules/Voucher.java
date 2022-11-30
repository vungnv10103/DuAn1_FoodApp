package com.fpoly.foodapp.modules;

public class Voucher {
    private int img;
    private String voucherTitle;
    private String voucherDeadline;

    public Voucher(int img, String voucherTitle, String voucherDeadline) {
        this.img = img;
        this.voucherTitle = voucherTitle;
        this.voucherDeadline = voucherDeadline;
    }

    public Voucher() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getVoucherTitle() {
        return voucherTitle;
    }

    public void setVoucherTitle(String voucherTitle) {
        this.voucherTitle = voucherTitle;
    }

    public String getVoucherDeadline() {
        return voucherDeadline;
    }

    public void setVoucherDeadline(String voucherDeadline) {
        this.voucherDeadline = voucherDeadline;
    }
}
