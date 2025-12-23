package com.example.eventhub.Model;

import com.example.eventhub.R;

public class AdminEventItem {
    public enum Status { UPCOMING, ONGOING, DONE }

    private final String title;
    private final String statusText;
    private final String priorityText;
    private final String startDate;
    private final String endDate;
    private final Status status;
    private final boolean highPriority;
    private final int eventId;
    private final int soLuongDaDangKy;
    private final int soLuongGioiHan;
    private final String avt1;
    private final String avt2;
    private final String avt3;
    private final SuKien source;

    public AdminEventItem(String title, Status status, boolean highPriority, String startDate, String endDate, int eventId) {
        this(title, status, highPriority, startDate, endDate, eventId, 0, 0, null, null, null, null);
    }

    public AdminEventItem(String title, Status status, boolean highPriority, String startDate, String endDate, int eventId,
                          int soLuongDaDangKy, int soLuongGioiHan, String avt1, String avt2, String avt3, SuKien source) {
        this.title = title;
        this.status = status;
        this.highPriority = highPriority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventId = eventId;
        this.soLuongDaDangKy = soLuongDaDangKy;
        this.soLuongGioiHan = soLuongGioiHan;
        this.avt1 = avt1;
        this.avt2 = avt2;
        this.avt3 = avt3;
        this.source = source;
        this.statusText = status == Status.ONGOING ? "Dang dien ra" : status == Status.DONE ? "Da dien ra" : "Sap dien ra";
        this.priorityText = highPriority ? "High" : "Normal";
    }

    public String getTitle() { return title; }
    public String getStatusText() { return statusText; }
    public String getPriorityText() { return priorityText; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public int getEventId() { return eventId; }
    public int getSoLuongDaDangKy() { return soLuongDaDangKy; }
    public int getSoLuongGioiHan() { return soLuongGioiHan; }
    public String getAvt1() { return avt1; }
    public String getAvt2() { return avt2; }
    public String getAvt3() { return avt3; }
    public SuKien getSource() { return source; }
    public Status getStatus() { return status; }
    public boolean isShowQr() { return false; } // không dùng QR cho ongoing nữa
    public boolean isShowDone() { return status == Status.DONE; }
    public int getAvatarRes() { return R.drawable.avatar; }
    public int getStatusIconRes() {
        return status == Status.ONGOING ? R.drawable.clockinprogress
                : status == Status.DONE ? R.drawable.sukiendadienra
                : R.drawable.sukiensapdienra;
    }
    public int getPriorityIconRes() {
        return highPriority ? R.drawable.flaghigh : R.drawable.flaghigh;
    }
    public int getDoneIconRes() { return R.drawable.sukiendadienra; }
    public int getCalendarIconRes() { return R.drawable.calendarduoiphansukiensapdienra; }
}
