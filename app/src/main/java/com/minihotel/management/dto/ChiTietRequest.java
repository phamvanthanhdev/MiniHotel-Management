package com.minihotel.management.dto;

public class ChiTietRequest {
    private String maPhong;
    private Integer idHangPhong;
    private Long donGia;
    private String ngayDen;
    private String ngayDi;

    public ChiTietRequest() {
    }

    public ChiTietRequest(String maPhong, Integer idHangPhong, Long donGia, String ngayDen, String ngayDi) {
        this.maPhong = maPhong;
        this.idHangPhong = idHangPhong;
        this.donGia = donGia;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public Integer getIdHangPhong() {
        return idHangPhong;
    }

    public void setIdHangPhong(Integer idHangPhong) {
        this.idHangPhong = idHangPhong;
    }

    public Long getDonGia() {
        return donGia;
    }

    public void setDonGia(Long donGia) {
        this.donGia = donGia;
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

    @Override
    public String toString() {
        return "ChiTietRequest{" +
                "maPhong='" + maPhong + '\'' +
                ", idHangPhong=" + idHangPhong +
                ", donGia=" + donGia +
                ", ngayDen='" + ngayDen + '\'' +
                ", ngayDi='" + ngayDi + '\'' +
                '}';
    }
}
