package com.minihotel.management.model;

public class DichVu {
    private Integer idDichVu;
    private String tenDichVu;
    private Long donGia;

    public DichVu() {
    }

    public DichVu(Integer idDichVu, String tenDichVu, Long donGia) {
        this.idDichVu = idDichVu;
        this.tenDichVu = tenDichVu;
        this.donGia = donGia;
    }

    public Integer getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(Integer idDichVu) {
        this.idDichVu = idDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public Long getDonGia() {
        return donGia;
    }

    public void setDonGia(Long donGia) {
        this.donGia = donGia;
    }
}
