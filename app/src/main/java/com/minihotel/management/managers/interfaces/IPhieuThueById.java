package com.minihotel.management.managers.interfaces;


import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.model.PhieuThue;

public interface IPhieuThueById {
    void onSuccess(PhieuThue phieuThue);
    void onError(Throwable t);
}
