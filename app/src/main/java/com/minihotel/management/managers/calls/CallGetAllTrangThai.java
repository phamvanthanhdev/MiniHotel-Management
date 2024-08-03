package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IGetAllTrangThai;
import com.minihotel.management.model.TrangThai;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallGetAllTrangThai {
    public static void getAllTrangThai(IGetAllTrangThai callback){
        ApiService.apiService.getAllTrangThai().enqueue(new Callback<List<TrangThai>>() {
            @Override
            public void onResponse(Call<List<TrangThai>> call, Response<List<TrangThai>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<TrangThai>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
