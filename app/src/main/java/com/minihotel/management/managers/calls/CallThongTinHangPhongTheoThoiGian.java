package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.managers.interfaces.IThongTinHangPhongTheoThoiGian;
import com.minihotel.management.model.ThongTinHangPhong;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallThongTinHangPhongTheoThoiGian {
    public static void getThongTinHangPhongTheoThoiGian(String ngayDenDat, String ngayDiDat, IThongTinHangPhongTheoThoiGian callback){
        ApiService.apiService.getThongTinHangPhongTheoThoiGian(ngayDenDat, ngayDiDat).enqueue(new Callback<List<ThongTinHangPhong>>() {
            @Override
            public void onResponse(Call<List<ThongTinHangPhong>> call, Response<List<ThongTinHangPhong>> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<List<ThongTinHangPhong>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
