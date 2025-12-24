package com.example.eventhub.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

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
                        Log.d("avt1",""+resultAccount.getHoTen());
                        liveData.postValue(resultAccount);
                        err.postValue(null);
                    }
                } else {
                    err.postValue("Tài khoản hoặc mật khẩu không đúng!");
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
    public  void updateAvatar(int userId, MultipartBody .Part body, MutableLiveData<TaiKhoan>liveData,MutableLiveData<String> err) {
        Call<ApiResponse> call = iapi.updateAvatar(userId, body);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    liveData.postValue(response.body().getTaiKhoan());
                }else{
                    err.postValue("Lỗi upload: " + response.code());                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                err.postValue("Lỗi kết nối: " + t.getMessage());

            }
        });

    }
    public void diemtichluy(MutableLiveData<Integer>liveData,MutableLiveData<String>err, int ma){
        Call<ApiResponse>call = iapi.diemtichluy(ma);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful())
                {

                    liveData.postValue(response.body().getDiem());
                    err.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                err.postValue(t.getMessage());
            }
        });
    }
//    public void getUserProfile(int userId, MutableLiveData<TaiKhoan> liveData, MutableLiveData<String> err) {
//        // Đổi Call<TaiKhoan> thành Call<ProfileResponse>
//        Call<ProfileResponse> call = iapi.getUserProfile(userId);
//
//        call.enqueue(new Callback<ProfileResponse>() { // Sửa Generic type ở đây
//            @Override
//            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    // Quan trọng: Lấy TaiKhoan từ bên trong ProfileResponse
//                    liveData.postValue(response.body().getProfile());
//                } else {
//                    err.postValue("Không tìm thấy thông tin. Mã lỗi: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProfileResponse> call, Throwable t) {
//                Log.e("API_ERROR", "Lỗi lấy profile: " + t.getMessage());
//                err.postValue("Lỗi kết nối: " + t.getMessage());
//            }
//        });
//
//    }
}
