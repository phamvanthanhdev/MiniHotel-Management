package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.HoaDonResponse;
import com.minihotel.management.model.KhachHang;

public interface ITraPhongKhachLe {
    void onSuccess(HoaDonResponse hoaDonResponse);
    void onError(Throwable t);
}
