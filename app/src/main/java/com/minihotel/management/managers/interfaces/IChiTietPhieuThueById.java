package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietPhieuThue;

public interface IChiTietPhieuThueById {
    void onSuccess(ChiTietPhieuThue chiTietPhieuThue);
    void onError(Throwable t);
}
