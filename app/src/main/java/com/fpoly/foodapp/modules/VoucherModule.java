package com.fpoly.foodapp.modules;

public class VoucherModule {
    public int id;
    public int img;
    public String voucherTitle;
    public String voucherDeadline;

    public VoucherModule(int id, int img, String voucherTitle, String voucherDeadline) {
        this.id = id;
        this.img = img;
        this.voucherTitle = voucherTitle;
        this.voucherDeadline = voucherDeadline;
    }

    public VoucherModule() {
    }

}
