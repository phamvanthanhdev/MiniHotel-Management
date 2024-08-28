package com.minihotel.management.managers.interfaces;

import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.model.DoanhThuTheoNgay;

import java.util.List;

public interface IDoiPhong {
    void onSuccess(ResultResponse response);
    void onError(Throwable t);
}
