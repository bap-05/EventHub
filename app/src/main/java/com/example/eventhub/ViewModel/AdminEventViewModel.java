package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.API.AdminEventResponse;
import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.AdminEventItem;
import com.example.eventhub.Model.SuKien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEventViewModel extends ViewModel {
    private final IAPI iapi = ApiClient.getClient().create(IAPI.class);
    private final MutableLiveData<List<AdminEventItem>> all = new MutableLiveData<>();
    private final MutableLiveData<List<AdminEventItem>> upcoming = new MutableLiveData<>();
    private final MutableLiveData<List<AdminEventItem>> ongoing = new MutableLiveData<>();
    private final MutableLiveData<List<AdminEventItem>> done = new MutableLiveData<>();
    private final MutableLiveData<String> err = new MutableLiveData<>();
    private boolean loading = false;

    public MutableLiveData<List<AdminEventItem>> getAll() { return all; }
    public MutableLiveData<List<AdminEventItem>> getUpcoming() { return upcoming; }
    public MutableLiveData<List<AdminEventItem>> getOngoing() { return ongoing; }
    public MutableLiveData<List<AdminEventItem>> getDone() { return done; }
    public MutableLiveData<String> getErr() { return err; }

    public void load() {
        if (loading) return;
        loading = true;
        iapi.getAdminEvents().enqueue(new Callback<AdminEventResponse>() {
            @Override
            public void onResponse(Call<AdminEventResponse> call, Response<AdminEventResponse> response) {
                loading = false;
                if (response.isSuccessful() && response.body() != null) {
                    List<AdminEventItem> up = mapItems(response.body().getUpcoming(), AdminEventItem.Status.UPCOMING);
                    List<AdminEventItem> on = mapItems(response.body().getOngoing(), AdminEventItem.Status.ONGOING);
                    List<AdminEventItem> dn = mapItems(response.body().getDone(), AdminEventItem.Status.DONE);
                    upcoming.postValue(up);
                    ongoing.postValue(on);
                    done.postValue(dn);
                    List<AdminEventItem> combined = new ArrayList<>();
                    combined.addAll(up);
                    combined.addAll(on);
                    combined.addAll(dn);
                    all.postValue(combined);
                } else {
                    err.postValue("Lỗi server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AdminEventResponse> call, Throwable t) {
                loading = false;
                err.postValue(t.getMessage());
            }
        });
    }

    private List<AdminEventItem> mapItems(List<SuKien> src, AdminEventItem.Status status) {
        List<AdminEventItem> out = new ArrayList<>();
        if (src == null) return out;
        for (SuKien sk : src) {
            String start = formatDate(sk.getThoiGianBatDau());
            String end = formatDate(sk.getThoiGianKetThuc());
            out.add(new AdminEventItem(
                    sk.getTenSK(),
                    status,
                    true,
                    start,
                    end,
                    sk.getMaSK(),
                    sk.getSoLuongDaDangKy(),
                    sk.getSoLuongGioiHan(),
                    sk.getAVT1(),
                    sk.getAVT2(),
                    sk.getAVT3(),
                    sk
            ));
        }
        return out;
    }

    private String formatDate(String s) {
        if (s == null) return "";
        try {
            SimpleDateFormat src = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date d = src.parse(s);
            if (d == null) return s;
            return new SimpleDateFormat("dd/MM", Locale.getDefault()).format(d);
        } catch (ParseException e) {
            return s;
        }
    }
}
