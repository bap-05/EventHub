package com.example.eventhub.Model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SuKien implements Serializable {

    @SerializedName("MaSK")
    private int MaSK;

    @SerializedName("TenSK")
    private String TenSK;

    @SerializedName("Poster")
    private String Poster;

    @SerializedName("TrangThai")
    private String TrangThai;

    @SerializedName("ThoiGianBatDau")
    private String ThoiGianBatDau;

    @SerializedName("ThoiGianKetThuc")
    private String ThoiGianKetThuc;

    @SerializedName("CoSo")
    private String CoSo;

    @SerializedName("DiaDiem")
    private String DiaDiem;

    @SerializedName("DiemCong")
    private int DiemCong;

    @SerializedName("MoTa")
    private String MoTa;

    @SerializedName("SoLuongGioiHan")
    private int SoLuongGioiHan;

    @SerializedName("SoLuongDaDangKy")
    private int SoLuongDaDangKy;

    @SerializedName("NguoiDang")
    private int NguoiDang;

    @SerializedName("NgayTao")
    private String NgayTao;

    @SerializedName("AVT1") private String AVT1;
    @SerializedName("AVT2") private String AVT2;
    @SerializedName("AVT3") private String AVT3;
    @SerializedName("AVT4") private String AVT4;
    @SerializedName("LoaiSuKien")
    private String LoaiSuKien;

    public SuKien() {
    }


    public int getMaSK() { return MaSK; }
    public void setMaSK(int maSK) { MaSK = maSK; }

    public String getTenSK() { return TenSK; }
    public void setTenSK(String tenSK) { TenSK = tenSK; }

    public String getPoster() { return Poster; }
    public void setPoster(String poster) { Poster = poster; }

    public String getTrangThai() { return TrangThai; }
    public void setTrangThai(String trangThai) { TrangThai = trangThai; }

    public String getThoiGianBatDau() { return ThoiGianBatDau; }
    public void setThoiGianBatDau(String thoiGianBatDau) { ThoiGianBatDau = thoiGianBatDau; }

    public String getThoiGianKetThuc() { return ThoiGianKetThuc; }
    public void setThoiGianKetThuc(String thoiGianKetThuc) { ThoiGianKetThuc = thoiGianKetThuc; }

    public String getCoSo() { return CoSo; }
    public void setCoSo(String coSo) { CoSo = coSo; }

    public String getDiaDiem() { return DiaDiem; }
    public void setDiaDiem(String diaDiem) { DiaDiem = diaDiem; }

    public int getDiemCong() { return DiemCong; }
    public void setDiemCong(int diemCong) { DiemCong = diemCong; }

    public String getMoTa() { return MoTa; }
    public void setMoTa(String moTa) { MoTa = moTa; }

    public int getSoLuongGioiHan() { return SoLuongGioiHan; }
    public void setSoLuongGioiHan(int soLuongGioiHan) { SoLuongGioiHan = soLuongGioiHan; }

    public int getSoLuongDaDangKy() { return SoLuongDaDangKy; }
    public void setSoLuongDaDangKy(int soLuongDaDangKy) { SoLuongDaDangKy = soLuongDaDangKy; }

    public int getNguoiDang() { return NguoiDang; }
    public void setNguoiDang(int nguoiDang) { NguoiDang = nguoiDang; }

    public String getNgayTao() { return NgayTao; }
    public void setNgayTao(String ngayTao) { NgayTao = ngayTao; }

    // Getters/Setters cho Avatar
    public String getAVT1() { return AVT1; }
    public void setAVT1(String AVT1) { this.AVT1 = AVT1; }

    public String getAVT2() { return AVT2; }
    public void setAVT2(String AVT2) { this.AVT2 = AVT2; }

    public String getAVT3() { return AVT3; }
    public void setAVT3(String AVT3) { this.AVT3 = AVT3; }

    public String getAVT4() { return AVT4; }
    public void setAVT4(String AVT4) { this.AVT4 = AVT4; }

    public String getLoaiSuKien() { return LoaiSuKien; }
    public void setLoaiSuKien(String loaiSuKien) { LoaiSuKien = loaiSuKien; }
}
