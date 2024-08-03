package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IPhongTrongByIdHangPhong;
import com.minihotel.management.model.PhongTrong;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallPhongTrongByIdHangPhong {
    public static void getPhongTrongByIdHangPhong(int idHangPhong, String ngayDenThue, String ngayDiThue,
                      IPhongTrongByIdHangPhong callback){
        ApiService.apiService.getPhongTrongByIdHangPhong(idHangPhong, ngayDenThue, ngayDiThue).enqueue(new Callback<List<PhongTrong>>() {
            @Override
            public void onResponse(Call<List<PhongTrong>> call, Response<List<PhongTrong>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<PhongTrong>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
