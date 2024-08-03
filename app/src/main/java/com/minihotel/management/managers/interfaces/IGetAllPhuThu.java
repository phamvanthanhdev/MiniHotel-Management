package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.PhuThu;

import java.util.List;

public interface IGetAllPhuThu {
    void onSuccess(List<PhuThu> phuThus);
    void onError(Throwable t);
}
