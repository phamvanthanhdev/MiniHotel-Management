package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.KhachHang;

public interface IKhachHangByIdPhieuDat {
    void onSuccess(KhachHang khachHang);
    void onError(Throwable t);
}
