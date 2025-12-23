package com.example.eventhub.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eventhub.API.AdminEventResponse;
import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.SuKien;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEventRepository {
    private final IAPI iapi;

    public AdminEventRepository() {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }

    public void fetchAdminEvents(MutableLiveData<List<SuKien>> upcoming,
                                 MutableLiveData<List<SuKien>> ongoing,
                                 MutableLiveData<List<SuKien>> done,
                                 MutableLiveData<String> err) {
        iapi.getAdminEvents().enqueue(new Callback<AdminEventResponse>() {
            @Override
            public void onResponse(Call<AdminEventResponse> call, Response<AdminEventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    upcoming.postValue(response.body().getUpcoming());
                    ongoing.postValue(response.body().getOngoing());
                    done.postValue(response.body().getDone());
                } else {
                    err.postValue("Lỗi server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AdminEventResponse> call, Throwable t) {
                Log.e("API", "fetchAdminEvents error", t);
                err.postValue(t.getMessage());
            }
        });
    }
}
