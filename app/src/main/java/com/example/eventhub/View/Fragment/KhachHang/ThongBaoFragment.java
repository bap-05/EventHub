package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventhub.Adapter.ThongBaoAdapter;
import com.example.eventhub.Model.ThongBao;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ThongBaoAdapter adapter;
    private List<ThongBao> thongBaoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_bao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView_thongbao);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu
        thongBaoList = new ArrayList<>();
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));
        thongBaoList.add(new ThongBao("Cập nhật lịch sự kiện “Tân sinh viên”", "Sự kiện sẽ được dời lịch sang 15/10/2025", "5 giờ trước"));

        adapter = new ThongBaoAdapter(thongBaoList);
        recyclerView.setAdapter(adapter);


//        Button btnTest = view.findViewById(R.id.btn_test_notification);
//        btnTest.setOnClickListener(v -> {
//            if (getActivity() instanceof MainActivity) {
//                ((MainActivity) getActivity()).sendNotification(
//                        "Sự kiện Test",
//                        "Đây là nội dung của thông báo test."
//                );
//            }
//        });
    }
}