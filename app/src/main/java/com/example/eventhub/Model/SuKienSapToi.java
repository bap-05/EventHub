package com.example.eventhub.Model;

public class SuKienSapToi {
    String TenSK,TrangThai,ThoiGian,Coso, DiaDiem, DiemHD,MoTa;
    int Poster;


    public SuKienSapToi(String tenSK, int poster, String trangThai, String thoiGian, String coso, String diaDiem, String diemHD, String moTa) {
        TenSK = tenSK;
        Poster = poster;
        TrangThai = trangThai;
        ThoiGian = thoiGian;
        Coso = coso;
        DiaDiem = diaDiem;
        DiemHD = diemHD;
        MoTa = moTa;
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

    public int getPoster() {
        return Poster;
    }

    public void setPoster(int poster) {
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


}
