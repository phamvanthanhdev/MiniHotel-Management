package com.minihotel.management.model;

public class HoaDonNgay {
    private String soHoaDon;
    private Integer idNhanVien;
    private Long tongTien;
    private String ngayTao;
    private Long tongGiaPhong;
    private Long tongDichVu;
    private Long tongPhuThu;
    private Long tienTamUng;

    public HoaDonNgay() {
    }

    public HoaDonNgay(String soHoaDon, Integer idNhanVien, Long tongTien, String ngayTao, Long tongGiaPhong, Long tongDichVu, Long tongPhuThu, Long tienTamUng) {
        this.soHoaDon = soHoaDon;
        this.idNhanVien = idNhanVien;
        this.tongTien = tongTien;
        this.ngayTao = ngayTao;
        this.tongGiaPhong = tongGiaPhong;
        this.tongDichVu = tongDichVu;
        this.tongPhuThu = tongPhuThu;
        this.tienTamUng = tienTamUng;
    }

    public String getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(String soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Long getTongTien() {
        return tongTien;
    }

    public void setTongTien(Long tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Long getTongGiaPhong() {
        return tongGiaPhong;
    }

    public void setTongGiaPhong(Long tongGiaPhong) {
        this.tongGiaPhong = tongGiaPhong;
    }

    public Long getTongDichVu() {
        return tongDichVu;
    }

    public void setTongDichVu(Long tongDichVu) {
        this.tongDichVu = tongDichVu;
    }

    public Long getTongPhuThu() {
        return tongPhuThu;
    }

    public void setTongPhuThu(Long tongPhuThu) {
        this.tongPhuThu = tongPhuThu;
    }

    public Long getTienTamUng() {
        return tienTamUng;
    }

    public void setTienTamUng(Long tienTamUng) {
        this.tienTamUng = tienTamUng;
    }

}