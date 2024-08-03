package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IChiTiet2sByIdPhieuDat;
import com.minihotel.management.model.HangPhongDat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallChiTiet2sByIdPhieuDat {
    public static void getChiTietsByIdPhieuDat(int idPhieuDat, IChiTiet2sByIdPhieuDat callback){
        ApiService.apiService.getChiTiet2sByIdPhieuDat(idPhieuDat).enqueue(new Callback<List<HangPhongDat>>() {
            @Override
            public void onResponse(Call<List<HangPhongDat>> call, Response<List<HangPhongDat>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<HangPhongDat>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
