package com.example.eventhub.Model;

public class SuKienSapToi {
    String TenSK, Poster,TrangThai,ThoiGian,Coso, DiaDiem, DiemHD,MoTa;
    int MaSK;


    public SuKienSapToi(String tenSK, String poster, String trangThai, String thoiGian, String coso, String diaDiem, String diemHD, String moTa, int maSK) {
        TenSK = tenSK;
        Poster = poster;
        TrangThai = trangThai;
        ThoiGian = thoiGian;
        Coso = coso;
        DiaDiem = diaDiem;
        DiemHD = diemHD;
        MoTa = moTa;
        MaSK = maSK;
    }


    public String getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        DiaDiem = diaDiem;
    }

    public String getDiemHD() {
        return DiemHD;
    }

    public void setDiemHD(String diemHD) {
        DiemHD = diemHD;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getCoSo() {
        return Coso;
    }

    public void setCoSo(String coSo) {
        Coso = coSo;
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

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public int getMaSK() {
        return MaSK;
    }

    public void setMaSK(int maSK) {
        MaSK = maSK;
    }
}
