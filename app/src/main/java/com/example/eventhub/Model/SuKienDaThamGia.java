package com.example.eventhub.Model;

public class SuKienDaThamGia {
    private String TenSK, ThoiGianBatDau, Poster,  LyDoTuChoi,AnhMinhChung;
    private int TrangThaiMinhChung, DiemCong;
    private Boolean DaCongDiem;
    public SuKienDaThamGia(String tenSK, String thoiGianBatDau, String poster, int trangThaiMinhChung,Boolean daCongDiem, int diemCong, String lyDoTuChoi, String anhMinhChung) {
        TenSK = tenSK;
        ThoiGianBatDau = thoiGianBatDau;
        Poster = poster;
        TrangThaiMinhChung = trangThaiMinhChung;
        LyDoTuChoi = lyDoTuChoi;
        AnhMinhChung = anhMinhChung;
        DaCongDiem = daCongDiem;
        DiemCong = diemCong;
    }

    public String getTenSK() {
        return TenSK;
    }

    public int getDiemCong() {
        return DiemCong;
    }

    public void setDiemCong(int diemCong) {
        DiemCong = diemCong;
    }

    public Boolean getDaCongDiem() {
        return DaCongDiem;
    }

    public void setDaCongDiem(Boolean daCongDiem) {
        DaCongDiem = daCongDiem;
    }

    public void setTenSK(String tenSK) {
        TenSK = tenSK;
    }

    public String getThoiGianBatDau() {
        return ThoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        ThoiGianBatDau = thoiGianBatDau;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public int getTrangThaiMinhChung() {
        return TrangThaiMinhChung;
    }

    public void setTrangThaiMinhChung(int trangThaiMinhChung) {
        TrangThaiMinhChung = trangThaiMinhChung;
    }

    public String getLyDoTuChoi() {
        return LyDoTuChoi;
    }

    public void setLyDoTuChoi(String lyDoTuChoi) {
        LyDoTuChoi = lyDoTuChoi;
    }

    public String getAnhMinhChung() {
        return AnhMinhChung;
    }

    public void setAnhMinhChung(String anhMinhChung) {
        AnhMinhChung = anhMinhChung;
    }
}
