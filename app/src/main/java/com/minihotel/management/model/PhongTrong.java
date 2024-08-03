package com.minihotel.management.model;

public class PhongTrong {
    private String maPhong;
    private int idHangPhong;
    private int tang;
    private String trangThai;
    private long donGia;

    public PhongTrong() {
    }

    public PhongTrong(String maPhong, int idHangPhong, int tang, String trangThai, long donGia) {
        this.maPhong = maPhong;
        this.idHangPhong = idHangPhong;
        this.tang = tang;
        this.trangThai = trangThai;
        this.donGia = donGia;
    }

    public int getIdHangPhong() {
        return idHangPhong;
    }

    public void setIdHangPhong(int idHangPhong) {
        this.idHangPhong = idHangPhong;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
