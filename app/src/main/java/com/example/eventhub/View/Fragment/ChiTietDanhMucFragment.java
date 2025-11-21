package com.example.eventhub.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventhub.Adapter.DanhMucAdapter;
import com.example.eventhub.Adapter.SuKienSapToiAdapter;
import com.example.eventhub.Model.DanhMuc;
import com.example.eventhub.Model.SuKienSapToi;
import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;
import com.example.eventhub.ViewModel.SuKienViewModel;

import java.util.ArrayList;
import java.util.List;


public class ChiTietDanhMucFragment extends Fragment {
    private RecyclerView rcv_danhmuc, rcv_sukien;
    private TextView txt_danhmuc;
    private List<DanhMuc> lDanhMuc;
    private ImageView img_search;
    private List<SuKienSapToi> suKienSapToiList = new ArrayList<>();
    private SuKienSapToiAdapter suKienSapToiAdapter;
    private  DanhMucAdapter danhMucAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chi_tiet_danh_muc, container, false);
        addView(v);
        addDanhmuc(v);
        addSK(v);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).addFragment(new SearchFragment());
            }
        });
        return v;
    }

    private void addSK(View v) {
        rcv_sukien.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SuKienViewModel suKienViewModel = new SuKienViewModel();
        suKienSapToiList =suKienViewModel.load_SK_danhchobn();
        suKienSapToiAdapter = new SuKienSapToiAdapter(suKienSapToiList);
        rcv_sukien.setAdapter(suKienSapToiAdapter);
        suKienSapToiAdapter.setListener(sk -> {
            suKienViewModel.setSk(sk);
            ((MainActivity)requireActivity()).addFragment(new ChiTietSuKienFragment());
        });
    }

    private void addDanhmuc(View v) {
        rcv_danhmuc.setLayoutManager(new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false));
        lDanhMuc = new ArrayList<>();
        lDanhMuc.add( new DanhMuc("Music",R.drawable.music));
        lDanhMuc.add( new DanhMuc("WorkShop",R.drawable.vector));
        lDanhMuc.add( new DanhMuc("Chủ nhật xanh",R.drawable.cnx));
        lDanhMuc.add( new DanhMuc("Music",R.drawable.music));
        danhMucAdapter = new DanhMucAdapter(lDanhMuc);
        rcv_danhmuc.setAdapter(danhMucAdapter);

    }

    private void addView(View v) {
        rcv_danhmuc = v.findViewById(R.id.rcv_ctdm_dm);
        rcv_sukien = v.findViewById(R.id.rcv_ctdm_sk);
        txt_danhmuc = v.findViewById(R.id.txt_ctdm_dm);
        img_search = v.findViewById(R.id.img_ctdm_search);
    }
}