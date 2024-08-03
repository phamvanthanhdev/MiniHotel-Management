package com.minihotel.management.managers.interfaces;


import com.minihotel.management.model.PhieuThue;

public interface IPhieuThueByCmnd {
    void onSuccess(PhieuThue phieuThue);
    void onError(Throwable t);
}
