package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.model.PhieuDat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhieuDatById {
    public static void getPhieuDatById(int id, IPhieuDatById callback){
        ApiService.apiService.getPhieuDatById(id).enqueue(new Callback<PhieuDat>() {
            @Override
            public void onResponse(Call<PhieuDat> call, Response<PhieuDat> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<PhieuDat> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
