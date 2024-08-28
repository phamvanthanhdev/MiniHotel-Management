package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.ResultResponse;

public interface IXoaKhachThueTrongChiTietPhieuThue {
    void onSuccess(ResultResponse response);
    void onError(Throwable t);
}
