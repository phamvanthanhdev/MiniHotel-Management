package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.model.KhachHang;

public interface IThuePhongKhachSan {
    void onSuccess(ResultResponse resultResponse);
    void onError(Throwable t);
}
