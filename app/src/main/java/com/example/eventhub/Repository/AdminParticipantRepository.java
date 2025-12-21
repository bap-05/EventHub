package com.example.eventhub.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.eventhub.API.AdminParticipantResponse;
import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.AdminParticipant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminParticipantRepository {
    private final IAPI iapi;

    public AdminParticipantRepository() {
        this.iapi = ApiClient.getClient().create(IAPI.class);
    }

    public void loadParticipants(int maSK, MutableLiveData<List<AdminParticipant>> data, MutableLiveData<String> err) {
        iapi.getParticipants(maSK).enqueue(new Callback<AdminParticipantResponse>() {
            @Override
            public void onResponse(Call<AdminParticipantResponse> call, Response<AdminParticipantResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body().getParticipants());
                } else {
                    err.postValue("Lỗi server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AdminParticipantResponse> call, Throwable t) {
                Log.e("API", "loadParticipants error", t);
                err.postValue(t.getMessage());
            }
        });
    }
}
