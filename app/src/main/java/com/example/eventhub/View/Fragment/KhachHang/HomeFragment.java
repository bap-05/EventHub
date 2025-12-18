package com.example.eventhub.View.Fragment.KhachHang;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.eventhub.Adapter.SuKienSapToiAdapter;
import com.example.eventhub.Model.SuKien;

import com.example.eventhub.ViewModel.SuKienViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eventhub.Adapter.DanhMucAdapter;
import com.example.eventhub.Adapter.SuKienAdapter;
import com.example.eventhub.Model.DanhMuc;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private List<DanhMuc> lDanhMuc;


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
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getErr().observe(getViewLifecycleOwner(),err->{
            if (err != null) {
                Toast.makeText(v.getContext(), "Lỗi tải sự kiện: " + err, Toast.LENGTH_SHORT).show();
            }
        });
       TaiKhoanViewModel.getTaikhoan().observe(getViewLifecycleOwner(),taiKhoan -> {
           if(taiKhoan!=null){
               suKienViewModel.loadSuKienSapThamGia(taiKhoan.getMaTk());
           }
       });
        return v;
    }

    private void addSKDanhchobn(View v) {
        rcv_danhchobn = v.findViewById(R.id.rcv_home_danhchoban);
        rcv_danhchobn.setLayoutManager(new LinearLayoutManager(v.getContext()));
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getListSKdienra().observe(getViewLifecycleOwner(),suKiens -> {
            if(suKiens!=null)
            {
                suKienDanhchobnAdapter = new SuKienSapToiAdapter(suKiens);
                rcv_danhchobn.setAdapter(suKienDanhchobnAdapter);
                suKienDanhchobnAdapter.setListener(sk -> {
                    suKienViewModel.setSk(sk);
                    Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
                });
            }
        });
        suKienViewModel.load_SK_danhchobn();

    }

    private void addSKSapToi(View v) {

        rcv_saptoi = v.findViewById(R.id.rcv_home_sukien);
        rcv_saptoi.setLayoutManager(new GridLayoutManager(v.getContext(),2,RecyclerView.HORIZONTAL,false));
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getListSKSapToi().observe(getViewLifecycleOwner(),suKiens -> {
            if(suKiens!=null)
            {
                suKienSapToiAdapter = new SuKienSapToiAdapter(suKiens);
                rcv_saptoi.setAdapter(suKienSapToiAdapter);
                suKienSapToiAdapter.setListener(sk -> {
                    suKienViewModel.setSk(sk);
                    Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
                });
            }
        });
        suKienViewModel.load_SK_saptoi();

    }

    private void addSKsapDienRa(View v) {
        rcv_sapdienra = v.findViewById(R.id.rcv_home_sapdienra);
        rcv_sapdienra.setLayoutManager(new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false));
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getListSK().observe(getViewLifecycleOwner(),suKiens -> {
            if(suKiens!=null)
            {
                suKienAdapter = new SuKienAdapter(suKiens);
                rcv_sapdienra.setAdapter(suKienAdapter);
                suKienAdapter.setListener(sk -> {
                    suKienViewModel.setSk(sk);
                    Navigation.findNavController(v).navigate(R.id.chiTietSuKienFragment);
                });
            }

        });
        suKienViewModel.loadSuKien();


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