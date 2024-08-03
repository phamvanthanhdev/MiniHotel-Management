package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.HangPhongDat;

import java.util.List;

public interface IChiTiet2sByIdPhieuDat {
    void onSuccess(List<HangPhongDat> chiTietPhieuDat2s);
    void onError(Throwable t);
}
