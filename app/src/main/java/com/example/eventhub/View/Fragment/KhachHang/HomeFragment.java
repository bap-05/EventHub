package com.example.eventhub.View.Fragment.KhachHang;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.eventhub.Adapter.SuKienSapToiAdapter;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.SuKienSapToi;

import com.example.eventhub.View.MainActivity;
import com.example.eventhub.ViewModel.SuKienViewModel;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eventhub.Adapter.DanhMucAdapter;
import com.example.eventhub.Adapter.SuKienAdapter;
import com.example.eventhub.Model.DanhMuc;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private List<DanhMuc> lDanhMuc;
    private  List<SuKienSapToi>suKienSapToiList = new ArrayList<>();
    private  List<SuKienSapToi>sukiendanhchobn = new ArrayList<>();
    private List<SuKien> suKienList =new ArrayList<>();
    private SuKienAdapter suKienAdapter;
    private RecyclerView rcv_danhmuc,rcv_sapdienra,rcv_saptoi,rcv_danhchobn;
    private DanhMucAdapter danhMucAdapter;
    private ImageView img_search;
    private SuKienSapToiAdapter suKienSapToiAdapter;
    private SuKienSapToiAdapter suKienDanhchobnAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_home, container, false);
        img_search = v.findViewById(R.id.img_home_search);
        img_search.setOnClickListener(view ->{
            Navigation.findNavController(v).navigate(R.id.searchFragment);

        });
        adddanhMuc(v);
        addSKsapDienRa(v);
        addSKSapToi(v);
        addSKDanhchobn(v);
        return v;
    }

    private void addSKDanhchobn(View v) {
        rcv_danhchobn = v.findViewById(R.id.rcv_home_danhchoban);
        rcv_danhchobn.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SuKienViewModel skvm = new SuKienViewModel();
        sukiendanhchobn =  skvm.load_SK_danhchobn();
        suKienDanhchobnAdapter = new SuKienSapToiAdapter(sukiendanhchobn);
        rcv_danhchobn.setAdapter(suKienDanhchobnAdapter);
        suKienDanhchobnAdapter.setListener(sk -> {
            skvm.setSk(sk);
            Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
        });
    }

    private void addSKSapToi(View v) {
        suKienSapToiList.clear();
        rcv_saptoi = v.findViewById(R.id.rcv_home_sukien);
        rcv_saptoi.setLayoutManager(new GridLayoutManager(v.getContext(),2,RecyclerView.HORIZONTAL,false));
        SuKienViewModel skvm = new SuKienViewModel();
        suKienSapToiList =  skvm.load_SK_danhchobn();
        suKienSapToiAdapter = new SuKienSapToiAdapter(suKienSapToiList);
       rcv_saptoi.setAdapter(suKienSapToiAdapter);
        suKienSapToiAdapter.setListener(sk -> {
            skvm.setSk(sk);
            Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
        });
    }

    private void addSKsapDienRa(View v) {
        rcv_sapdienra = v.findViewById(R.id.rcv_home_sapdienra);
        rcv_sapdienra.setLayoutManager(new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false));
        SuKienViewModel sk = new SuKienViewModel();
        suKienList = sk.loadSuKien();
        suKienAdapter = new SuKienAdapter(suKienList);
        rcv_sapdienra.setAdapter(suKienAdapter);
    }

    private void adddanhMuc(View v) {
        rcv_danhmuc = v.findViewById(R.id.rcv_danhmuc);
        lDanhMuc = new ArrayList<>();
        lDanhMuc.add( new DanhMuc("Music",R.drawable.music));
        lDanhMuc.add( new DanhMuc("WorkShop",R.drawable.vector));
        lDanhMuc.add( new DanhMuc("Chủ nhật xanh",R.drawable.cnx));
        lDanhMuc.add( new DanhMuc("Music",R.drawable.music));
        danhMucAdapter = new DanhMucAdapter(lDanhMuc);
        LinearLayoutManager lm = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);
        rcv_danhmuc.setLayoutManager(lm);
        rcv_danhmuc.setAdapter(danhMucAdapter);
        danhMucAdapter.setListener(dmuc->{
            Navigation.findNavController(v).navigate(R.id.chiTietDanhMucFragment2);
        });
    }


}