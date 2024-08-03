package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IUpdateNgayDiChiTiet;
import com.minihotel.management.model.ChiTietPhieuThue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallUpdateNgayDiChiTiet {
    public static void updateNgayDiChiTiet(int idChiTiet, String ngayDi, IUpdateNgayDiChiTiet callback){
        ApiService.apiService.updateNgayDiChiTiet(idChiTiet, ngayDi).enqueue(new Callback<ChiTietPhieuThue>() {
            @Override
            public void onResponse(Call<ChiTietPhieuThue> call, Response<ChiTietPhieuThue> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<ChiTietPhieuThue> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
