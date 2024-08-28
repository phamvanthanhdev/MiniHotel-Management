package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.HoaDonResponse;

public interface ITraPhongKhachDoan {
    void onSuccess(HoaDonResponse hoaDonResponse);
    void onError(Throwable t);
}
