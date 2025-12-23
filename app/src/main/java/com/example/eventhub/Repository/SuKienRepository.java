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
    public SuKienRepository() {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }

    public void SukienSapDienRa(MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err) {
        Call<ApiResponse> call = iapi.getSK();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null) {
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Du lieu tra ve null");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void SuKienSapToi(MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err) {
        Call<ApiResponse> call = iapi.SuKienSapToi();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null) {
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

    public void getSuKienSapThamGia(int userId, MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err) {
        Call<ApiResponse> call = iapi.getSuKienSapThamGia(userId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Loi tai du lieu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "Loi getSuKienSapThamGia: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void getSuKienDaThamGia(int userId, MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err) {
        Call<ApiResponse> call = iapi.getSuKienDaThamGia(userId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Loi tai du lieu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "Loi getSuKienDaThamGia: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void dangKySuKien(MutableLiveData<String> err, ThamGiaSuKien thamGiaSuKien) {
        Call<Void> call = iapi.DkSuKien(thamGiaSuKien);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("DKSuKien", "Dang ky thanh cong");
                    err.postValue("Dang ky thanh cong");
                } else {
                    Log.d("DKSuKien", "Dang ky that bai");
                    err.postValue("Dang ky that bai");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void huySuKien(MutableLiveData<String> err, int maSK, int maTK) {
        iapi.cancelRegistration(maSK, maTK).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    err.postValue(null); // chi thong bao loi, khong toast khi thanh cong
                } else {
                    err.postValue("Huy that bai: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                err.postValue(t.getMessage());
            }
        });
    }

    public void timSuKien(MutableLiveData<SuKien> liveData, MutableLiveData<String> err, ThamGiaSuKien thamGiaSuKien) {
        Call<ApiResponse> call = iapi.timSuKien(thamGiaSuKien);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body().getSuKien());
                    err.postValue(null);
                } else {
                    err.postValue("Ban chua dang ky tham gia su kien nay!");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    public void uploadMinhChung(MutableLiveData<String> tb, int id, MinhChung minhChung) {
        Call<Void> call = iapi.uploadMinhChung(id, minhChung);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    tb.postValue("Da gui thanh cong");
                else
                    tb.postValue("Da xay ra loi");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                tb.postValue(t.getMessage());
            }
        });
    }

    public void tatCaSuKien(MutableLiveData<List<SuKien>> liveData, MutableLiveData<String> err) {
        // Dùng endpoint admin (đã có trên server) để lấy đủ upcoming/ongoing/done
        Call<com.example.eventhub.API.AdminEventResponse> call = iapi.getAdminEvents();
        call.enqueue(new Callback<com.example.eventhub.API.AdminEventResponse>() {
            @Override
            public void onResponse(Call<com.example.eventhub.API.AdminEventResponse> call, Response<com.example.eventhub.API.AdminEventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SuKien> all = new java.util.ArrayList<>();
                    if (response.body().getUpcoming() != null) all.addAll(response.body().getUpcoming());
                    if (response.body().getOngoing() != null) all.addAll(response.body().getOngoing());
                    if (response.body().getDone() != null) all.addAll(response.body().getDone());
                    liveData.postValue(all);
                } else {
                    err.postValue("Loi tai du lieu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<com.example.eventhub.API.AdminEventResponse> call, Throwable t) {
                Log.e("API", "Loi tatCaSuKien: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }
}
