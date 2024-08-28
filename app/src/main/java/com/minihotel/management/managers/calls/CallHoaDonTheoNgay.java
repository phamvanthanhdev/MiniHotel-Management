package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IHoaDonNgayHienTai;
import com.minihotel.management.managers.interfaces.IHoaDonTheoNgay;
import com.minihotel.management.model.HoaDonNgay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallHoaDonTheoNgay {
    public static void getHoaDonTheoNgay(String ngay, IHoaDonTheoNgay callback){
        ApiService.apiService.getHoaDonTheoNgay(ngay).enqueue(new Callback<List<HoaDonNgay>>() {
            @Override
            public void onResponse(Call<List<HoaDonNgay>> call, Response<List<HoaDonNgay>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<HoaDonNgay>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
