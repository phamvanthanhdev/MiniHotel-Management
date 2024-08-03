package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.TrangThai;

import java.util.List;

public interface IGetAllTrangThai {
    void onSuccess(List<TrangThai> trangThais);
    void onError(Throwable t);
}
