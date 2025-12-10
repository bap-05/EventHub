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
}
