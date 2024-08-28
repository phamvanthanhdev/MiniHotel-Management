package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IPhieuDatById;
import com.minihotel.management.managers.interfaces.IPhieuDatKhachHang;
import com.minihotel.management.model.PhieuDat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhieuDatKhachHang {
    public static void getPhieuDatTheoCMND(String cmnd, IPhieuDatKhachHang callback){
        ApiService.apiService.getPhieuDatTheoCMND(cmnd).enqueue(new Callback<List<PhieuDat>>() {
            @Override
            public void onResponse(Call<List<PhieuDat>> call, Response<List<PhieuDat>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<PhieuDat>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
