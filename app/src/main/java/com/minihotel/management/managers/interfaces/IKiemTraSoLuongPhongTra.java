package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.ResultResponse;

public interface IKiemTraSoLuongPhongTra {
    void onSuccess(Boolean result);
    void onError(Throwable t);
}
