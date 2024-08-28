package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.HoaDonNgay;
import com.minihotel.management.model.TrangThai;

import java.util.List;

public interface IHoaDonNgayHienTai {
    void onSuccess(List<HoaDonNgay> responses);
    void onError(Throwable t);
}
