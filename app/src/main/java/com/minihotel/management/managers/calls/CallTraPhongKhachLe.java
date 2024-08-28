package com.minihotel.management.managers.calls;

import com.minihotel.management.api.ApiService;
import com.minihotel.management.dto.HoaDonResponse;
import com.minihotel.management.managers.interfaces.ITraPhongKhachLe;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallTraPhongKhachLe {
    public static void traPhongKhachLe(Integer idNhanVien, Long tongTien,
                                       String ngayTao, Integer idChiTietPhieuThue,
                                       ITraPhongKhachLe callback){
        ApiService.apiService.traPhongKhachLe(idNhanVien, tongTien, ngayTao, idChiTietPhieuThue).enqueue(new Callback<HoaDonResponse>() {
            @Override
            public void onResponse(Call<HoaDonResponse> call, Response<HoaDonResponse> response) {
                if(response.isSuccessful())
                    callback.onSuccess(response.body());
                else
                    callback.onError(new Throwable("Error code: " + response.code()));
            }

            @Override
            public void onFailure(Call<HoaDonResponse> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
