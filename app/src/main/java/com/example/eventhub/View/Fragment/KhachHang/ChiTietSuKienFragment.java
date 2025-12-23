package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.ThamGiaSuKien;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;
import com.squareup.picasso.Picasso;

public class ChiTietSuKienFragment extends Fragment implements View.OnClickListener {
    private TextView txt_noidung, txt_thoigian, txt_trangthai, txt_diem, txt_mota, txt_diadiem, txt_soluong;
    private ImageView img_poster;
    private ImageButton btn_ql;
    private Button btn_dk, btn_huy;
    private int maSK;
    private int currentUserId = -1;
    private SuKien currentSk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chi_tiet_su_kien, container, false);
        addView(v);

        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        TaiKhoanViewModel.getTaikhoan().observe(getViewLifecycleOwner(), tk -> {
            currentUserId = tk != null ? tk.getMaTk() : -1;
        });

        SuKienViewModel.getSk().observe(getViewLifecycleOwner(), sk -> {
            currentSk = sk;
            if (sk == null) return;
            maSK = sk.getMaSK();
            txt_thoigian.setText(sk.getThoiGianBatDau());
            txt_diadiem.setText(sk.getDiaDiem());
            txt_noidung.setText(sk.getTenSK());
            txt_mota.setText(sk.getMoTa());
            txt_diem.setText(sk.getDiemCong() + " Diem hoat dong");
            txt_soluong.setText("So luong tham gia: " + sk.getSoLuongDaDangKy() + "/" + sk.getSoLuongGioiHan());
            txt_trangthai.setText(sk.getTrangThai());
            if (sk.getPoster() != null && !sk.getPoster().isEmpty()) {
                Glide.with(v).load(sk.getPoster()).into(img_poster);
            } else {
                img_poster.setImageResource(R.drawable.postersukien);
            }
            updateButtonsState(suKienViewModel);
        });

        btn_dk.setOnClickListener(v1 -> {
            TaiKhoan tk = TaiKhoanViewModel.getTaikhoan().getValue();
            if (tk != null && currentSk != null) {
                ThamGiaSuKien thamGiaSuKien = new ThamGiaSuKien(tk.getMaTk(), maSK);
                SuKienViewModel.getDkSuKien().observe(getViewLifecycleOwner(), msg -> {
                    if (msg != null) {
                        Toast.makeText(v1.getContext(), msg, Toast.LENGTH_LONG).show();
                        Navigation.findNavController(v1).navigate(R.id.nav_home);
                    }
                });
                suKienViewModel.dangKySuKien(thamGiaSuKien);
            }
        });

        btn_huy.setOnClickListener(v12 -> cancelRegistration(suKienViewModel));

        suKienViewModel.getListSKSapThamGia().observe(getViewLifecycleOwner(), list -> {
            updateButtonsState(suKienViewModel);
        });

        btn_ql.setOnClickListener(this);
        return v;
    }

    private void cancelRegistration(SuKienViewModel vm) {
        if (currentSk == null) return;
        if (currentUserId <= 0) {
            Toast.makeText(getContext(), "Vui long dang nhap de huy", Toast.LENGTH_SHORT).show();
            return;
        }
        vm.huySuKien(currentSk.getMaSK(), currentUserId);
        vm.loadSuKienSapThamGia(currentUserId);
    }

    private void updateButtonsState(SuKienViewModel vm) {
        boolean isRegistered = false;
        if (vm.getListSKSapThamGia().getValue() != null && currentSk != null) {
            for (SuKien sk : vm.getListSKSapThamGia().getValue()) {
                if (sk.getMaSK() == currentSk.getMaSK()) {
                    isRegistered = true;
                    break;
                }
            }
        }
        if (isRegistered) {
            btn_dk.setText("Da dang ky");
            btn_dk.setBackgroundResource(R.drawable.custom_btn_dksk);
            btn_dk.setEnabled(false);
            boolean allowCancel = currentSk != null && "Sắp diễn ra".equalsIgnoreCase(currentSk.getTrangThai());
            btn_huy.setVisibility(allowCancel ? View.VISIBLE : View.GONE);
        } else {
            btn_dk.setText("Dang ky");
            btn_dk.setBackgroundResource(R.drawable.custom_btn_danhmuc);
            btn_dk.setEnabled(true);
            btn_huy.setVisibility(View.GONE);
        }
    }

    private void addView(View v) {
        txt_diadiem = v.findViewById(R.id.txt_ct_diadiem);
        txt_diem = v.findViewById(R.id.txt_ct_diemhd);
        txt_mota = v.findViewById(R.id.txt_ct_mota);
        txt_trangthai = v.findViewById(R.id.txt_ct_trangthai);
        txt_noidung = v.findViewById(R.id.txt_ct_noidung);
        txt_thoigian = v.findViewById(R.id.txt_ct_thoigian);
        img_poster = v.findViewById(R.id.img_ct_poster);
        btn_ql = v.findViewById(R.id.btn_ct_ql);
        txt_soluong = v.findViewById(R.id.txt_ct_soluongthamgia);
        btn_dk = v.findViewById(R.id.btn_ct_dk);
        btn_huy = v.findViewById(R.id.btn_ct_huy);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ct_ql) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
