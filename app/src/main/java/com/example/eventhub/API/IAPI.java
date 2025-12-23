package com.example.eventhub.API;

import com.example.eventhub.Model.MinhChung;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.Repository.ProfileResponse;
import com.example.eventhub.Model.ThamGiaSuKien;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    @POST("sukien/timsukien")
    Call<ApiResponse> timSuKien(@Body ThamGiaSuKien suKien);
    @PUT("sukien/uploadminhchung/{id}")
    Call<Void> uploadMinhChung(@Path("id")int id, @Body MinhChung minhChung);
    @GET("sukien/admin")
    Call<AdminEventResponse> getAdminEvents();
    @GET("sukien/thamgia/{maSK}")
    Call<AdminParticipantResponse> getParticipants(@Path("maSK") int maSK);
    @PUT("sukien/thamgia/{maSK}/{maTK}")
    Call<Void> updateParticipantStatus(@Path("maSK") int maSK, @Path("maTK") int maTK, @Body ApproveRequest req);
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
}
