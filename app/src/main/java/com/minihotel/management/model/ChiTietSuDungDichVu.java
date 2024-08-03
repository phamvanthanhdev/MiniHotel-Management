package com.minihotel.management.model;

public class ChiTietSuDungDichVu {
    private Integer idChiTietPhieuThue;
    private Integer idDichVu;
    private String tenDichVu;
    private Integer soLuong;
    private String ngayTao;
    private Long donGia;
    private Boolean daThanhToan;

    public ChiTietSuDungDichVu() {
    }

    public ChiTietSuDungDichVu(Integer idChiTietPhieuThue, Integer idDichVu, String tenDichVu, Integer soLuong, String ngayTao, Long donGia, Boolean daThanhToan) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
        this.idDichVu = idDichVu;
        this.tenDichVu = tenDichVu;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.donGia = donGia;
        this.daThanhToan = daThanhToan;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public Integer getIdChiTietPhieuThue() {
        return idChiTietPhieuThue;
    }

    public void setIdChiTietPhieuThue(Integer idChiTietPhieuThue) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
    }

    public Integer getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(Integer idDichVu) {
        this.idDichVu = idDichVu;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
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
}
