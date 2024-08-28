package com.minihotel.management.dto;

import java.time.LocalDate;

public class HoaDonResponse {
    private String soHoaDon;
    private String tenNhanVien;
    private Long tongTien;
    private String ngayTao;

    public HoaDonResponse() {
    }

    public HoaDonResponse(String soHoaDon, String tenNhanVien, Long tongTien, String ngayTao) {
        this.soHoaDon = soHoaDon;
        this.tenNhanVien = tenNhanVien;
        this.tongTien = tongTien;
        this.ngayTao = ngayTao;
    }

    public String getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(String soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public Long getTongTien() {
        return tongTien;
    }

    public void setTongTien(Long tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
