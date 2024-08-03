package com.minihotel.management.dto;

public class ChiTietPhuThuRequest {
    private Integer idPhuThu;
    private Integer idChiTietPhieuThue;
    private Integer soLuong;
    private String ngayTao;
    private Long donGia;

    public ChiTietPhuThuRequest() {
    }

    public ChiTietPhuThuRequest(Integer idPhuThu, Integer idChiTietPhieuThue, Integer soLuong, String ngayTao, Long donGia) {
        this.idPhuThu = idPhuThu;
        this.idChiTietPhieuThue = idChiTietPhieuThue;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.donGia = donGia;
    }

    public Integer getIdPhuThu() {
        return idPhuThu;
    }

    public void setIdPhuThu(Integer idPhuThu) {
        this.idPhuThu = idPhuThu;
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
        return "ChiTietSuDungPhuThuRequest{" +
                "idPhuThu=" + idPhuThu +
                ", idChiTietPhieuThue=" + idChiTietPhieuThue +
                ", soLuong=" + soLuong +
                ", ngayTao='" + ngayTao + '\'' +
                ", donGia=" + donGia +
                '}';
    }
}
