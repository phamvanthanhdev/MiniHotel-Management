package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.PhongHienTai;

import java.util.List;

public interface IPhongsHienTai {
    void onSuccess(List<PhongHienTai> phongHienTais);
    void onError(Throwable t);
}
