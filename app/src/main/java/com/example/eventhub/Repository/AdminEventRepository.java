package com.example.eventhub.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.AdminCreateEventRequest;
import com.example.eventhub.Model.AdminUpdateEventRequest;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEventRepository {
    private final IAPI iapi;

    public AdminEventRepository() {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }

    public void createEvent(AdminCreateEventRequest request,
                            MutableLiveData<Boolean> created,
                            MutableLiveData<String> err) {
        if (request == null) {
            err.postValue("Loi du lieu");
            return;
        }
        File poster = request.getPosterFile();
        if (poster == null || !poster.exists()) {
            err.postValue("Loi file poster");
            return;
        }

        RequestBody requestFile = RequestBody.create(poster, MediaType.parse("image/*"));
        MultipartBody.Part posterPart =
                MultipartBody.Part.createFormData("poster", poster.getName(), requestFile);

        RequestBody rbTen = toText(request.getTenSK());
        RequestBody rbMoTa = toText(request.getMoTa());
        RequestBody rbLoai = toText(request.getLoaiSuKien());
        RequestBody rbSoLuong = toText(request.getSoLuongGioiHan());
        RequestBody rbDiem = toText(request.getDiemCong());
        RequestBody rbCoSo = toText(request.getCoSo());
        RequestBody rbDiaDiem = toText(request.getDiaDiem());
        RequestBody rbBD = toText(request.getThoiGianBatDau());
        RequestBody rbKT = toText(request.getThoiGianKetThuc());
        RequestBody rbNguoiDang = toText(request.getNguoiDang());

        iapi.createSuKien(posterPart, rbTen, rbMoTa, rbLoai, rbSoLuong, rbDiem, rbCoSo, rbDiaDiem, rbBD, rbKT, rbNguoiDang)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            created.postValue(true);
                        } else {
                            err.postValue("Loi server: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("API", "createEvent error", t);
                        err.postValue(t.getMessage());
                    }
                });
    }

    private RequestBody toText(String value) {
        return RequestBody.create(value == null ? "" : value, MediaType.parse("text/plain"));
    }

    private void putIfNotEmpty(java.util.Map<String, String> map, String key, String value) {
        if (value != null && !value.isEmpty()) {
            map.put(key, value);
        }
    }

    public void updateEvent(AdminUpdateEventRequest request,
                            MutableLiveData<Boolean> updated,
                            MutableLiveData<String> err) {
        if (request == null) {
            err.postValue("Loi du lieu");
            return;
        }

        // Gửi JSON, không cần multipart (backend chưa parse multipart)
        java.util.Map<String, String> body = new java.util.HashMap<>();
        putIfNotEmpty(body, "TenSK", request.getTenSK());
        putIfNotEmpty(body, "MoTa", request.getMoTa());
        putIfNotEmpty(body, "LoaiSuKien", request.getLoaiSuKien());
        putIfNotEmpty(body, "SoLuongGioiHan", request.getSoLuongGioiHan());
        putIfNotEmpty(body, "DiemCong", request.getDiemCong());
        putIfNotEmpty(body, "CoSo", request.getCoSo());
        putIfNotEmpty(body, "DiaDiem", request.getDiaDiem());
        putIfNotEmpty(body, "ThoiGianBatDau", request.getThoiGianBatDau());
        putIfNotEmpty(body, "ThoiGianKetThuc", request.getThoiGianKetThuc());
        putIfNotEmpty(body, "TrangThai", request.getTrangThai());

        iapi.updateSuKienJson(request.getEventId(), body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    updated.postValue(true);
                } else {
                    err.postValue("Loi server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API", "updateEvent error", t);
                err.postValue(t.getMessage());
            }
        });
    }
}
