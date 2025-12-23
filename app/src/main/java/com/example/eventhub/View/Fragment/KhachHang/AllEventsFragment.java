package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.SuKienSapToiAdapter;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;

public class AllEventsFragment extends Fragment {
    private static final String ARG_TYPE = "ARG_TYPE";
    public enum Type { UPCOMING, SOON }

    public static AllEventsFragment newInstance(Type type) {
        AllEventsFragment f = new AllEventsFragment();
        Bundle b = new Bundle();
        b.putString(ARG_TYPE, type.name());
        f.setArguments(b);
        return f;
    }

    private Type type = Type.UPCOMING;
    private int currentUserId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String t = getArguments().getString(ARG_TYPE, Type.UPCOMING.name());
            type = Type.valueOf(t);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTitle = view.findViewById(R.id.tv_all_title);
        RecyclerView rv = view.findViewById(R.id.rv_all_events);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        SuKienViewModel vm = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        SuKienSapToiAdapter adapter = new SuKienSapToiAdapter(new java.util.ArrayList<>());
        rv.setAdapter(adapter);
        TaiKhoanViewModel.getTaikhoan().observe(getViewLifecycleOwner(), tk -> {
            currentUserId = tk != null ? tk.getMaTk() : -1;
            if (currentUserId > 0) {
                vm.loadSuKienSapThamGia(currentUserId);
            }
        });
        adapter.setListener(new SuKienSapToiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SuKien sk) {
                vm.setSk(sk);
                Navigation.findNavController(view).navigate(R.id.chiTietSuKienFragment);
            }

            @Override
            public void onCancel(SuKien sk) {
                handleCancel(vm, sk);
            }
        });

        java.util.Set<Integer> ids = new java.util.HashSet<>();
        androidx.lifecycle.Observer<java.util.List<com.example.eventhub.Model.SuKien>> markRegistered = list -> {
            ids.clear();
            if (vm.getListSKSapThamGia().getValue() != null) {
                for (com.example.eventhub.Model.SuKien s : vm.getListSKSapThamGia().getValue()) {
                    ids.add(s.getMaSK());
                }
            }
            adapter.setRegistered(ids);
        };
        vm.getListSKSapThamGia().observe(getViewLifecycleOwner(), markRegistered);

        if (type == Type.UPCOMING) {
            tvTitle.setText("Sự kiện sắp tới");
            vm.getListSKSapToi().observe(getViewLifecycleOwner(), list -> {
                if (list != null) adapter.update(list);
            });
            vm.load_SK_saptoi();
        } else {
            tvTitle.setText("Sự kiện sắp diễn ra");
            vm.getListSK().observe(getViewLifecycleOwner(), list -> {
                if (list != null) adapter.update(list);
            });
            vm.loadSuKien();
        }
    }
    private void handleCancel(SuKienViewModel vm, SuKien sk) {
        if (sk == null) return;
        if (currentUserId <= 0) {
            Toast.makeText(getContext(), "Vui long dang nhap de huy", Toast.LENGTH_SHORT).show();
            return;
        }
        vm.huySuKien(sk.getMaSK(), currentUserId);
        vm.loadSuKienSapThamGia(currentUserId);
    }
}
