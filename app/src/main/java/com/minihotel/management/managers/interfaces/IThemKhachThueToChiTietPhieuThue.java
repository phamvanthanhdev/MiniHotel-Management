package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.model.HoaDonNgay;

import java.util.List;

public interface IThemKhachThueToChiTietPhieuThue {
    void onSuccess(ResultResponse response);
    void onError(Throwable t);
}
