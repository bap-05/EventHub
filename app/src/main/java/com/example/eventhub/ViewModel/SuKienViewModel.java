package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.MinhChung;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.ThamGiaSuKien;
import com.example.eventhub.R;
import com.example.eventhub.Repository.SuKienRepository;


import java.util.ArrayList;
import java.util.List;

public class SuKienViewModel extends ViewModel {

    private SuKienRepository suKienRepository = new SuKienRepository();
    private static MutableLiveData<String>dkSuKien = new MutableLiveData<>();
    private static MutableLiveData<SuKien> sk = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSK = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSKSapToi = new MutableLiveData<>();
    private MutableLiveData<String> err = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>>listSKdienra = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSKSapThamGia = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSKDaThamGia = new MutableLiveData<>();
    private MutableLiveData<List<SuKien>> listSKSearch = new MutableLiveData<>();
    private MutableLiveData<SuKien> sukiencantim = new MutableLiveData<>();
    private MutableLiveData<String> thongBaoTimSK = new MutableLiveData<>();
    private MutableLiveData<String> thongBaoUpload = new MutableLiveData<>();

    public static MutableLiveData<String> getDkSuKien() {
        return dkSuKien;
    }

    public MutableLiveData<List<SuKien>> getListSKdienra() {
        return listSKdienra;
    }

    private List<SuKien> suKienList = new ArrayList<>();

    public MutableLiveData<String> getThongBaoUpload() {
        return thongBaoUpload;
    }

    public MutableLiveData<String> getThongBaoTimSK() {
        return thongBaoTimSK;
    }

    public MutableLiveData<SuKien> getSukiencantim() {
        return sukiencantim;
    }

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

    public MutableLiveData<List<SuKien>> getListSKSearch() {
        return listSKSearch;
    }

    public void loadSuKien()
    {
        suKienRepository.SukienSapDienRa(listSK,err);
    }

    public void setThongBaoUpload(String thongBao) {
        this.thongBaoUpload.postValue(thongBao);
    }

    public void setSukiencantim(SuKien sukien) {
        this.sukiencantim.postValue(sukien);
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

    public void searchSuKien(String keyword, String tags, String time){
        suKienRepository.searchSuKien(keyword, tags, time, listSKSearch, err);
    }
    public void dangKySuKien (ThamGiaSuKien thamGiaSuKien)
    {
        suKienRepository.dangKySuKien(dkSuKien,thamGiaSuKien);
    }
    public void timSuKien (ThamGiaSuKien thamGiaSuKien){
        suKienRepository.timSuKien(sukiencantim,thongBaoTimSK,thamGiaSuKien);
    }
    public void uploadMinhChung(int id, MinhChung minhChung)
    {
        suKienRepository.uploadMinhChung(thongBaoUpload,id,minhChung);
    }
}
