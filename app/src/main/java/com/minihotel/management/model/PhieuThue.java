package com.minihotel.management.model;


import java.io.Serializable;

public class PhieuThue implements Serializable {
    private Integer idPhieuThue;
    private String ngayDen;
    private String ngayDi;
    private String ngayTao;
    private String hoTenKhach;
    private String cmnd;
    private Integer idNhanVien;
    private String tenNhanVien;
    private Integer idPhieuDat;
    private Long tienTamUng;

    public PhieuThue() {
    }

    public PhieuThue(Integer idPhieuThue, String ngayDen, String ngayDi,
                     String ngayTao, String hoTenKhach, String cmnd, Integer idNhanVien,
                     String tenNhanVien, Integer idPhieuDat, Long tienTamUng) {
        this.idPhieuThue = idPhieuThue;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.ngayTao = ngayTao;
        this.hoTenKhach = hoTenKhach;
        this.cmnd = cmnd;
        this.idNhanVien = idNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.idPhieuDat = idPhieuDat;
        this.tienTamUng = tienTamUng;
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

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getHoTenKhach() {
        return hoTenKhach;
    }

    public void setHoTenKhach(String hoTenKhach) {
        this.hoTenKhach = hoTenKhach;
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

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public Integer getIdPhieuDat() {
        return idPhieuDat;
    }

    public void setIdPhieuDat(Integer idPhieuDat) {
        this.idPhieuDat = idPhieuDat;
    }

    public Long getTienTamUng() {
        return tienTamUng;
    }

    public void setTienTamUng(Long tienTamUng) {
        this.tienTamUng = tienTamUng;
    }

    @Override
    public String toString() {
        return "PhieuThue{" +
                "idPhieuThue=" + idPhieuThue +
                ", ngayDen=" + ngayDen +
                ", ngayDi=" + ngayDi +
                ", ngayTao=" + ngayTao +
                ", hoTenKhach='" + hoTenKhach + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", idNhanVien=" + idNhanVien +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", idPhieuDat=" + idPhieuDat +
                ", tienTamUng=" + tienTamUng +
                '}';
    }
}
