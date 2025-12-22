package com.example.eventhub.View.Fragment.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.AdminEventListAdapter;
import com.example.eventhub.Model.AdminEventItem;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;

public class AdminEventListFragment extends Fragment {

    public enum Type { ALL, ONGOING, DONE }

    private static final String ARG_TYPE = "ARG_TYPE";

    public static AdminEventListFragment newInstance(Type type) {
        AdminEventListFragment fragment = new AdminEventListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type.name());
        fragment.setArguments(args);
        return fragment;
    }

    private Type type = Type.ALL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String t = getArguments().getString(ARG_TYPE, Type.ALL.name());
            type = Type.valueOf(t);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_admin_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new AdminEventListAdapter(getMockData(type)));
    }

    private List<AdminEventItem> getMockData(Type type) {
        List<AdminEventItem> all = new ArrayList<>();
        all.add(new AdminEventItem("Sự kiện mua đồ", AdminEventItem.Status.ONGOING, true, "25/12", "27/12"));
        all.add(new AdminEventItem("Sự kiện chủ nhật xanh", AdminEventItem.Status.ONGOING, true, "26/12", "27/12"));
        all.add(new AdminEventItem("Workshop UI", AdminEventItem.Status.UPCOMING, false, "28/12", "29/12"));
        all.add(new AdminEventItem("Music Fest", AdminEventItem.Status.DONE, true, "20/12", "21/12"));
        all.add(new AdminEventItem("Hội thao", AdminEventItem.Status.UPCOMING, false, "30/12", "31/12"));
        all.add(new AdminEventItem("Đêm nhạc", AdminEventItem.Status.DONE, false, "15/12", "16/12"));
        all.add(new AdminEventItem("Triển lãm ảnh", AdminEventItem.Status.UPCOMING, true, "05/01", "06/01"));
        all.add(new AdminEventItem("Cuộc thi coding", AdminEventItem.Status.ONGOING, false, "24/12", "26/12"));
        all.add(new AdminEventItem("Chạy bộ gây quỹ", AdminEventItem.Status.DONE, false, "10/12", "10/12"));
        all.add(new AdminEventItem("Hội thảo AI", AdminEventItem.Status.UPCOMING, true, "07/01", "07/01"));

        if (type == Type.ALL) {
            return all.subList(0, Math.min(5, all.size()));
        }

        List<AdminEventItem> filtered = new ArrayList<>();
        for (AdminEventItem item : all) {
            if (type == Type.ONGOING && item.isShowQr()) {
                filtered.add(item);
            } else if (type == Type.DONE && item.isShowDone()) {
                filtered.add(item);
            }
        }

        // Bảo đảm mỗi tab đủ 5 item: nhân bản trong cùng nhóm, không lẫn loại khác
        if (!filtered.isEmpty()) {
            int baseCount = filtered.size();
            while (filtered.size() < 5) {
                filtered.add(filtered.get(filtered.size() % baseCount));
            }
        }
        return filtered;
    }
}
