package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.DanhMucAdapter;
import com.example.eventhub.Adapter.SuKienSapToiAdapter;
import com.example.eventhub.Model.DanhMuc;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ChiTietDanhMucFragment extends Fragment {
    private RecyclerView rcv_danhmuc, rcv_sukien;
    private TextView txt_danhmuc;
    private List<DanhMuc> lDanhMuc;
    private ImageView img_search;
    private SuKienSapToiAdapter suKienSapToiAdapter;
    private DanhMucAdapter danhMucAdapter;
    private String loaiSelected = "";
    private final Set<String> loaiSet = new LinkedHashSet<>();
    private int currentUserId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chi_tiet_danh_muc, container, false);
        addView(v);
        if (getArguments() != null) {
            loaiSelected = getArguments().getString("ARG_LOAI_SK", "");
        }
        txt_danhmuc.setText(loaiSelected.isEmpty() ? "Danh muc" : loaiSelected);
        TaiKhoanViewModel.getTaikhoan().observe(getViewLifecycleOwner(), tk -> {
            currentUserId = tk != null ? tk.getMaTk() : -1;
            if (currentUserId > 0) {
                new ViewModelProvider(requireActivity()).get(SuKienViewModel.class).loadSuKienSapThamGia(currentUserId);
            }
        });
        addDanhmuc(v);
        addSK(v);
        img_search.setOnClickListener(v1 -> Navigation.findNavController(v1).navigate(R.id.searchFragment));
        return v;
    }

    private void addSK(View v) {
        rcv_sukien.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        androidx.lifecycle.Observer<List<SuKien>> render = suKiens -> {
            List<SuKien> filtered = new ArrayList<>();
            if (suKiens != null) {
                for (SuKien sk : suKiens) {
                    if (loaiSelected.isEmpty() || loaiSelected.equalsIgnoreCase(sk.getLoaiSuKien())) {
                        filtered.add(sk);
                    }
                }
            }
            suKienSapToiAdapter = new SuKienSapToiAdapter(filtered);
            rcv_sukien.setAdapter(suKienSapToiAdapter);
            suKienSapToiAdapter.setListener(new SuKienSapToiAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(SuKien sk) {
                    suKienViewModel.setSk(sk);
                    Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
                }

                @Override
                public void onCancel(SuKien sk) {
                    handleCancel(suKienViewModel, sk);
                }
            });
            // đánh dấu đã đăng ký
            if (suKienViewModel.getListSKSapThamGia().getValue() != null) {
                java.util.Set<Integer> ids = new java.util.HashSet<>();
                for (SuKien s : suKienViewModel.getListSKSapThamGia().getValue()) {
                    ids.add(s.getMaSK());
                }
                suKienSapToiAdapter.setRegistered(ids);
            }
        };
        suKienViewModel.getListTatCa().observe(getViewLifecycleOwner(), render);
        suKienViewModel.loadTatCaSuKien();
    }

    private void addDanhmuc(View v) {
        rcv_danhmuc.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));
        lDanhMuc = new ArrayList<>();
        danhMucAdapter = new DanhMucAdapter(lDanhMuc);
        rcv_danhmuc.setAdapter(danhMucAdapter);

        danhMucAdapter.setListener(dmuc -> {
            Bundle args = new Bundle();
            args.putString("ARG_LOAI_SK", dmuc.getText());
            Navigation.findNavController(v).navigate(R.id.chiTietDanhMucFragment2, args);
        });

        SuKienViewModel vm = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        androidx.lifecycle.Observer<List<SuKien>> update = this::updateDanhMucFromEvents;
        vm.getListSK().observe(getViewLifecycleOwner(), update);
        vm.getListSKSapToi().observe(getViewLifecycleOwner(), update);
        vm.getListSKdienra().observe(getViewLifecycleOwner(), update);
        vm.loadSuKien();
        vm.load_SK_saptoi();
        vm.load_SK_danhchobn();
    }

    private void addView(View v) {
        rcv_danhmuc = v.findViewById(R.id.rcv_ctdm_dm);
        rcv_sukien = v.findViewById(R.id.rcv_ctdm_sk);
        txt_danhmuc = v.findViewById(R.id.txt_ctdm_dm);
        img_search = v.findViewById(R.id.img_ctdm_search);
    }

    private void updateDanhMucFromEvents(List<SuKien> suKiens) {
        if (suKiens == null) return;
        loaiSet.clear();
        for (SuKien sk : suKiens) {
            if (sk.getLoaiSuKien() != null && !sk.getLoaiSuKien().trim().isEmpty()) {
                loaiSet.add(sk.getLoaiSuKien().trim());
            }
        }
        String[] defaults = {"Hội thảo", "Âm nhạc", "Chủ nhật xanh"};
        for (String d : defaults) loaiSet.add(d);

        lDanhMuc.clear();
        for (String t : loaiSet) {
            if (!t.equalsIgnoreCase(loaiSelected)) { // chỉ hiển thị hai loại còn lại
                lDanhMuc.add(new DanhMuc(t, mapIcon(t)));
            }
        }
        danhMucAdapter.notifyDataSetChanged();
    }

    private int mapIcon(String type) {
        String lower = type.toLowerCase();
        if (lower.contains("work")) return R.drawable.vector;
        if (lower.contains("âm") || lower.contains("nhạc") || lower.contains("music")) return R.drawable.music;
        if (lower.contains("chủ nhật") || lower.contains("chu nhat")) return R.drawable.cnx;
        return R.drawable.vector;
    }
    private void handleCancel(SuKienViewModel vm, SuKien sk) {
        if (sk == null) return;
        if (currentUserId <= 0) {
            Toast.makeText(getContext(), "Vui long dang nhap de huy", Toast.LENGTH_SHORT).show();
            return;
        }
        vm.huySuKien(sk.getMaSK(), currentUserId);
        vm.loadSuKienSapThamGia(currentUserId);
    }
}

