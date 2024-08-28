package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.dto.KhachThueRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.interfaces.IThemKhachThueToChiTietPhieuThue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallThemKhachThueToChiTietPhieuThue {
    public static void themKhachThueToChiTietPhieuThue(KhachThueRequest khachThueRequest, IThemKhachThueToChiTietPhieuThue callback){
        ApiService.apiService.addKhachThueToChiTietPhieuThue(khachThueRequest).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
