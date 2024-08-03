package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.KhachHang;

public interface IUpdateNgayDiChiTiet {
    void onSuccess(ChiTietPhieuThue chiTietPhieuThue);
    void onError(Throwable t);
}
