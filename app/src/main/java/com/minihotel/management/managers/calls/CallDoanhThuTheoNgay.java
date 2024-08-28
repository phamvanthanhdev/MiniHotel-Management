package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IDoanhThuTheoNgay;
import com.minihotel.management.model.DoanhThuTheoNgay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallDoanhThuTheoNgay {
    public static void getDoanhThuTheoNgay(String ngayBatDau, String ngayKetThuc, IDoanhThuTheoNgay callback){
        ApiService.apiService.getDoanhThuTheoNgay(ngayBatDau, ngayKetThuc).enqueue(new Callback<List<DoanhThuTheoNgay>>() {
            @Override
            public void onResponse(Call<List<DoanhThuTheoNgay>> call, Response<List<DoanhThuTheoNgay>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<DoanhThuTheoNgay>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
