package com.example.eventhub.Model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TaiKhoan implements Serializable {

    @SerializedName("MaTK")
    private int MaTk;

    @SerializedName("MaSV")
    private String MaSV;

    @SerializedName("TenTK")
    private String HoTen;

    @SerializedName("Khoa")
    private String Khoa;

    @SerializedName("Lop")
    private String Lop;

    @SerializedName("AVT")
    private String AVT;

    @SerializedName("Email")
    private String Email;

    @SerializedName("Pass")
    private String Pass;

    @SerializedName("VaiTro")
    private String VaiTro;

    @SerializedName("DiemTichLuy")
    private int DiemTichLuy;

    public TaiKhoan() {
    }


    public int getMaTk() {
        return MaTk;
    }

    public void setMaTk(int maTk) {
        MaTk = maTk;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getKhoa() {
        return Khoa;
    }

    public void setKhoa(String khoa) {
        Khoa = khoa;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    public String getAVT() {
        return AVT;
    }

    public void setAVT(String AVT) {
        this.AVT = AVT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(String vaiTro) {
        VaiTro = vaiTro;
    }

    public int getDiemTichLuy() {
        return DiemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        DiemTichLuy = diemTichLuy;
    }
}
