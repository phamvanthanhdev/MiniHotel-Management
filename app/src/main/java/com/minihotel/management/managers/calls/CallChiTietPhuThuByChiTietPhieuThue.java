package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IChiTietPhuThuByChiTietPhieuThue;
import com.minihotel.management.model.ChiTietPhuThu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallChiTietPhuThuByChiTietPhieuThue {
    public static void getChiTietPhuThuByChiTietPhieuThue(int idChiTietPhieuThue, IChiTietPhuThuByChiTietPhieuThue callback){
        ApiService.apiService.getChiTietPhuThuByIdChiTietPhieuThue(idChiTietPhieuThue).enqueue(new Callback<List<ChiTietPhuThu>>() {
            @Override
            public void onResponse(Call<List<ChiTietPhuThu>> call, Response<List<ChiTietPhuThu>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<ChiTietPhuThu>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
