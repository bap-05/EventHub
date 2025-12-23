package com.example.eventhub.View.Fragment.KhachHang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.DanhMucAdapter;
import com.example.eventhub.Adapter.SuKienAdapter;
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

public class HomeFragment extends Fragment {
    private List<DanhMuc> lDanhMuc;
    private SuKienAdapter suKienAdapter;
    private RecyclerView rcv_danhmuc, rcv_sapdienra, rcv_saptoi, rcv_danhchobn;
    private DanhMucAdapter danhMucAdapter;
    private ImageView img_search;
    private SuKienSapToiAdapter suKienSapToiAdapter;
    private SuKienSapToiAdapter suKienDanhchobnAdapter;
    private final Set<String> loaiSet = new LinkedHashSet<>();
    private SuKienViewModel suKienViewModel;
    private android.widget.TextView tvSeeAllSapToi;
    private android.widget.TextView tvSeeAllSapDienRa;
    private final java.util.Set<Integer> registeredIds = new java.util.HashSet<>();
    private int currentUserId = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        img_search = v.findViewById(R.id.img_home_search);
        img_search.setOnClickListener(view -> Navigation.findNavController(v).navigate(R.id.searchFragment));
        suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        tvSeeAllSapToi = v.findViewById(R.id.txt_home_seeall);
        tvSeeAllSapDienRa = v.findViewById(R.id.txt_home_seeall_sapdienra);
        tvSeeAllSapToi.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString("ARG_TYPE", com.example.eventhub.View.Fragment.KhachHang.AllEventsFragment.Type.UPCOMING.name());
            Navigation.findNavController(v).navigate(R.id.allEventsFragment, args);
        });
        tvSeeAllSapDienRa.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString("ARG_TYPE", com.example.eventhub.View.Fragment.KhachHang.AllEventsFragment.Type.SOON.name());
            Navigation.findNavController(v).navigate(R.id.allEventsFragment, args);
        });
        adddanhMuc(v);
        addSKsapDienRa(v);
        addSKSapToi(v);
        addSKDanhchobn(v);
        suKienViewModel.getErr().observe(getViewLifecycleOwner(), err -> {
            if (err != null) {
                Toast.makeText(v.getContext(), "Loi tai su kien: " + err, Toast.LENGTH_SHORT).show();
            }
        });
        TaiKhoanViewModel.getTaikhoan().observe(getViewLifecycleOwner(), taiKhoan -> {
            if (taiKhoan != null) {
                currentUserId = taiKhoan.getMaTk();
                suKienViewModel.loadSuKienSapThamGia(taiKhoan.getMaTk());
            } else {
                currentUserId = -1;
            }
        });
        suKienViewModel.getListSKSapThamGia().observe(getViewLifecycleOwner(), list -> {
            registeredIds.clear();
            if (list != null) {
                for (SuKien sk : list) {
                    registeredIds.add(sk.getMaSK());
                }
            }
            if (suKienSapToiAdapter != null) suKienSapToiAdapter.setRegistered(registeredIds);
            if (suKienDanhchobnAdapter != null) suKienDanhchobnAdapter.setRegistered(registeredIds);
            if (suKienAdapter != null) suKienAdapter.setRegisteredIds(registeredIds);
        });
        suKienViewModel.getListTatCa().observe(getViewLifecycleOwner(), this::updateDanhMucFromEvents);
        suKienViewModel.loadTatCaSuKien();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (suKienViewModel != null) {
            suKienViewModel.loadTatCaSuKien();
            suKienViewModel.loadSuKien();
            suKienViewModel.load_SK_saptoi();
            suKienViewModel.load_SK_danhchobn();
        }
    }

    private void addSKDanhchobn(View v) {
        rcv_danhchobn = v.findViewById(R.id.rcv_home_danhchoban);
        rcv_danhchobn.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getListSKdienra().observe(getViewLifecycleOwner(), suKiens -> {
            if (suKiens != null) {
                suKienDanhchobnAdapter = new SuKienSapToiAdapter(suKiens);
                rcv_danhchobn.setAdapter(suKienDanhchobnAdapter);
                suKienDanhchobnAdapter.setListener(new SuKienSapToiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(SuKien sk) {
                        suKienViewModel.setSk(sk);
                        Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
                    }

                    @Override
                    public void onCancel(SuKien sk) {
                        handleCancel(sk);
                    }
                });
                suKienDanhchobnAdapter.setRegistered(registeredIds);
            }
        });
        suKienViewModel.load_SK_danhchobn();
    }

    private void addSKSapToi(View v) {
        rcv_saptoi = v.findViewById(R.id.rcv_home_sukien);
        rcv_saptoi.setLayoutManager(new GridLayoutManager(v.getContext(), 2, RecyclerView.HORIZONTAL, false));
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getListSKSapToi().observe(getViewLifecycleOwner(), suKiens -> {
            if (suKiens != null) {
                suKienSapToiAdapter = new SuKienSapToiAdapter(suKiens);
                rcv_saptoi.setAdapter(suKienSapToiAdapter);
                suKienSapToiAdapter.setListener(new SuKienSapToiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(SuKien sk) {
                        suKienViewModel.setSk(sk);
                        Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
                    }

                    @Override
                    public void onCancel(SuKien sk) {
                        handleCancel(sk);
                    }
                });
                updateDanhMucFromEvents(suKiens);
                suKienSapToiAdapter.setRegistered(registeredIds);
            }
        });
        suKienViewModel.load_SK_saptoi();
    }

    private void addSKsapDienRa(View v) {
        rcv_sapdienra = v.findViewById(R.id.rcv_home_sapdienra);
        rcv_sapdienra.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getListSK().observe(getViewLifecycleOwner(), suKiens -> {
            if (suKiens != null) {
                suKienAdapter = new SuKienAdapter(suKiens);
                rcv_sapdienra.setAdapter(suKienAdapter);
                suKienAdapter.setListener(sk -> {
                    suKienViewModel.setSk(sk);
                    Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
                });
                updateDanhMucFromEvents(suKiens);
                suKienAdapter.setRegisteredIds(registeredIds);
            }
        });
        suKienViewModel.loadSuKien();
    }

    private void adddanhMuc(View v) {
        rcv_danhmuc = v.findViewById(R.id.rcv_danhmuc);
        lDanhMuc = new ArrayList<>();
        danhMucAdapter = new DanhMucAdapter(lDanhMuc);
        LinearLayoutManager lm = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcv_danhmuc.setLayoutManager(lm);
        rcv_danhmuc.setAdapter(danhMucAdapter);
        danhMucAdapter.setListener(dmuc -> {
            Bundle args = new Bundle();
            args.putString("ARG_LOAI_SK", dmuc.getText());
            Navigation.findNavController(v).navigate(R.id.chiTietDanhMucFragment2, args);
        });
    }

    private void updateDanhMucFromEvents(List<SuKien> suKiens) {
        if (suKiens == null) return;
        loaiSet.clear();
        for (SuKien sk : suKiens) {
            if (sk.getLoaiSuKien() != null && !sk.getLoaiSuKien().trim().isEmpty()) {
                loaiSet.add(sk.getLoaiSuKien().trim());
            }
        }
        // đảm bảo 3 loại mặc định vẫn xuất hiện nếu chưa có dữ liệu
        String[] defaults = {"Hội thảo", "Âm nhạc", "Chủ nhật xanh"};
        for (String d : defaults) loaiSet.add(d);

        lDanhMuc.clear();
        for (String t : loaiSet) {
            lDanhMuc.add(new DanhMuc(t, mapIcon(t)));
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
    private void handleCancel(SuKien sk) {
        if (sk == null) return;
        if (currentUserId <= 0) {
            Toast.makeText(getContext(), "Vui long dang nhap de huy", Toast.LENGTH_SHORT).show();
            return;
        }
        suKienViewModel.huySuKien(sk.getMaSK(), currentUserId);
        suKienViewModel.loadSuKienSapThamGia(currentUserId);
    }
}


