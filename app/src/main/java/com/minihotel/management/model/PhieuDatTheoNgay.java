package com.minihotel.management.model;


public class PhieuDatTheoNgay {
    private Integer idPhieuDat;
    private String ngayBatDau;
    private String ngayTraPhong;
    private String ghiChu;
    private String ngayTao;
    private Long tienTamUng;
    private Integer idKhachHang;
    private String tenKhachHang;
    private String cmnd;
    private Integer idNhanVien;
    private Boolean trangThaiHuy;

    public PhieuDatTheoNgay() {
    }

    public PhieuDatTheoNgay(Integer idPhieuDat, String ngayBatDau, String ngayTraPhong, String ghiChu, String ngayTao, Long tienTamUng, Integer idKhachHang, String tenKhachHang, String cmnd, Integer idNhanVien, Boolean trangThaiHuy) {
        this.idPhieuDat = idPhieuDat;
        this.ngayBatDau = ngayBatDau;
        this.ngayTraPhong = ngayTraPhong;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
        this.tienTamUng = tienTamUng;
        this.idKhachHang = idKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.cmnd = cmnd;
        this.idNhanVien = idNhanVien;
        this.trangThaiHuy = trangThaiHuy;
    }

    public Integer getIdPhieuDat() {
        return idPhieuDat;
    }

    public void setIdPhieuDat(Integer idPhieuDat) {
        this.idPhieuDat = idPhieuDat;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayTraPhong() {
        return ngayTraPhong;
    }

    public void setNgayTraPhong(String ngayTraPhong) {
        this.ngayTraPhong = ngayTraPhong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Long getTienTamUng() {
        return tienTamUng;
    }

    public void setTienTamUng(Long tienTamUng) {
        this.tienTamUng = tienTamUng;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Boolean getTrangThaiHuy() {
        return trangThaiHuy;
    }

    public void setTrangThaiHuy(Boolean trangThaiHuy) {
        this.trangThaiHuy = trangThaiHuy;
    }

    @Override
    public String toString() {
        return "PhieuDatThoiGian{" +
                "idPhieuDat=" + idPhieuDat +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayTraPhong=" + ngayTraPhong +
                ", ghiChu='" + ghiChu + '\'' +
                ", ngayTao=" + ngayTao +
                ", tienTamUng=" + tienTamUng +
                ", idKhachHang=" + idKhachHang +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", idNhanVien=" + idNhanVien +
                ", trangThaiHuy=" + trangThaiHuy +
                '}';
    }
}
