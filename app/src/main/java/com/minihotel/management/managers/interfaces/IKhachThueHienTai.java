package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.HoaDonNgay;
import com.minihotel.management.model.KhachThue;

import java.util.List;

public interface IKhachThueHienTai {
    void onSuccess(List<KhachThue> responses);
    void onError(Throwable t);
}
