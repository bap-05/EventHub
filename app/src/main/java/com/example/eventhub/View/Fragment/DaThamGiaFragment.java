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
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;

public class DaThamGiaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_event_list,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<SuKien> skdathamgia = new ArrayList<>();
//        skdathamgia.add(new SuKienSapToi(
//                "Ngày hội tân sinh viên 2025",
//                "URL_POSTER_3",
//                "Đã tích lũy",
//                "12/09 - CN - 7:00",
//                "Cơ sở 1",
//                101,
//                2
//
//        ));
//        skdathamgia.add(new SuKienSapToi(
//                "Ngày hội tân sinh viên 2025",
//                "URL_POSTER_4",
//                "Đã tích lũy",
//                "12/08 - CN - 7:00",
//                "Cơ sở 1",
//                102,
//                2
//        ));
        EventAdapter adapter=new EventAdapter(skdathamgia);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
