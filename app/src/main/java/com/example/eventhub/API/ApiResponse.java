package com.example.eventhub.API;

import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.SuKienDaThamGia;
import com.example.eventhub.Model.TaiKhoan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName(value = "getSK", alternate = {"suKien"})
    private List<SuKien> suKienList;
    @SerializedName("taikhoan")
    private TaiKhoan taiKhoan;
    @SerializedName("sukiencantim")
    private SuKien suKien;
    @SerializedName("trangThai")
    private SuKienDaThamGia suKienDaThamGia;
    @SerializedName("DiemTichLuy") // Phải khớp với key bên Node.js (cẩn thận chữ hoa/thường)
    private int diem;

    public int getDiem() { return diem; }
    public SuKienDaThamGia getSuKienDaThamGia() {
        return suKienDaThamGia;
    }

    public SuKien getSuKien() {
        return suKien;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public List<SuKien> getSuKienList() {
        return suKienList;
    }
}
