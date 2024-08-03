package com.minihotel.management.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PhieuDat implements Serializable {
    private Integer idPhieuDat;
    private String ngayBatDau;
    private String ngayTraPhong;
    private String ghiChu;
    private String ngayTao;
    private int tienTamUng;
    private Integer idNhanVien;
    private Boolean trangThaiHuy;

    public PhieuDat(Integer idPhieuDat, String ngayBatDau, String ngayTraPhong, String ghiChu,
                    String ngayTao, int tienTamUng, Integer idNhanVien, Boolean trangThaiHuy) {
        this.idPhieuDat = idPhieuDat;
        this.ngayBatDau = ngayBatDau;
        this.ngayTraPhong = ngayTraPhong;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
        this.tienTamUng = tienTamUng;
        this.idNhanVien = idNhanVien;
        this.trangThaiHuy = trangThaiHuy;
    }

    public Boolean getTrangThaiHuy() {
        return trangThaiHuy;
    }

    public void setTrangThaiHuy(Boolean trangThaiHuy) {
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

    public int getTienTamUng() {
        return tienTamUng;
    }

    public void setTienTamUng(int tienTamUng) {
        this.tienTamUng = tienTamUng;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }
}
