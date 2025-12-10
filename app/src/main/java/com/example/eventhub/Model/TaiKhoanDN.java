package com.example.eventhub.Model;

public class TaiKhoanDN {
    private String MaSV, Pass;

    public TaiKhoanDN(String maSV, String pass) {
        MaSV = maSV;
        Pass = pass;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
