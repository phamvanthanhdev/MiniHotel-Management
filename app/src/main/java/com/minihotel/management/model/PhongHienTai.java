package com.minihotel.management.model;

import java.time.LocalDate;

public class PhongHienTai {
    private String maPhong;
    private Integer tang;
    private Integer idHangPhong;
    private String tenHangPhong;
    private Integer soNguoiToiDa;
    private Long giaGoc;
    private Long giaKhuyenMai;
    private String tenTrangThai;
    private Boolean daThue;
    private Integer idChiTietPhieuThue;
    private String ngayDen;
    private String ngayDi;

    public PhongHienTai() {
    }

    public PhongHienTai(String maPhong, Integer tang, Integer idHangPhong, String tenHangPhong, Integer soNguoiToiDa, Long giaGoc, Long giaKhuyenMai, String tenTrangThai,
                        Boolean daThue, Integer idChiTietPhieuThue, String ngayDen, String ngayDi) {
        this.maPhong = maPhong;
        this.tang = tang;
        this.idHangPhong = idHangPhong;
        this.tenHangPhong = tenHangPhong;
        this.soNguoiToiDa = soNguoiToiDa;
        this.giaGoc = giaGoc;
        this.giaKhuyenMai = giaKhuyenMai;
        this.tenTrangThai = tenTrangThai;
        this.daThue = daThue;
        this.idChiTietPhieuThue = idChiTietPhieuThue;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public Integer getTang() {
        return tang;
    }

    public void setTang(Integer tang) {
        this.tang = tang;
    }

    public Integer getIdHangPhong() {
        return idHangPhong;
    }

    public void setIdHangPhong(Integer idHangPhong) {
        this.idHangPhong = idHangPhong;
    }

    public String getTenHangPhong() {
        return tenHangPhong;
    }

    public void setTenHangPhong(String tenHangPhong) {
        this.tenHangPhong = tenHangPhong;
    }

    public Integer getSoNguoiToiDa() {
        return soNguoiToiDa;
    }

    public void setSoNguoiToiDa(Integer soNguoiToiDa) {
        this.soNguoiToiDa = soNguoiToiDa;
    }

    public Long getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(Long giaGoc) {
        this.giaGoc = giaGoc;
    }

    public Long getGiaKhuyenMai() {
        return giaKhuyenMai;
    }

    public void setGiaKhuyenMai(Long giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public Boolean getDaThue() {
        return daThue;
    }

    public void setDaThue(Boolean daThue) {
        this.daThue = daThue;
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

    public Integer getIdChiTietPhieuThue() {
        return idChiTietPhieuThue;
    }

    public void setIdChiTietPhieuThue(Integer idChiTietPhieuThue) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
    }

    @Override
    public String toString() {
        return "PhongHienTai{" +
                "maPhong='" + maPhong + '\'' +
                ", tang=" + tang +
                ", idHangPhong=" + idHangPhong +
                ", tenHangPhong='" + tenHangPhong + '\'' +
                ", soNguoiToiDa=" + soNguoiToiDa +
                ", giaGoc=" + giaGoc +
                ", giaKhuyenMai=" + giaKhuyenMai +
                ", tenTrangThai='" + tenTrangThai + '\'' +
                ", daThue=" + daThue +
                ", idChiTietPhieuThue=" + idChiTietPhieuThue +
                ", ngayDen='" + ngayDen + '\'' +
                ", ngayDi='" + ngayDi + '\'' +
                '}';
    }
}
