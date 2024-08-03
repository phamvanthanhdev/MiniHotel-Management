package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.managers.interfaces.IChiTietSuDungDichVuByChiTietPhieuThue;
import com.minihotel.management.managers.interfaces.IThemChiTietSuDungDichVu;
import com.minihotel.management.model.ChiTietSuDungDichVu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallChiTietSuDungDichVuByChiTietPhieuThue {
    public static void getChiTietSuDungDichVuByChiTietPhieuThue(int idChiTietPhieuThue, IChiTietSuDungDichVuByChiTietPhieuThue callback){
        ApiService.apiService.getChiTietSuDungDichVuByIdChiTietPhieuThue(idChiTietPhieuThue).enqueue(new Callback<List<ChiTietSuDungDichVu>>() {
            @Override
            public void onResponse(Call<List<ChiTietSuDungDichVu>> call, Response<List<ChiTietSuDungDichVu>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<ChiTietSuDungDichVu>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
