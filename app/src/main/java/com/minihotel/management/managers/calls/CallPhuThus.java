package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IGetAllPhuThu;
import com.minihotel.management.model.PhuThu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhuThus {
    public static void getAllPhuThu(IGetAllPhuThu callback){
        ApiService.apiService.getAllPhuThu().enqueue(new Callback<List<PhuThu>>() {
            @Override
            public void onResponse(Call<List<PhuThu>> call, Response<List<PhuThu>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<PhuThu>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
