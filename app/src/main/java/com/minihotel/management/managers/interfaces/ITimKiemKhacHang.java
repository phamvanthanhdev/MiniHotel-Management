package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.KhachHang;
import com.minihotel.management.model.PhongTrong;

import java.util.List;

public interface ITimKiemKhacHang {
    void onSuccess(KhachHang khachHang);
    void onError(Throwable t);
}
