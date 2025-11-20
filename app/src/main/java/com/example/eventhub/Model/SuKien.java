package com.example.eventhub.Model;

import java.util.Date;

public class SuKien {
    String TenSK, Poster,TrangThai, ThoiGian;
    int AVT1, AVT2, AVT3, AVT4;

    public SuKien(String tenSK, String poster, String trangThai, int AVT1, int AVT2, int AVT3, int AVT4, String thoiGian) {
        TenSK = tenSK;
        Poster = poster;
        TrangThai = trangThai;
        this.AVT1 = AVT1;
        this.AVT2 = AVT2;
        this.AVT3 = AVT3;
        this.AVT4 = AVT4;
        ThoiGian = thoiGian;

    }

    public String getTenSK() {
        return TenSK;
    }

    public void setTenSK(String tenSK) {
        TenSK = tenSK;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public int getAVT1() {
        return AVT1;
    }

    public void setAVT1(int AVT1) {
        this.AVT1 = AVT1;
    }

    public int getAVT2() {
        return AVT2;
    }

    public void setAVT2(int AVT2) {
        this.AVT2 = AVT2;
    }

    public int getAVT3() {
        return AVT3;
    }

    public void setAVT3(int AVT3) {
        this.AVT3 = AVT3;
    }

    public int getAVT4() {
        return AVT4;
    }

    public void setAVT4(int AVT4) {
        this.AVT4 = AVT4;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }


}
