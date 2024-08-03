package com.minihotel.management.model;

public class ChiTietPhuThu {
    private Integer idChiTietPhieuThue;
    private Integer idPhuThu;
    private String noiDung;
    private Integer soLuong;
    private String ngayTao;
    private Long donGia;
    private Boolean daThanhToan;

    public ChiTietPhuThu() {
    }

    public ChiTietPhuThu(Integer idChiTietPhieuThue, Integer idPhuThu, String noiDung, Integer soLuong, String ngayTao, Long donGia, Boolean daThanhToan) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
        this.idPhuThu = idPhuThu;
        this.noiDung = noiDung;
        this.soLuong = soLuong;
        this.ngayTao = ngayTao;
        this.donGia = donGia;
        this.daThanhToan = daThanhToan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Integer getIdChiTietPhieuThue() {
        return idChiTietPhieuThue;
    }

    public void setIdChiTietPhieuThue(Integer idChiTietPhieuThue) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
    }

    public Integer getIdPhuThu() {
        return idPhuThu;
    }

    public void setIdPhuThu(Integer idPhuThu) {
        this.idPhuThu = idPhuThu;
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
