package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Adapter.EventAdapter;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DaThamGiaFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<SuKien> eventList;
    private int currentUserId;
    private static final String ARG_USER_ID = "USER_ID";
    public  static DaThamGiaFragment newInstance(int userId){
        DaThamGiaFragment fragment = new DaThamGiaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID,userId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public  void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            currentUserId = getArguments().getInt(ARG_USER_ID);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        setupRecyclerView();
        loadEvents();
    }

    private void loadEvents() {
        IAPI iapi = ApiClient.getClient().create(IAPI.class);
//        Call<List<SuKien>> call = iapi.getSuKienDaThamGia(currentUserId);

    }

    private void setupRecyclerView() {
        
    }
}
