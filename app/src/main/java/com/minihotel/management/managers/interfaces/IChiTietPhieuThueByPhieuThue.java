package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.ChiTietPhieuThue;

import java.util.List;

public interface IChiTietPhieuThueByPhieuThue {
    void onSuccess(List<ChiTietPhieuThue> responses);
    void onError(Throwable t);
}
