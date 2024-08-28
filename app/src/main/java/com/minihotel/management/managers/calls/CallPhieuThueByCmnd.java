package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IPhieuThueByCmnd;
import com.minihotel.management.managers.interfaces.IPhieuThueById;
import com.minihotel.management.model.PhieuThue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhieuThueByCmnd {
    public static void getPhieuThueByCmnd(String cmnd, IPhieuThueByCmnd callback){
        ApiService.apiService.getPhieuThueByCmnd(cmnd).enqueue(new Callback<List<PhieuThue>>() {
            @Override
            public void onResponse(Call<List<PhieuThue>> call, Response<List<PhieuThue>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<PhieuThue>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
