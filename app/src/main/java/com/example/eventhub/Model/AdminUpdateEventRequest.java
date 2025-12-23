package com.example.eventhub.Model;

import java.io.File;

public class AdminUpdateEventRequest {
    private final int eventId;
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
    private final String trangThai;

    public AdminUpdateEventRequest(int eventId, File posterFile, String tenSK, String moTa, String loaiSuKien,
                                   String soLuongGioiHan, String diemCong, String coSo, String diaDiem,
                                   String thoiGianBatDau, String thoiGianKetThuc, String trangThai) {
        this.eventId = eventId;
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
        this.trangThai = safe(trangThai);
    }

    public static AdminUpdateEventRequest fromForm(int eventId,
                                                   File posterFile,
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
                                                   String trangThai) {
        String thoiGianBatDau = formatDateTime(ngayBatDau, gioBatDau);
        String thoiGianKetThuc = formatDateTime(ngayKetThuc, gioKetThuc);
        return new AdminUpdateEventRequest(
                eventId,
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
                trangThai
        );
    }

    public int getEventId() { return eventId; }
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
    public String getTrangThai() { return trangThai; }

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
