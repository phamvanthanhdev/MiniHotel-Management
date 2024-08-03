package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.ITimKiemKhacHang;
import com.minihotel.management.model.KhachHang;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallTimKiemKhachHang {
    public static void timKiemKhachHangBySdt(String sdt, ITimKiemKhacHang callback){
        ApiService.apiService.timKiemKhachHangTheoSdt(sdt).enqueue(new Callback<KhachHang>() {
            @Override
            public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<KhachHang> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
