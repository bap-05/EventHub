package com.example.eventhub.Repository;

import com.example.eventhub.Model.TaiKhoan;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("profile") // Phải trùng y hệt key trong JSON
    private TaiKhoan profile;

    public TaiKhoan getProfile() {
        return profile;
    }
}