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
import com.example.eventhub.ViewModel.AdminEventViewModel;
import com.example.eventhub.R;

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
        AdminEventViewModel vm = new androidx.lifecycle.ViewModelProvider(requireActivity()).get(AdminEventViewModel.class);
        AdminEventListAdapter adapter = new AdminEventListAdapter(new java.util.ArrayList<>(), new AdminEventListAdapter.OnEventClick() {
            @Override
            public void onItemClick(com.example.eventhub.Model.AdminEventItem item) {
                Bundle args = new Bundle();
                if (item.getSource() != null) {
                    args.putSerializable("event", item.getSource());
                }
                androidx.navigation.NavController navController = androidx.navigation.Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_admin_event_detail, args);
            }

            @Override
            public void onAvatarClick(com.example.eventhub.Model.AdminEventItem item) {
                Bundle args = new Bundle();
                args.putInt("ARG_MA_SK", item.getEventId());
                args.putString("ARG_EVENT_TITLE", item.getTitle());
                args.putString("ARG_EVENT_STATUS", item.getStatusText());
                args.putString("ARG_COUNT", item.getSoLuongDaDangKy() + "/" + item.getSoLuongGioiHan());
                androidx.navigation.NavController navController = androidx.navigation.Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_student_list, args);
            }

            @Override
            public void onEditClick(com.example.eventhub.Model.AdminEventItem item) {
                Bundle args = new Bundle();
                if (item.getSource() != null) {
                    args.putSerializable("event", item.getSource());
                }
                args.putString("status_text", item.getStatusText());
                androidx.navigation.NavController navController = androidx.navigation.Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_admin_edit_task, args);
            }
        });
        recyclerView.setAdapter(adapter);

        vm.getErr().observe(getViewLifecycleOwner(), err -> {
            if (err != null && !err.isEmpty()) {
                android.widget.Toast.makeText(getContext(), "Lỗi tải sự kiện admin: " + err, android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        androidx.lifecycle.Observer<java.util.List<com.example.eventhub.Model.AdminEventItem>> observer = items -> {
            if (items != null) {
                adapter.updateData(items);
            }
        };

        if (type == Type.ALL) {
            vm.getAll().observe(getViewLifecycleOwner(), observer);
        } else if (type == Type.ONGOING) {
            vm.getOngoing().observe(getViewLifecycleOwner(), observer);
        } else {
            vm.getDone().observe(getViewLifecycleOwner(), observer);
        }
    }
}
