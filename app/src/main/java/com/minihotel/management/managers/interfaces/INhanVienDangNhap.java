package com.minihotel.management.managers.interfaces;

import com.minihotel.management.model.NhanVien;

public interface INhanVienDangNhap {
    void onSuccess(NhanVien response);
    void onError(Throwable t);
}
