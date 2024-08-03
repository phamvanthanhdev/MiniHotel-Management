package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.model.ChiTietPhieuThue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallChiTietPhieuThueById {
    public static void getChiTietPhieuThueById(int idChiTietPhieuThue, IChiTietPhieuThueById callback){
        ApiService.apiService.getChiTietPhieuThueById(idChiTietPhieuThue).enqueue(new Callback<ChiTietPhieuThue>() {
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
