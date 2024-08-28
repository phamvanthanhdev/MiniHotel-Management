package com.minihotel.management.managers.interfaces;


import com.minihotel.management.model.PhieuThue;

import java.util.List;

public interface IPhieuThueByCmnd {
    void onSuccess(List<PhieuThue> responses);
    void onError(Throwable t);
}
