package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IKhachThueHienTai;
import com.minihotel.management.model.KhachThue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallKhachThueHienTai {
    public static void getKhachThueHienTai(IKhachThueHienTai callback){
        ApiService.apiService.getKhachThueHienTai().enqueue(new Callback<List<KhachThue>>() {
            @Override
            public void onResponse(Call<List<KhachThue>> call, Response<List<KhachThue>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<KhachThue>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
