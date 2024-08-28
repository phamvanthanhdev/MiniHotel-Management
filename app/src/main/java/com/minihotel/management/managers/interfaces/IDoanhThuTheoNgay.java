package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.DoanhThuTheoNgay;

import java.util.List;

public interface IDoanhThuTheoNgay {
    void onSuccess(List<DoanhThuTheoNgay> doanhThuTheoNgays);
    void onError(Throwable t);
}
