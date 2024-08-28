package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.HoaDonNgay;

import java.util.List;

public interface IHoaDonTheoNgay {
    void onSuccess(List<HoaDonNgay> responses);
    void onError(Throwable t);
}
