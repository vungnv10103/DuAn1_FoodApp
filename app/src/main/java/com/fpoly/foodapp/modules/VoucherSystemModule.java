package com.fpoly.foodapp.modules;

public class VoucherSystemModule {
    public int id;
    public int img;
    public int discount;
    public String voucherDeadline;

    public VoucherSystemModule(int id, int img, int discount, String voucherDeadline) {
        this.id = id;
        this.img = img;
        this.discount = discount;
        this.voucherDeadline = voucherDeadline;
    }

    public VoucherSystemModule() {
    }

}
