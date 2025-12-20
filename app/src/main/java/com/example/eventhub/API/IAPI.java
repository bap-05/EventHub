package com.example.eventhub.API;

import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.Repository.ProfileResponse;
import com.example.eventhub.Model.ThamGiaSuKien;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    @Multipart
    @PUT("profile/update-avatar/{userId}")
    Call<ApiResponse> updateAvatar(@Path("userId") int userId,@Part MultipartBody.Part avatar);
}