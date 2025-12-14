package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider; // Import ViewModel
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.EventAdapter;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel; // Import ViewModel của bạn

import java.util.ArrayList;
import java.util.List;

public class SapThamGiaFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<SuKien> eventList;
    private int currentUserId;
    private static final String ARG_USER_ID = "USER_ID";

    private SuKienViewModel suKienViewModel;

    public static SapThamGiaFragment newInstance(int userId) {
        SapThamGiaFragment fragment = new SapThamGiaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUserId = getArguments().getInt(ARG_USER_ID);
        }
        suKienViewModel = new ViewModelProvider(this).get(SuKienViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        setupRecyclerView();

        observeViewModel();

        suKienViewModel.loadSuKienSapThamGia(currentUserId);
    }

    private void observeViewModel() {
        suKienViewModel.getListSKSapThamGia().observe(getViewLifecycleOwner(), sukiens -> {
            if (sukiens != null) {
                eventList.clear();
                eventList.addAll(sukiens);
                adapter.notifyDataSetChanged();
            } else {
            }
        });

        suKienViewModel.getErr().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && getContext() != null) {
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        eventList = new ArrayList<>();
        adapter = new EventAdapter(eventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}