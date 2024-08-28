package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IPhieuDatTheoNgay;
import com.minihotel.management.managers.interfaces.IPhieuDatTheoThoiGian;
import com.minihotel.management.model.PhieuDatTheoNgay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhieuDatTheoThoiGian {
    public static void getPhieuDatTheoThoiGian(String ngayBatDauTim, String ngayKetThucTim, IPhieuDatTheoThoiGian callback){
        ApiService.apiService.getPhieuDatTheoThoiGian(ngayBatDauTim, ngayKetThucTim).enqueue(new Callback<List<PhieuDatTheoNgay>>() {
            @Override
            public void onResponse(Call<List<PhieuDatTheoNgay>> call, Response<List<PhieuDatTheoNgay>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<PhieuDatTheoNgay>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
