package com.minihotel.management.model;

import java.time.LocalDate;

public class ChiTietPhieuThue {
    private Integer idChiTietPhieuThue;
    private String maPhong;
    private Integer idHangPhong;
    private String tenHangPhong;
    private Integer idPhieuThue;
    private String ngayDen;
    private String ngayDi;
    private Long donGia;
    private Boolean daThanhToan;


    public ChiTietPhieuThue() {
    }

    public ChiTietPhieuThue(Integer idChiTietPhieuThue, String maPhong, Integer idHangPhong, String tenHangPhong,
                            Integer idPhieuThue, String ngayDen, String ngayDi, Long donGia, Boolean daThanhToan) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
        this.maPhong = maPhong;
        this.idHangPhong = idHangPhong;
        this.tenHangPhong = tenHangPhong;
        this.idPhieuThue = idPhieuThue;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.donGia = donGia;
        this.daThanhToan = daThanhToan;
    }

    public String getTenHangPhong() {
        return tenHangPhong;
    }

    public void setTenHangPhong(String tenHangPhong) {
        this.tenHangPhong = tenHangPhong;
    }

    public Integer getIdChiTietPhieuThue() {
        return idChiTietPhieuThue;
    }

    public void setIdChiTietPhieuThue(Integer idChiTietPhieuThue) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public Integer getIdPhieuThue() {
        return idPhieuThue;
    }

    public void setIdPhieuThue(Integer idPhieuThue) {
        this.idPhieuThue = idPhieuThue;
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

    public Long getDonGia() {
        return donGia;
    }

    public void setDonGia(Long donGia) {
        this.donGia = donGia;
    }

    public Boolean getDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(Boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    public Integer getIdHangPhong() {
        return idHangPhong;
    }

    public void setIdHangPhong(Integer idHangPhong) {
        this.idHangPhong = idHangPhong;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuThue{" +
                "idChiTietPhieuThue=" + idChiTietPhieuThue +
                ", maPhong='" + maPhong + '\'' +
                ", tenHangPhong='" + tenHangPhong + '\'' +
                ", idPhieuThue=" + idPhieuThue +
                ", ngayDen='" + ngayDen + '\'' +
                ", ngayDi='" + ngayDi + '\'' +
                ", donGia=" + donGia +
                ", daThanhToan=" + daThanhToan +
                '}';
    }
}
