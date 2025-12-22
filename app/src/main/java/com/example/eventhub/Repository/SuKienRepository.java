package com.example.eventhub.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.SuKien;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuKienRepository {
    private final IAPI iapi;

    public SuKienRepository( ) {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }
    public void SukienSapDienRa(MutableLiveData<List<SuKien>>liveData, MutableLiveData<String>err){
        Call<ApiResponse> call = iapi.getSK();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                liveData.postValue(response.body().getSuKienList());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API",t.getMessage(),t);
                err.postValue(t.getMessage());
            }
        });
    }
    public void SuKienSapToi(MutableLiveData<List<SuKien>>liveData, MutableLiveData<String>err){
        Call<ApiResponse> call = iapi.SuKienSapToi();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                liveData.postValue(response.body().getSuKienList());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API",t.getMessage(),t);
                err.postValue(t.getMessage());
            }
        });
    }
    public  void getSuKienSapThamGia(int userId, MutableLiveData<List<SuKien>>liveData, MutableLiveData<String>err){
        Call<List<SuKien>> call = iapi.getSuKienSapThamGia(userId);
        call.enqueue(new Callback<List<SuKien>>() {
            @Override
            public void onResponse(Call<List<SuKien>> call, Response<List<SuKien>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    liveData.postValue(response.body());
                }else {
                    err.postValue("Không tải được dữ liệu. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SuKien>> call, Throwable t) {
                Log.e("API", "Lỗi getSuKienSapThamGia: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }
    public  void getSuKienDaThamGia(int userId, MutableLiveData<List<SuKien>>liveData, MutableLiveData<String>err){
        Call<List<SuKien>> call = iapi.getSuKienDaThamGia(userId);
        call.enqueue(new Callback<List<SuKien>>() {
            @Override
            public void onResponse(Call<List<SuKien>> call, Response<List<SuKien>> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    liveData.postValue(response.body());
                }else {
                    err.postValue("Không tải được dữ liệu. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SuKien>> call, Throwable t) {
                Log.e("API", "Lỗi getSuKienDaThamGia: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }
    public void searchSuKien(String keyword, String tags, String time, MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err){
        Call<ApiResponse> call = iapi.searchSuKien(keyword, tags, time);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Khong lay duoc du lieu. Ma loi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "Loi searchSuKien: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }
}
