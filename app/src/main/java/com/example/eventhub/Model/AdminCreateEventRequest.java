package com.example.eventhub.Model;

import java.io.File;

public class AdminCreateEventRequest {
    private final File posterFile;
    private final String tenSK;
    private final String moTa;
    private final String loaiSuKien;
    private final String soLuongGioiHan;
    private final String diemCong;
    private final String coSo;
    private final String diaDiem;
    private final String thoiGianBatDau;
    private final String thoiGianKetThuc;
    private final String nguoiDang;

    public AdminCreateEventRequest(File posterFile, String tenSK, String moTa, String loaiSuKien,
                                   String soLuongGioiHan, String diemCong, String coSo, String diaDiem,
                                   String thoiGianBatDau, String thoiGianKetThuc, String nguoiDang) {
        this.posterFile = posterFile;
        this.tenSK = safe(tenSK);
        this.moTa = safe(moTa);
        this.loaiSuKien = safe(loaiSuKien);
        this.soLuongGioiHan = safe(soLuongGioiHan);
        this.diemCong = safe(diemCong);
        this.coSo = safe(coSo);
        this.diaDiem = safe(diaDiem);
        this.thoiGianBatDau = safe(thoiGianBatDau);
        this.thoiGianKetThuc = safe(thoiGianKetThuc);
        this.nguoiDang = safe(nguoiDang);
    }

    public static AdminCreateEventRequest fromForm(File posterFile,
                                                   String tenSK,
                                                   String moTa,
                                                   String loaiSuKien,
                                                   String soLuongGioiHan,
                                                   String diemCong,
                                                   String coSo,
                                                   String diaDiem,
                                                   String ngayBatDau,
                                                   String gioBatDau,
                                                   String ngayKetThuc,
                                                   String gioKetThuc,
                                                   String nguoiDang) {
        String thoiGianBatDau = formatDateTime(ngayBatDau, gioBatDau);
        String thoiGianKetThuc = formatDateTime(ngayKetThuc, gioKetThuc);
        return new AdminCreateEventRequest(
                posterFile,
                tenSK,
                moTa,
                loaiSuKien,
                soLuongGioiHan,
                diemCong,
                coSo,
                diaDiem,
                thoiGianBatDau,
                thoiGianKetThuc,
                nguoiDang
        );
    }

    public File getPosterFile() { return posterFile; }
    public String getTenSK() { return tenSK; }
    public String getMoTa() { return moTa; }
    public String getLoaiSuKien() { return loaiSuKien; }
    public String getSoLuongGioiHan() { return soLuongGioiHan; }
    public String getDiemCong() { return diemCong; }
    public String getCoSo() { return coSo; }
    public String getDiaDiem() { return diaDiem; }
    public String getThoiGianBatDau() { return thoiGianBatDau; }
    public String getThoiGianKetThuc() { return thoiGianKetThuc; }
    public String getNguoiDang() { return nguoiDang; }

    private static String formatDateTime(String date, String time) {
        String safeDate = safe(date);
        String safeTime = safe(time);
        String[] parts = safeDate.split("/");
        if (parts.length == 3) {
            return parts[2] + "-" + parts[1] + "-" + parts[0] + " " + safeTime + ":00";
        }
        return safeDate + " " + safeTime + ":00";
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
