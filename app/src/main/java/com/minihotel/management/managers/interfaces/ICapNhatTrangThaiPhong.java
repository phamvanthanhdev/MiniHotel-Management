package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.model.HangPhongDat;

import java.util.List;

public interface ICapNhatTrangThaiPhong {
    void onSuccess(ResultResponse resultResponse);
    void onError(Throwable t);
}
