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
                    err.postValue("D??_ li???u tr??? v??? null");
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
                    err.postValue("L??-i t???i d??_ li???u: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "L??-i getSuKienSapThamGia: " + t.getMessage(), t);
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
                    err.postValue("L??-i t???i d??_ li???u: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "L??-i getSuKienDaThamGia: " + t.getMessage(), t);
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

    public void dangKySuKien(MutableLiveData<String>err, ThamGiaSuKien thamGiaSuKien){
        Call<Void> call = iapi.DkSuKien(thamGiaSuKien);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Log.d("DKSuKien", "????ng kA? thA?nh cA'ng");
                    err.postValue("????ng kA? thA?nh cA'ng");
                }
                else{
                    Log.d("DKSuKien", "????ng kA? t???t b???i");
                    err.postValue("????ng kA? t???t b???i");
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
                    err.postValue("B???n ch??a ?`??ng kA? tham gia s??? ki???n nA?y!");
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
                    tb.postValue("??A? g??-i thA?nh cA'ng");
                else
                    tb.postValue("??A? x???y ra l??-i");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                tb.postValue(t.getMessage());
            }
        });
    }
}
