package com.example.eventhub.Model;

import java.util.Date;

public class SuKien {
    String TenSK, TrangThai, ThoiGianBatDau,ThoiGianKetThuc, CoSo, DiaDiem, DiemCong, MoTa,Poster;
    String SoLuongGioiHan,SoLuongDaDangKy,NguoiDang,NgayTao,AVT1, AVT2, AVT3, AVT4;
    String MaSK;

    public SuKien( String maSK, String tenSK, String trangThai, String thoiGianBatDau, String thoiGianKetThuc, String coso, String diaDiem, String diemCong, String moTa, String soLuongGioiHan, String soLuongDaDangKy, String nguoiDang, String ngayTao) {
        TenSK = tenSK;
        TrangThai = trangThai;
        ThoiGianBatDau = thoiGianBatDau;
        ThoiGianKetThuc = thoiGianKetThuc;
        CoSo = coso;
        DiaDiem = diaDiem;
        DiemCong = diemCong;
        MoTa = moTa;
        SoLuongGioiHan = soLuongGioiHan;
        SoLuongDaDangKy = soLuongDaDangKy;
        NguoiDang = nguoiDang;
        NgayTao = ngayTao;
        MaSK = maSK;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getTenSK() {
        return TenSK;
    }

    public void setTenSK(String tenSK) {
        TenSK = tenSK;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getThoiGianBatDau() {
        return ThoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        ThoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return ThoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        ThoiGianKetThuc = thoiGianKetThuc;
    }

    public String getCoso() {
        return CoSo;
    }

    public void setCoso(String coso) {
        CoSo = coso;
    }

    public String getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        DiaDiem = diaDiem;
    }

    public String getDiemCong() {
        return DiemCong;
    }

    public void setDiemCong(String diemCong) {
        DiemCong = diemCong;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getSoLuongGioiHan() {
        return SoLuongGioiHan;
    }

    public void setSoLuongGioiHan(String soLuongGioiHan) {
        SoLuongGioiHan = soLuongGioiHan;
    }

    public String getSoLuongDaDangKy() {
        return SoLuongDaDangKy;
    }

    public void setSoLuongDaDangKy(String soLuongDaDangKy) {
        SoLuongDaDangKy = soLuongDaDangKy;
    }

    public String getNguoiDang() {
        return NguoiDang;
    }

    public void setNguoiDang(String nguoiDang) {
        NguoiDang = nguoiDang;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public String getAVT1() {
        return AVT1;
    }

    public void setAVT1(String AVT1) {
        this.AVT1 = AVT1;
    }

    public String getAVT2() {
        return AVT2;
    }

    public void setAVT2(String AVT2) {
        this.AVT2 = AVT2;
    }

    public String getAVT3() {
        return AVT3;
    }

    public void setAVT3(String AVT3) {
        this.AVT3 = AVT3;
    }

    public String getAVT4() {
        return AVT4;
    }

    public void setAVT4(String AVT4) {
        this.AVT4 = AVT4;
    }

    public String getMaSK() {
        return MaSK;
    }

    public void setMaSK(String maSK) {
        MaSK = maSK;
    }
}