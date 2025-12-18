package com.example.eventhub.API;

import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.Repository.ProfileResponse;
import com.example.eventhub.Model.ThamGiaSuKien;

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
    @GET("profile/{userId}/sapthamgia")
    Call<ApiResponse> getSuKienSapThamGia(@Path("userId") int userId);
    @GET("profile/{userId}/dathamgia")
    Call<ApiResponse> getSuKienDaThamGia(@Path("userId") int userId);

    @GET("profile/profile/{userId}")
    Call<ProfileResponse> getUserProfile(@Path("userId") int userId);
    @POST("sukien/")
    Call<Void> DkSuKien(@Body ThamGiaSuKien thamGiaSuKien);
    // 4. Update avatar (Nếu sau này bạn mở lại tính năng này)
    // @PUT("profile/update-avatar")
    // Call<TaiKhoan> updateAvatar(@Body UpdateAvatarRequest request);
}