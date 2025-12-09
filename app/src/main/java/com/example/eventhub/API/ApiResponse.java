package com.example.eventhub.API;

import com.example.eventhub.Model.SuKien;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("getSK")
    private List<SuKien> suKienList;

    public List<SuKien> getSuKienList() {
        return suKienList;
    }
}
