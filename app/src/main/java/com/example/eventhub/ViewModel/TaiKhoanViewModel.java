package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.Repository.TaiKhoanRespository;

import okhttp3.MultipartBody;

public class TaiKhoanViewModel extends ViewModel {
    private TaiKhoanRespository taiKhoanRespository = new TaiKhoanRespository();
    private static MutableLiveData<TaiKhoan> taikhoan = new MutableLiveData<>();
    private MutableLiveData<String> err = new MutableLiveData<>();
    private static MutableLiveData<Integer> diem = new MutableLiveData<>();
    private static MutableLiveData<String> tbdiem = new MutableLiveData<>();
    public static void setTaikhoan(TaiKhoan tk) {
        taikhoan.postValue(tk);
    }

    public static MutableLiveData<TaiKhoan> getTaikhoan() {
        return taikhoan;
    }

    public static MutableLiveData<String> getTbdiem() {
        return tbdiem;
    }


    public static MutableLiveData<Integer> getDiem() {
        return diem;
    }


    public MutableLiveData<String> getErr() {
        return err;
    }
    public void ktraLogin(TaiKhoanDN taiKhoanDN){
        taiKhoanRespository.KtraLogin(taikhoan,err,taiKhoanDN);
    }
    public void uploadAvatar(int userId, MultipartBody.Part body) {
        taiKhoanRespository.updateAvatar(userId, body, taikhoan, err);
    }
    public void diemtichluy(int ma)
    {
        taiKhoanRespository.diemtichluy(diem,tbdiem,ma);
    }
}
