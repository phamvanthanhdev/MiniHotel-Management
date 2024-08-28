package com.minihotel.management.managers.interfaces;


import com.minihotel.management.model.PhieuDat;

import java.util.List;

public interface IPhieuDatKhachHang {
    void onSuccess(List<PhieuDat> phieuDats);
    void onError(Throwable t);
}
