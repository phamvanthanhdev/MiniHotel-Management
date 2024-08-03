package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IPhieuThueByCmnd;
import com.minihotel.management.managers.interfaces.IPhieuThueById;
import com.minihotel.management.model.PhieuThue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhieuThueByCmnd {
    public static void getPhieuThueByCmnd(String cmnd, IPhieuThueByCmnd callback){
        ApiService.apiService.getPhieuThueByCmnd(cmnd).enqueue(new Callback<PhieuThue>() {
            @Override
            public void onResponse(Call<PhieuThue> call, Response<PhieuThue> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<PhieuThue> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
