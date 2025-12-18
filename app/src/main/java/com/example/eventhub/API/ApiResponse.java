package com.example.eventhub.API;

import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.TaiKhoan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName(value = "getSK", alternate = {"suKien"})
    private List<SuKien> suKienList;
    @SerializedName("taikhoan")
    private TaiKhoan taiKhoan;

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public List<SuKien> getSuKienList() {
        return suKienList;
    }
}
