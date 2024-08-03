package com.minihotel.management.dto;

import java.util.List;

public class PhieuThuePhongRequest {
    private String ngayDen;
    private String ngayDi;
    private boolean trangThai;
    private String ngayTao;
    private Integer idKhachHang;
    private Integer idNhanVien;
    private Integer idPhieuDat;

    private List<ChiTietRequest> chiTietRequests;

    public PhieuThuePhongRequest() {
    }

    public PhieuThuePhongRequest(String ngayDen, String ngayDi, boolean trangThai, String ngayTao, Integer idKhachHang, Integer idNhanVien, Integer idPhieuDat, List<ChiTietRequest> chiTietRequests) {
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.idKhachHang = idKhachHang;
        this.idNhanVien = idNhanVien;
        this.idPhieuDat = idPhieuDat;
        this.chiTietRequests = chiTietRequests;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Integer getIdPhieuDat() {
        return idPhieuDat;
    }

    public void setIdPhieuDat(Integer idPhieuDat) {
        this.idPhieuDat = idPhieuDat;
    }

    public List<ChiTietRequest> getChiTietRequests() {
        return chiTietRequests;
    }

    public void setChiTietRequests(List<ChiTietRequest> chiTietRequests) {
        this.chiTietRequests = chiTietRequests;
    }

    @Override
    public String toString() {
        return "PhieuThuePhongRequest{" +
                "ngayDen='" + ngayDen + '\'' +
                ", ngayDi='" + ngayDi + '\'' +
                ", trangThai=" + trangThai +
                ", ngayTao='" + ngayTao + '\'' +
                ", idKhachHang=" + idKhachHang +
                ", idNhanVien=" + idNhanVien +
                ", idPhieuDat=" + idPhieuDat +
                ", chiTietRequests=" + chiTietRequests +
                '}';
    }
}
