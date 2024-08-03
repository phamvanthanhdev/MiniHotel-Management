package com.minihotel.management.model;

public class HangPhongDat {
    private Integer soLuong;
    private Integer idHangPhong;
    private String tenHangPhong;
    private Long donGia;
    private Integer soLuongTrong;

    public HangPhongDat() {
    }

    public HangPhongDat(Integer soLuong, Integer idHangPhong, String tenHangPhong, Long donGia, Integer soLuongTrong) {
        this.soLuong = soLuong;
        this.idHangPhong = idHangPhong;
        this.tenHangPhong = tenHangPhong;
        this.donGia = donGia;
        this.soLuongTrong = soLuongTrong;
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

    public Integer getSoLuongTrong() {
        return soLuongTrong;
    }

    public void setSoLuongTrong(Integer soLuongTrong) {
        this.soLuongTrong = soLuongTrong;
    }

    public Long getDonGia() {
        return donGia;
    }

    public void setDonGia(Long donGia) {
        this.donGia = donGia;
    }

    public Integer getIdHangPhong() {
        return idHangPhong;
    }

    public void setIdHangPhong(Integer idHangPhong) {
        this.idHangPhong = idHangPhong;
    }
}
