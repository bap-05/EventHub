package com.example.eventhub.Repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.MinhChung;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.ThamGiaSuKien;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuKienRepository {
    private final IAPI iapi;

    public SuKienRepository( ) {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }

    public void SukienSapDienRa(MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err){
        Call<ApiResponse> call = iapi.getSK();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body() != null) {
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Dữ liệu trả về null");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void SuKienSapToi(MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err){
        Call<ApiResponse> call = iapi.SuKienSapToi();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body() != null) {
                    liveData.postValue(response.body().getSuKienList());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void getSuKienSapThamGia(int userId, MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err){
        Call<ApiResponse> call = iapi.getSuKienSapThamGia(userId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Lỗi tải dữ liệu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "Lỗi getSuKienSapThamGia: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void getSuKienDaThamGia(int userId, MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err){
        Call<ApiResponse> call = iapi.getSuKienDaThamGia(userId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Lỗi tải dữ liệu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "Lỗi getSuKienDaThamGia: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }
    public void dangKySuKien(MutableLiveData<String>err, ThamGiaSuKien thamGiaSuKien){
        Call<Void> call = iapi.DkSuKien(thamGiaSuKien);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Log.d("DKSuKien", "Đăng ký thành công");
                    err.postValue("Đăng ký thành công");
                }
                else{
                    Log.d("DKSuKien", "Đăng ký tất bại");
                    err.postValue("Đăng ký tất bại");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API",t.getMessage(),t);
                err.postValue(t.getMessage());
            }
        });
    }
    public void timSuKien (MutableLiveData<SuKien> liveData, MutableLiveData<String> err, ThamGiaSuKien thamGiaSuKien){
        Call<ApiResponse> call = iapi.timSuKien(thamGiaSuKien);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    liveData.postValue(response.body().getSuKien());
                    err.postValue(null);
                }
                else{
                    err.postValue("Bạn chưa đăng ký tham gia sự kiện này!");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API",t.getMessage(),t);
                err.postValue(t.getMessage());
            }
        });
    }
    public void uploadMinhChung(MutableLiveData<String>tb, int id, MinhChung minhChung){
        Call<Void> call = iapi.uploadMinhChung(id,minhChung);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                    tb.postValue("Đã gửi thành công");
                else
                    tb.postValue("Đã xảy ra lỗi");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                tb.postValue(t.getMessage());
            }
        });
    }
}