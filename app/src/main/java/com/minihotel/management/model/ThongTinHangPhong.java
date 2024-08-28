package com.minihotel.management.model;

public class ThongTinHangPhong {
    private Integer idHangPhong;
    private String tenHangPhong;
    private Integer soLuongTrong;
    private Long giaGoc;
    private Long giaKhuyenMai;

    public ThongTinHangPhong() {
    }

    public ThongTinHangPhong(Integer idHangPhong, String tenHangPhong, Integer soLuongTrong, Long giaGoc, Long giaKhuyenMai) {
        this.idHangPhong = idHangPhong;
        this.tenHangPhong = tenHangPhong;
        this.soLuongTrong = soLuongTrong;
        this.giaGoc = giaGoc;
        this.giaKhuyenMai = giaKhuyenMai;
    }

    public Integer getIdHangPhong() {
        return idHangPhong;
    }

    public void setIdHangPhong(Integer idHangPhong) {
        this.idHangPhong = idHangPhong;
    }

    public String getTenHangPhong() {
        return tenHangPhong;
    }

    public void setTenHangPhong(String tenHangPhong) {
        this.tenHangPhong = tenHangPhong;
    }

    public Integer getSoLuongTrong() {
        return soLuongTrong;
    }

    public void setSoLuongTrong(Integer soLuongTrong) {
        this.soLuongTrong = soLuongTrong;
    }

    public Long getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(Long giaGoc) {
        this.giaGoc = giaGoc;
    }

    public Long getGiaKhuyenMai() {
        return giaKhuyenMai;
    }

    public void setGiaKhuyenMai(Long giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }
}
