package com.minihotel.management.managers.interfaces;


import com.minihotel.management.dto.ResultResponse;

public interface IKiemTraPhongThue {
    void onSuccess(ResultResponse resultResponse);
    void onError(Throwable t);
}
