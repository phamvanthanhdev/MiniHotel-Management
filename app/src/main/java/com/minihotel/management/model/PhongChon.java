package com.minihotel.management.model;

import java.time.LocalDate;

public class PhongChon {
    private String maPhong;
    private Integer idHangPhong;
    private Long donGia;
    private LocalDate ngayDiThue;
    private LocalDate ngayDenThue;
    private boolean daChon;

    public PhongChon() {
    }

    public PhongChon(String maPhong, Integer idHangPhong, Long donGia, LocalDate ngayDiThue, LocalDate ngayDenThue) {
        this.maPhong = maPhong;
        this.idHangPhong = idHangPhong;
        this.donGia = donGia;
        this.ngayDiThue = ngayDiThue;
        this.ngayDenThue = ngayDenThue;
        this.daChon = false;
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

    public boolean isDaChon() {
        return daChon;
    }

    public void setDaChon(boolean daChon) {
        this.daChon = daChon;
    }

    public LocalDate getNgayDiThue() {
        return ngayDiThue;
    }

    public void setNgayDiThue(LocalDate ngayDiThue) {
        this.ngayDiThue = ngayDiThue;
    }

    public LocalDate getNgayDenThue() {
        return ngayDenThue;
    }

    public void setNgayDenThue(LocalDate ngayDenThue) {
        this.ngayDenThue = ngayDenThue;
    }
}
