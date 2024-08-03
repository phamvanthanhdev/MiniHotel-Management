package com.minihotel.management.model;

public class TrangThai {
    private Integer idTrangThai;
    private String tenTrangThai;

    public TrangThai() {
    }

    public TrangThai(Integer idTrangThai, String tenTrangThai) {
        this.idTrangThai = idTrangThai;
        this.tenTrangThai = tenTrangThai;
    }

    public Integer getIdTrangThai() {
        return idTrangThai;
    }

    public void setIdTrangThai(Integer idTrangThai) {
        this.idTrangThai = idTrangThai;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }
}
