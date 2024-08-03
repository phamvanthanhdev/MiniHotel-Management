package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.PhongHienTai;

import java.util.List;

public interface IGetAllDichVu {
    void onSuccess(List<DichVu> dichVus);
    void onError(Throwable t);
}
