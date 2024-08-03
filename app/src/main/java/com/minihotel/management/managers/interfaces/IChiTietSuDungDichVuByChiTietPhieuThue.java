package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietSuDungDichVu;

import java.util.List;

public interface IChiTietSuDungDichVuByChiTietPhieuThue {
    void onSuccess(List<ChiTietSuDungDichVu> chiTietSuDungDichVus);
    void onError(Throwable t);
}
