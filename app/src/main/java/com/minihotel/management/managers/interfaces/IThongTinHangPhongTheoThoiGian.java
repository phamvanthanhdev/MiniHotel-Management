package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.HoaDonNgay;
import com.minihotel.management.model.ThongTinHangPhong;

import java.util.List;

public interface IThongTinHangPhongTheoThoiGian {
    void onSuccess(List<ThongTinHangPhong> responses);
    void onError(Throwable t);
}
