package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.example.eventhub.Repository.SuKienRepository;


import java.util.ArrayList;
import java.util.List;

public class SuKienViewModel extends ViewModel {

    private SuKienRepository suKienRepository = new SuKienRepository();
    private static MutableLiveData<SuKien> sk = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSK = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSKSapToi = new MutableLiveData<>();
    private MutableLiveData<String> err = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>>listSKdienra = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSKSapThamGia = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSKDaThamGia = new MutableLiveData<>();


    public MutableLiveData<List<SuKien>> getListSKdienra() {
        return listSKdienra;
    }

    private List<SuKien> suKienList = new ArrayList<>();

    public MutableLiveData<List<SuKien>> getListSKSapToi() {
        return listSKSapToi;
    }

    public MutableLiveData<List<SuKien>> getListSK() {
        return listSK;
    }

    public MutableLiveData<String> getErr() {
        return err;
    }

    public static MutableLiveData<SuKien> getSk() {
        return sk;
    }

    public void setSk(SuKien sk) {
        this.sk.postValue(sk);
    }
    public MutableLiveData<List<SuKien>> getListSKSapThamGia() {
        return listSKSapThamGia;
    }

    public MutableLiveData<List<SuKien>> getListSKDaThamGia() {
        return listSKDaThamGia;
    }

    public void loadSuKien()
    {
        suKienRepository.SukienSapDienRa(listSK,err);
    }

    public void load_SK_danhchobn()
    {
        suKienRepository.SuKienSapToi(listSKdienra,err);
    }
    public void load_SK_saptoi()
    {
        suKienRepository.SuKienSapToi(listSKSapToi,err);
    }
    public void loadSuKienSapThamGia(int userId){
        suKienRepository.getSuKienSapThamGia(userId,listSKSapThamGia,err);
    }
    public void loadSuKienDaThamGia(int userId){
        suKienRepository.getSuKienDaThamGia(userId,listSKDaThamGia,err);
    }
}
