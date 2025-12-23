package com.example.eventhub.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.MinhChung;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.ThamGiaSuKien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuKienRepository {

    private final IAPI iapi;

    public SuKienRepository() {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }

    /* ===================== SỰ KIỆN SẮP DIỄN RA ===================== */
    public void SukienSapDienRa(MutableLiveData<List<SuKien>> liveData,
                                MutableLiveData<String> err) {
        iapi.getSK().enqueue(new Callback<ApiResponse>() {
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

    /* ===================== SỰ KIỆN SẮP TỚI ===================== */
    public void SuKienSapToi(MutableLiveData<List<SuKien>> liveData,
                             MutableLiveData<String> err) {
        iapi.SuKienSapToi().enqueue(new Callback<ApiResponse>() {
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

    /* ===================== SỰ KIỆN SẮP THAM GIA ===================== */
    public void getSuKienSapThamGia(int userId,
                                   MutableLiveData<List<SuKien>> liveData,
                                   MutableLiveData<String> err) {
        iapi.getSuKienSapThamGia(userId).enqueue(new Callback<ApiResponse>() {
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

    /* ===================== SỰ KIỆN ĐÃ THAM GIA ===================== */
    public void getSuKienDaThamGia(int userId,
                                  MutableLiveData<List<SuKien>> liveData,
                                  MutableLiveData<String> err) {
        iapi.getSuKienDaThamGia(userId).enqueue(new Callback<ApiResponse>() {
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

    /* ===================== TÌM / SEARCH SỰ KIỆN ===================== */
    public void searchSuKien(String keyword, String tags, String time,
                             MutableLiveData<List<SuKien>> liveData,
                             MutableLiveData<String> err) {
        iapi.searchSuKien(keyword, tags, time).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body().getSuKienList());
                } else {
                    err.postValue("Khong lay duoc du lieu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "Loi searchSuKien: " + t.getMessage(), t);
                err.postValue(t.getMessage());
            }
        });
    }

    /* ===================== ĐĂNG KÝ SỰ KIỆN ===================== */
    public void dangKySuKien(MutableLiveData<String> err,
                             ThamGiaSuKien thamGiaSuKien) {
        iapi.DkSuKien(thamGiaSuKien).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    err.postValue("Dang ky thanh cong");
                } else {
                    err.postValue("Dang ky that bai");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                err.postValue(t.getMessage());
            }
        });
    }

    /* ===================== HỦY ĐĂNG KÝ SỰ KIỆN (CHỈ CÓ Ở NHÁNH) ===================== */
    public void huySuKien(MutableLiveData<String> err, int maSK, int maTK) {
        iapi.cancelRegistration(maSK, maTK).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    err.postValue("Huy that bai: " + response.code());
                } else {
                    err.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                err.postValue(t.getMessage());
            }
        });
    }

    /* ===================== TÌM SỰ KIỆN ĐÃ THAM GIA ===================== */
    public void timSuKien(MutableLiveData<SuKien> liveData,
                          MutableLiveData<String> err,
                          ThamGiaSuKien thamGiaSuKien) {
        iapi.timSuKien(thamGiaSuKien).enqueue(new Callback<ApiResponse>() {
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
                err.postValue(t.getMessage());
            }
        });
    }

    /* ===================== UPLOAD MINH CHỨNG ===================== */
    public void uploadMinhChung(MutableLiveData<String> tb,
                                int id,
                                MinhChung minhChung) {
        iapi.uploadMinhChung(id, minhChung).enqueue(new Callback<Void>() {
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

    /* ===================== TẤT CẢ SỰ KIỆN (ADMIN – CHỈ CÓ Ở NHÁNH) ===================== */
    public void tatCaSuKien(MutableLiveData<List<SuKien>> liveData,
                            MutableLiveData<String> err) {
        iapi.getAdminEvents().enqueue(new Callback<com.example.eventhub.API.AdminEventResponse>() {
            @Override
            public void onResponse(Call<com.example.eventhub.API.AdminEventResponse> call,
                                   Response<com.example.eventhub.API.AdminEventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SuKien> all = new ArrayList<>();
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
                err.postValue(t.getMessage());
            }
        });
    }
}
