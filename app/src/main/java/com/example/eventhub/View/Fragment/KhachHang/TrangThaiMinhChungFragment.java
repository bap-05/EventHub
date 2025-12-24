package com.example.eventhub.View.Fragment.KhachHang;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel;


public class TrangThaiMinhChungFragment extends Fragment {
    private ImageView img_poster,img_minhchung;
    private TextView txt_tensk, txt_thoigian, txt_tinhtrang, txt_lydoteude,txt_diem;
    private EditText txt_lydo;
    private Button btn_ql;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trang_thai_minh_chung, container, false);
        addView(v);
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getSuKienDaThamGia().observe(getViewLifecycleOwner(),suKienDaThamGia -> {
            if(suKienDaThamGia!=null)
            {
                txt_tensk.setText(suKienDaThamGia.getTenSK());
                txt_thoigian.setText("Thời gian: "+suKienDaThamGia.getThoiGianBatDau());
                Glide.with(v).load(suKienDaThamGia.getPoster()).into(img_poster);
                Glide.with(v).load(suKienDaThamGia.getAnhMinhChung()).into(img_minhchung);
                if(suKienDaThamGia.getDaCongDiem())
                {
                    txt_diem.setVisibility(VISIBLE);
                    txt_diem.setText("Điểm: +"+suKienDaThamGia.getDiemCong());
                }
                else
                {
                    txt_diem.setVisibility(GONE);
                }
                if(suKienDaThamGia.getTrangThaiMinhChung()==1)
                {
                    txt_tinhtrang.setText("Tình trạng: Đang chờ duyệt");
                    txt_lydoteude.setVisibility(GONE);
                    txt_lydo.setVisibility(GONE);
                }

                else
                    if(suKienDaThamGia.getTrangThaiMinhChung()==2)
                    {
                        txt_tinhtrang.setText("Tình trạng: Đã duyệt");
                        txt_lydoteude.setVisibility(GONE);
                        txt_lydo.setVisibility(GONE);
                    }

                    else
                        if(suKienDaThamGia.getTrangThaiMinhChung()==3)
                        {
                            txt_tinhtrang.setText("Tình trạng: Bị từ chối");
                            txt_lydoteude.setVisibility(VISIBLE);
                            txt_lydo.setVisibility(VISIBLE);

                            txt_lydo.setText(suKienDaThamGia.getLyDoTuChoi());
                        }

            }
        });
        btn_ql.setOnClickListener(view ->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return v;
    }
    private void addView(View v) {
        img_poster = v.findViewById(R.id.img_TrangThai_poster);
        img_minhchung = v.findViewById(R.id.img_TrangThai_minhchung);
        txt_tensk = v.findViewById(R.id.txt_TrangThai_tensk);
        txt_thoigian = v.findViewById(R.id.txt_TrangThai_thoigian);
        txt_tinhtrang = v.findViewById(R.id.txt_TrangThai_tinhtrang);
        txt_lydo = v.findViewById(R.id.txt_TrangThai_lydo);
        btn_ql = v.findViewById(R.id.btn_TrangThai_ql);
        txt_lydoteude =  v.findViewById(R.id.txt_TrangThai_lydotieude);
        txt_diem = v.findViewById(R.id.txt_TrangThai_diem);
    }
}