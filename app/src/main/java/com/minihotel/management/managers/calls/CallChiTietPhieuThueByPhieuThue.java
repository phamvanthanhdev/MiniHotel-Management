package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueById;
import com.minihotel.management.managers.interfaces.IChiTietPhieuThueByPhieuThue;
import com.minihotel.management.model.ChiTietPhieuThue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallChiTietPhieuThueByPhieuThue {
    public static void getChiTietPhieuThueByPhieuThue(int idPhieuThue, IChiTietPhieuThueByPhieuThue callback){
        ApiService.apiService.getChiTietPhieuThueByIdPhieuThue(idPhieuThue).enqueue(new Callback<List<ChiTietPhieuThue>>() {
            @Override
            public void onResponse(Call<List<ChiTietPhieuThue>> call, Response<List<ChiTietPhieuThue>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<ChiTietPhieuThue>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
