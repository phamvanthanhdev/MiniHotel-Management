package com.minihotel.management.utils;

import com.minihotel.management.dto.ChiTietRequest;
import com.minihotel.management.model.HangPhongDat;
import com.minihotel.management.model.PhongChon;
import com.minihotel.management.model.PhongTrong;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utils {
//    public static int idPhieuDat;
    public static int idNhanVien = 1;
    public static List<HangPhongDat> chitietHangPhongsThue = new ArrayList<>();
    public static List<ChiTietRequest> phongChons = new ArrayList<>(); // Chọn phòng để thuê

    public static PhongChon convertPhongTrongToPhongChon(PhongTrong phongTrong, LocalDate ngayDenThue, LocalDate ngayDiThue){
        return new PhongChon(phongTrong.getMaPhong(), phongTrong.getIdHangPhong(),
                phongTrong.getDonGia(), ngayDenThue, ngayDiThue);
    }
    public static void tangSoLuongHangPhong(int idHangPhong){
        for (HangPhongDat chiTiet: chitietHangPhongsThue) {
            if(chiTiet.getIdHangPhong() == idHangPhong){
                chiTiet.setSoLuong(chiTiet.getSoLuong() + 1);
                break;
            }
        }
    }

    public static void giamSoLuongHangPhong(int idHangPhong){
        for (HangPhongDat chiTiet: chitietHangPhongsThue) {
            if(chiTiet.getIdHangPhong() == idHangPhong){
                chiTiet.setSoLuong(chiTiet.getSoLuong() - 1);
                break;
            }
        }
    }

    public static void xoaHangPhong(int idHangPhong){
        for (HangPhongDat chiTiet: chitietHangPhongsThue) {
            if(chiTiet.getIdHangPhong() == idHangPhong){
                chitietHangPhongsThue.remove(chiTiet);
                break;
            }
        }
    }

    public static long getTongTienHangPhongsThue(LocalDate ngayDen, LocalDate ngayDi){
        long tongTien = 0;
        for (HangPhongDat i: chitietHangPhongsThue) {
            tongTien += (long) i.getDonGia() * i.getSoLuong() * Common.calculateBetweenDate(ngayDen, ngayDi);
        }
        return tongTien;
    }

    // =========== PHÒNG ==========
    public static boolean kiemTraPhongDaChon(String maPhong){
        for (ChiTietRequest chiTiet:Utils.phongChons) {
            if(chiTiet.getMaPhong().equals(maPhong)){
                return true;
            }
        }
        return false;
    }

    public static int soLuongPhongThuocHangPhong(int idHangPhong){
        int soLuong = 0;
        for (ChiTietRequest chiTiet:Utils.phongChons) {
            if(chiTiet.getIdHangPhong() == idHangPhong)
                soLuong += 1;
        }
        return soLuong;
    }

    public static void addPhongChon(PhongChon phongChon){
//        if(Utils.phongChons == null) phongChons = new ArrayList<>();
        Utils.phongChons.add(new ChiTietRequest(phongChon.getMaPhong(),
                phongChon.getIdHangPhong(), phongChon.getDonGia(),
                Common.fommatDateRequest(phongChon.getNgayDiThue()),
                Common.fommatDateRequest(phongChon.getNgayDenThue())));
    }

    public static void removePhongChon(PhongChon phongChon){
        for (ChiTietRequest chiTiet: phongChons) {
            if(phongChon.getMaPhong().equals(chiTiet.getMaPhong())){
                phongChons.remove(chiTiet);
            }
        }
    }

    public static long getTongTienPhongsDaChon(LocalDate ngayDen, LocalDate ngayDi){
        long tongTien = 0;
        for (ChiTietRequest phongChon: phongChons) {
            tongTien += (phongChon.getDonGia() * Common.calculateBetweenDate(ngayDen, ngayDi));
        }
        return tongTien;
    }

}
