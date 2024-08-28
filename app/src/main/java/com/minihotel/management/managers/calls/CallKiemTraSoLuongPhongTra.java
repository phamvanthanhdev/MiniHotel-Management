package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IKiemTraSoLuongPhongTra;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallKiemTraSoLuongPhongTra {
    public static void kiemTraSoLuongPhongTra(int idPhieuThue, int soLuong, IKiemTraSoLuongPhongTra callback){
        ApiService.apiService.kiemTraSoLuongPhongTra(idPhieuThue, soLuong).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
