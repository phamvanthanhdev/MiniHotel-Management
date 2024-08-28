package com.minihotel.management.dto;

import java.util.List;

public class KhachThueRequest {
    private Integer idChiTietPhieuThue;
    private List<Integer> idKhachThues;

    public KhachThueRequest() {
    }

    public KhachThueRequest(Integer idChiTietPhieuThue, List<Integer> idKhachThues) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
        this.idKhachThues = idKhachThues;
    }

    public Integer getIdChiTietPhieuThue() {
        return idChiTietPhieuThue;
    }

    public void setIdChiTietPhieuThue(Integer idChiTietPhieuThue) {
        this.idChiTietPhieuThue = idChiTietPhieuThue;
    }

    public List<Integer> getIdKhachThues() {
        return idKhachThues;
    }

    public void setIdKhachThues(List<Integer> idKhachThues) {
        this.idKhachThues = idKhachThues;
    }
}
