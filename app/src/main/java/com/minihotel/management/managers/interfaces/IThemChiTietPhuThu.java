package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;

import java.util.List;

public interface IThemChiTietPhuThu {
    void onSuccess(List<ChiTietPhuThu> chiTietPhuThus);
    void onError(Throwable t);
}
