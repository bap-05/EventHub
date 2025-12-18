package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.Repository.TaiKhoanRespository;

public class TaiKhoanViewModel extends ViewModel {
    private TaiKhoanRespository taiKhoanRespository = new TaiKhoanRespository();
    private static MutableLiveData<TaiKhoan> taikhoan = new MutableLiveData<>();
    private MutableLiveData<String> err = new MutableLiveData<>();

    public static void setTaikhoan(TaiKhoan tk) {
        taikhoan.postValue(tk);
    }

    public static MutableLiveData<TaiKhoan> getTaikhoan() {
        return taikhoan;
    }

    public MutableLiveData<String> getErr() {
        return err;
    }
    public void ktraLogin(TaiKhoanDN taiKhoanDN){
        taiKhoanRespository.KtraLogin(taikhoan,err,taiKhoanDN);
    }
    public void loadUserProfile(int userId){
        taiKhoanRespository.getUserProfile(userId,taikhoan,err);
    }
}
