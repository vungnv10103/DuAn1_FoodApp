package com.fpoly.foodapp.modules;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class BilldetailModule {
 private int id ;

 private int madonhang ;
 private String soluongsanphan;
 private String trangthai;
 private String ngaymua;
 private double tongtiensanpham;
 private double tax;
 private double dalivery;
 private double tongtien;

 public int getMadonhang() {
  return madonhang;
 }

 public BilldetailModule() {
 }

 public BilldetailModule(int id, int madonhang, String soluongsanphan, String trangthai, String ngaymua, double tongtiensanpham, double tax, double dalivery, double tongtien) {
  this.id = id;
  this.madonhang = madonhang;
  this.soluongsanphan = soluongsanphan;
  this.trangthai = trangthai;
  this.ngaymua = ngaymua;
  this.tongtiensanpham = tongtiensanpham;
  this.tax = tax;
  this.dalivery = dalivery;
  this.tongtien = tongtien;
 }

 public void setMadonhang(int madonhang) {
  this.madonhang = madonhang;
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

 public double getTax() {
  return tax;
 }

 public void setTax(double tax) {
  this.tax = tax;
 }

 public double getDalivery() {
  return dalivery;
 }

 public void setDalivery(double dalivery) {
  this.dalivery = dalivery;
 }

 public double getTongtien() {
  return tongtien;
 }

 public void setTongtien(double tongtien) {
  this.tongtien = tongtien;
 }

 public BilldetailModule(int madonhang, String soluongsanphan, String trangthai, String ngaymua, double tongtiensanpham, double tax, double dalivery, double tongtien) {
  this.madonhang = madonhang;
  this.soluongsanphan = soluongsanphan;
  this.trangthai = trangthai;
  this.ngaymua = ngaymua;
  this.tongtiensanpham = tongtiensanpham;
  this.tax = tax;
  this.dalivery = dalivery;
  this.tongtien = tongtien;
 }
}
