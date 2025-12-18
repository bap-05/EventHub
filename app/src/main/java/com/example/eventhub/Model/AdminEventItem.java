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

    public AdminEventItem(String title, Status status, boolean highPriority, String startDate, String endDate) {
        this.title = title;
        this.status = status;
        this.highPriority = highPriority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusText = status == Status.ONGOING ? "Đang diễn ra" : status == Status.DONE ? "Đã diễn ra" : "Sắp diễn ra";
        this.priorityText = highPriority ? "High" : "Normal";
    }

    public String getTitle() { return title; }
    public String getStatusText() { return statusText; }
    public String getPriorityText() { return priorityText; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public boolean isShowQr() { return status == Status.ONGOING; }
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
