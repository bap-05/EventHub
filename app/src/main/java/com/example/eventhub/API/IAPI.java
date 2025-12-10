package com.example.eventhub.API;

import com.example.eventhub.Model.TaiKhoanDN;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IAPI {
    @GET("sukien/")
    Call<ApiResponse> getSK();
    @GET("sukien/saptoi")
    Call<ApiResponse> SuKienSapToi();
    @POST("taikhoan/")
    Call<ApiResponse> taikhoan(@Body TaiKhoanDN taiKhoanDN);
}
