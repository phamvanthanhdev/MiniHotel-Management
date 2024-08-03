package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.managers.interfaces.ICapNhatTrangThaiPhong;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallCapNhatTrangThaiPhong {
    public static void capNhatTrangThaiPhong(int idTrangThai, String maPhong, ICapNhatTrangThaiPhong callback){
        ApiService.apiService.capNhatTrangThaiPhong(idTrangThai, maPhong).enqueue(new Callback<ResultResponse>() {
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
