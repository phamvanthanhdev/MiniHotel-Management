package com.minihotel.management.dto;

public class ChiTietSuDungDichVuRequest {
    private Integer idDichVu;
    private Integer idChiTietPhieuThue;
    private Integer soLuong;
    private String ngayTao;
    private Long donGia;

    public ChiTietSuDungDichVuRequest() {
    }

    public ChiTietSuDungDichVuRequest(Integer idDichVu, Integer idChiTietPhieuThue, Integer soLuong, String ngayTao, Long donGia) {
        this.idDichVu = idDichVu;
        this.idChiTietPhieuThue = idChiTietPhieuThue;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.donGia = donGia;
    }

    public Integer getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(Integer idDichVu) {
        this.idDichVu = idDichVu;
    }

    public Integer getIdChiTietPhieuThue() {
        return idChiTietPhieuThue;
    }

    public void setIdChiTietPhieuThue(Integer idChiTietPhieuThue) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
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

    @Override
    public String toString() {
        return "ChiTietSuDungDichVuRequest{" +
                "idDichVu=" + idDichVu +
                ", idChiTietPhieuThue=" + idChiTietPhieuThue +
                ", soLuong=" + soLuong +
                ", ngayTao='" + ngayTao + '\'' +
                ", donGia=" + donGia +
                '}';
    }
}
