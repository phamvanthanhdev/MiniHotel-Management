package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IGetAllDichVu;
import com.minihotel.management.model.DichVu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallDichVus {
    public static void getAllDichVu(IGetAllDichVu callback){
        ApiService.apiService.getAllDichVu().enqueue(new Callback<List<DichVu>>() {
            @Override
            public void onResponse(Call<List<DichVu>> call, Response<List<DichVu>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<DichVu>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
