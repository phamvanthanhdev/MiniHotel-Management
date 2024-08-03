package com.minihotel.management.model;

public class PhuThu {
    private Integer idPhuThu;
    private String noiDung;
    private Long donGia;

    public PhuThu() {
    }

    public PhuThu(Integer idPhuThu, String noiDung, Long donGia) {
        this.idPhuThu = idPhuThu;
        this.noiDung = noiDung;
        this.donGia = donGia;
    }

    public Integer getIdPhuThu() {
        return idPhuThu;
    }

    public void setIdPhuThu(Integer idPhuThu) {
        this.idPhuThu = idPhuThu;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Long getDonGia() {
        return donGia;
    }

    public void setDonGia(Long donGia) {
        this.donGia = donGia;
    }
}
