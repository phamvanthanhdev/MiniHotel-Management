package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.PhongTrong;

import java.util.List;

public interface IPhongTrongByIdHangPhong {
    void onSuccess(List<PhongTrong> phongTrongs);
    void onError(Throwable t);
}
