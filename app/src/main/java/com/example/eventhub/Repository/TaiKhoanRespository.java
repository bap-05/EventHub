package com.example.eventhub.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaiKhoanRespository {
    private final IAPI iapi;

    public TaiKhoanRespository() {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }
    public void KtraLogin(MutableLiveData<TaiKhoan> liveData, MutableLiveData<String>err, TaiKhoanDN tk){
        Call<ApiResponse> call = iapi.taikhoan(tk);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TaiKhoan resultAccount = response.body().getTaiKhoan();
                    if (resultAccount != null) {
                        liveData.postValue(resultAccount);
                        err.postValue("");
                    } else {
                        err.postValue("Tài khoản hoặc mật khẩu không đúng!");
                        liveData.postValue(null);
                    }
                } else {
                    err.postValue("Lỗi Server: " + response.code());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage(), t);
                err.postValue("Lỗi kết nối: " + t.getMessage());
                liveData.postValue(null);
            }

        });
    }
}
