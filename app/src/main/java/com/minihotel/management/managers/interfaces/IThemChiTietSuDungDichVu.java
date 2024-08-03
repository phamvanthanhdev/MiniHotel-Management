package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.PhongTrong;

import java.util.List;

public interface IThemChiTietSuDungDichVu {
    void onSuccess(List<ChiTietSuDungDichVu> chiTietSuDungDichVus);
    void onError(Throwable t);
}
