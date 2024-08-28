package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.KhachHang;

import java.util.List;

public interface IThemKhachHang {
    void onSuccess(KhachHang khachHang);
    void onError(Throwable t);
}
