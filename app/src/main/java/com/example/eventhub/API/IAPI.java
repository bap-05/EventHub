package com.example.eventhub.API;

import com.example.eventhub.Model.ForgotPasswordRequest;
import com.example.eventhub.Model.ResetPasswordRequest;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.Model.VerifyOtpRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAPI {

    @GET("sukien/")
    Call<ApiResponse> getSK();

    @GET("sukien/saptoi")
    Call<ApiResponse> SuKienSapToi();

    @POST("taikhoan/")
    Call<ApiResponse> taikhoan(@Body TaiKhoanDN taiKhoanDN);

    @POST("taikhoan/forgot-password")
    Call<ApiMessageResponse> sendOtp(@Body ForgotPasswordRequest request);

    @POST("taikhoan/verify-otp")
    Call<ApiMessageResponse> verifyOtp(@Body VerifyOtpRequest request);

    @POST("taikhoan/reset-password")
    Call<ApiMessageResponse> resetPassword(@Body ResetPasswordRequest request);

    @GET("profile/{userId}/sapthamgia")
    Call<List<SuKien>> getSuKienSapThamGia(@Path("userId") int userId);

    @GET("profile/{userId}/dathamgia")
    Call<List<SuKien>> getSuKienDaThamGia(@Path("userId") int userId);
    @GET("profile/profile/{userId}")
    Call<TaiKhoan> getUserProfile(@Path("userId") int userId);

    // 4. Update avatar (Nếu sau này bạn mở lại tính năng này)
    // @PUT("profile/update-avatar")
    // Call<TaiKhoan> updateAvatar(@Body UpdateAvatarRequest request);
}
