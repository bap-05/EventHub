package com.example.eventhub.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.EventAdapter;
import com.example.eventhub.Model.SuKienSapToi;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;

public class SapThamGiaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_event_list,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<SuKienSapToi> sksaptoi = new ArrayList<>();
        // Item 1
        sksaptoi.add(new SuKienSapToi(
                "Ngày hội tân sinh viên 2025",    // tenSK
                R.drawable.boy,              // poster (Kiểu int - resource ID)
                "Sắp diễn ra",                    // trangThai
                "12/10 - CN - 7:00",              // thoiGian
                "Cơ sở 1",                        // coso
                "Hội trường A",                   // diaDiem (Mới thêm)
                "5",                              // diemHD (Kiểu String)
                "Mô tả chi tiết về sự kiện..."    // moTa (Mới thêm)
        ));

// Item 2
        sksaptoi.add(new SuKienSapToi(
                "Ngày hội tân sinh viên 2025",    // tenSK
                R.drawable.boy,              // poster (Kiểu int - resource ID)
                "Sắp diễn ra",                    // trangThai
                "12/10 - CN - 7:00",              // thoiGian
                "Cơ sở 20",                       // coso
                "Phòng 104",                      // diaDiem (Lấy từ số 104 cũ của bạn)
                "0",                              // diemHD (Lấy từ số 0 cũ, chuyển sang String)
                "Không có mô tả"                  // moTa (Mới thêm)
        ));
        EventAdapter adapter =new EventAdapter(sksaptoi);
        recyclerView.setAdapter(adapter);
        return view;

    }
}
