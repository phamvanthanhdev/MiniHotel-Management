package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IPhongsHienTai;
import com.minihotel.management.model.PhongHienTai;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhongsHienTai {
    public static void getPhongsHienTai(IPhongsHienTai callback){
        ApiService.apiService.getAllPhongHienTai().enqueue(new Callback<List<PhongHienTai>>() {
            @Override
            public void onResponse(Call<List<PhongHienTai>> call, Response<List<PhongHienTai>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<PhongHienTai>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
