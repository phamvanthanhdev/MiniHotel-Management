package com.minihotel.management.model;


public class DoanhThuTheoNgay {
    private String ngayTao;
    private Long tongTien;

    public DoanhThuTheoNgay() {
    }

    public DoanhThuTheoNgay(String ngayTao, Long tongTien) {
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Long getTongTien() {
        return tongTien;
    }

    public void setTongTien(Long tongTien) {
        this.tongTien = tongTien;
    }
}
