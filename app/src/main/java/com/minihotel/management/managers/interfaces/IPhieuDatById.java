package com.minihotel.management.managers.interfaces;


import com.minihotel.management.model.PhieuDat;

public interface IPhieuDatById {
    void onSuccess(PhieuDat phieuDat);
    void onError(Throwable t);
}
