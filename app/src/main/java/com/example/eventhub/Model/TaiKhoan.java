package com.example.eventhub.Model;

public class TaiKhoan {
    private String MaSV, HoTen, Khoa, Lop, AVT, Email, Pass, VaiTro;
    private int MaTK,DiemTichLuy;
    public int getMaTk() {
        return MaTK;
    }

    public void setMaTk(int maTk) {
        MaTK = maTk;
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
