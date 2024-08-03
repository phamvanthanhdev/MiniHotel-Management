package com.minihotel.management.model;

public class ChiTietPhieuDat {
    private Integer soLuong;
    private String tenHangPhong;
    private Long donGia;

    public ChiTietPhieuDat(Integer soLuong, String tenHangPhong, String hinhAnh, Long donGia) {
        this.soLuong = soLuong;
        this.tenHangPhong = tenHangPhong;
        this.donGia = donGia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenHangPhong() {
        return tenHangPhong;
    }

    public void setTenHangPhong(String tenHangPhong) {
        this.tenHangPhong = tenHangPhong;
    }

    public Long getDonGia() {
        return donGia;
    }

    public void setDonGia(Long donGia) {
        this.donGia = donGia;
    }
}
