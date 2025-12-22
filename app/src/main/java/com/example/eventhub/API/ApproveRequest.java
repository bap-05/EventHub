package com.example.eventhub.API;

public class ApproveRequest {
    private int trangThai;
    private String lyDo;

    public ApproveRequest(int trangThai, String lyDo) {
        this.trangThai = trangThai;
        this.lyDo = lyDo;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public String getLyDo() {
        return lyDo;
    }
}
