package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.INhanVienDangNhap;
import com.minihotel.management.model.NhanVien;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallNhanVienDangNhap {
    public static void getNhanVienDangNhap(String tenDangNhap, String matKhau, INhanVienDangNhap callback){
        ApiService.apiService.getNhanVienDangNhap(tenDangNhap, matKhau).enqueue(new Callback<NhanVien>() {
            @Override
            public void onResponse(Call<NhanVien> call, Response<NhanVien> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<NhanVien> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
