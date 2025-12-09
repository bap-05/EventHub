package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventhub.R;

import com.example.eventhub.ViewModel.SuKienViewModel;
import com.squareup.picasso.Picasso;


public class ChiTietSuKienFragment extends Fragment implements View.OnClickListener {
    private TextView txt_noidung, txt_thoigian, txt_trangthai, txt_diem, txt_mota, txt_diadiem, txt_soluong;
    private ImageView img_poster;
    private ImageButton btn_ql;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chi_tiet_su_kien, container, false);
        addView(v);
        SuKienViewModel.getSk().observe(getViewLifecycleOwner(), suKienSapToi -> {
            txt_thoigian.setText(suKienSapToi.getThoiGianBatDau());
            txt_diadiem.setText(suKienSapToi.getDiaDiem());
            txt_noidung.setText(suKienSapToi.getTenSK());
            txt_mota.setText(suKienSapToi.getMoTa());
            txt_diem.setText(suKienSapToi.getDiemCong()+" điểm hoạt động");
            txt_soluong.setText("Số lương tham gia: "+suKienSapToi.getSoLuongDaDangKy()+"/"+suKienSapToi.getSoLuongGioiHan());
            txt_trangthai.setText(suKienSapToi.getTrangThai());
            Picasso.get().load(suKienSapToi.getPoster()).into(img_poster);
        });
        btn_ql.setOnClickListener(this);
        return v;
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
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_ct_ql)
        {
//            ((MainActivity)requireActivity()).addFragment(new HomeFragment(),false,1);
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }
}