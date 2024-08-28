package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.PhieuDatTheoNgay;

import java.util.List;

public interface IPhieuDatTheoThoiGian {
    void onSuccess(List<PhieuDatTheoNgay> phieuDatTheoNgays);
    void onError(Throwable t);
}
