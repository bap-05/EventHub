package com.example.eventhub.API;

import com.example.eventhub.Model.ForgotPasswordRequest;
import com.example.eventhub.Model.MinhChung;
import com.example.eventhub.Model.ResetPasswordRequest;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.Model.ThamGiaSuKien;
import com.example.eventhub.Model.VerifyOtpRequest;
import com.example.eventhub.Repository.ProfileResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAPI {

    @GET("sukien/")
    Call<ApiResponse> getSK();

    @GET("sukien/saptoi")
    Call<ApiResponse> SuKienSapToi();

    @GET("sukien/search")
    Call<ApiResponse> searchSuKien(@Query("q") String keyword, @Query("tags") String tags, @Query("time") String time);

    @POST("taikhoan/")
    Call<ApiResponse> taikhoan(@Body TaiKhoanDN taiKhoanDN);

    @POST("taikhoan/forgot-password")
    Call<ApiMessageResponse> sendOtp(@Body ForgotPasswordRequest request);

    @POST("taikhoan/verify-otp")
    Call<ApiMessageResponse> verifyOtp(@Body VerifyOtpRequest request);

    @POST("taikhoan/reset-password")
    Call<ApiMessageResponse> resetPassword(@Body ResetPasswordRequest request);

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
    Call<ApiResponse> updateAvatar(@Path("userId") int userId, @Part MultipartBody.Part avatar);

    @POST("sukien/timsukien")
    Call<ApiResponse> timSuKien(@Body ThamGiaSuKien suKien);

    @PUT("sukien/uploadminhchung/{id}")
    Call<Void> uploadMinhChung(@Path("id")int id, @Body MinhChung minhChung);
    @GET("sukien/admin")
    Call<AdminEventResponse> getAdminEvents();
    @GET("sukien/all")
    Call<ApiResponse> getAllEvents();
    @GET("sukien/thamgia/{maSK}")
    Call<AdminParticipantResponse> getParticipants(@Path("maSK") int maSK);
    @PUT("sukien/thamgia/{maSK}/{maTK}")
    Call<Void> updateParticipantStatus(@Path("maSK") int maSK, @Path("maTK") int maTK, @Body ApproveRequest req);
    @DELETE("sukien/thamgia/{maSK}/{maTK}")
    Call<Void> cancelRegistration(@Path("maSK") int maSK, @Path("maTK") int maTK);
    @Multipart
    @POST("sukien/create")
    Call<ResponseBody> createSuKien(
            @Part MultipartBody.Part poster,
            @Part("TenSK") RequestBody tenSK,
            @Part("MoTa") RequestBody moTa,
            @Part("LoaiSuKien") RequestBody loaiSK,
            @Part("SoLuongGioiHan") RequestBody soLuong,
            @Part("DiemCong") RequestBody diem,
            @Part("CoSo") RequestBody coSo,
            @Part("DiaDiem") RequestBody diaDiem,
            @Part("ThoiGianBatDau") RequestBody batDau,
            @Part("ThoiGianKetThuc") RequestBody ketThuc,
            @Part("NguoiDang") RequestBody nguoiDang
    );

    @Multipart
    @PUT("sukien/update/{id}")
    Call<ResponseBody> updateSuKien(
            @Path("id") int id,
            @Part MultipartBody.Part poster,
            @Part("TenSK") RequestBody tenSK,
            @Part("MoTa") RequestBody moTa,
            @Part("LoaiSuKien") RequestBody loaiSK,
            @Part("SoLuongGioiHan") RequestBody soLuong,
            @Part("DiemCong") RequestBody diem,
            @Part("CoSo") RequestBody coSo,
            @Part("DiaDiem") RequestBody diaDiem,
            @Part("ThoiGianBatDau") RequestBody batDau,
            @Part("ThoiGianKetThuc") RequestBody ketThuc,
            @Part("TrangThai") RequestBody trangThai
    );

    // JSON update (không multipart) dùng cho thay đổi trạng thái/fields đơn giản
    @PUT("sukien/update/{id}")
    Call<ResponseBody> updateSuKienJson(@Path("id") int id, @Body java.util.Map<String, String> body);
    @POST("sukien/sukiendathamgia")
    Call<ApiResponse> sukiendathamgia(@Body ThamGiaSuKien thamGiaSuKien);
    @GET("taikhoan/diemtichluy/{id}")
    Call<ApiResponse> diemtichluy (@Path("id")int id);
}
