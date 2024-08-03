package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietPhuThu;

import java.util.List;

public interface IChiTietPhuThuByChiTietPhieuThue {
    void onSuccess(List<ChiTietPhuThu> chiTietPhuThus);
    void onError(Throwable t);
}
