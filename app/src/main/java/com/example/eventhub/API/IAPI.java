package com.example.eventhub.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IAPI {
    @GET("sukien/")
    Call<ApiResponse> getSK();
    @GET("sukien/saptoi")
    Call<ApiResponse> SuKienSapToi();
}
