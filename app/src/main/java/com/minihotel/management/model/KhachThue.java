package com.minihotel.management.model;


public class KhachThue {
    private String maPhong;
    private String hoTen;
    private String sdt;
    private String ngayDen;
    private String ngayDi;

    public KhachThue() {
    }

    public KhachThue(String maPhong, String hoTen, String sdt, String ngayDen, String ngayDi) {
        this.maPhong = maPhong;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgayDen() {
        return ngayDen;
    }

    public void setNgayDen(String ngayDen) {
        this.ngayDen = ngayDen;
    }

    public String getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(String ngayDi) {
        this.ngayDi = ngayDi;
    }
}
