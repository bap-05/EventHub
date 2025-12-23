package com.example.eventhub.View.Fragment.Admin;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.AdminParticipantAdapter;
import com.example.eventhub.Model.AdminParticipant;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.AdminParticipantViewModel;

public class AdminStudentListFragment extends Fragment {

    private static final String ARG_MA_SK = "ARG_MA_SK";
    private static final String ARG_EVENT_TITLE = "ARG_EVENT_TITLE";
    private static final String ARG_EVENT_STATUS = "ARG_EVENT_STATUS";
    private static final String ARG_COUNT = "ARG_COUNT";

    public static AdminStudentListFragment newInstance(int maSK, String title, String status, String countText) {
        AdminStudentListFragment f = new AdminStudentListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_MA_SK, maSK);
        b.putString(ARG_EVENT_TITLE, title);
        b.putString(ARG_EVENT_STATUS, status);
        b.putString(ARG_COUNT, countText);
        f.setArguments(b);
        return f;
    }

    private int maSK;
    private String eventTitle = "";
    private String eventStatus = "";
    private String countText = "";
    private AdminParticipantViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_student_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            maSK = getArguments().getInt(ARG_MA_SK, 0);
            if (maSK == 0) {
                maSK = getArguments().getInt("maSK", 0);
            }
            eventTitle = getArguments().getString(ARG_EVENT_TITLE, "");
            if (eventTitle.isEmpty()) {
                eventTitle = getArguments().getString("eventTitle", "");
            }
            eventStatus = getArguments().getString(ARG_EVENT_STATUS, "");
            if (eventStatus.isEmpty()) {
                eventStatus = getArguments().getString("eventStatus", "");
            }
            countText = getArguments().getString(ARG_COUNT, "");
            if (countText.isEmpty()) {
                countText = getArguments().getString("countText", "");
            }
        }
        TextView tvTitle = view.findViewById(R.id.tv_event_title);
        TextView tvInfo = view.findViewById(R.id.tv_event_info);
        tvTitle.setText("Danh sach: " + eventTitle);
        tvInfo.setText(countText);

        RecyclerView rv = view.findViewById(R.id.rv_participants);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        boolean allowApprove = "done".equalsIgnoreCase(eventStatus) || "Da dien ra".equalsIgnoreCase(eventStatus);

        vm = new ViewModelProvider(this).get(AdminParticipantViewModel.class);
        AdminParticipantAdapter adapter = new AdminParticipantAdapter(new AdminParticipantAdapter.OnParticipantAction() {
            @Override
            public void onSelect(AdminParticipant p) {
                openVerification(p);
            }

            @Override
            public void onApprove(AdminParticipant p) {
                openVerification(p, true);
            }

            @Override
            public void onReject(AdminParticipant p) {
                openVerification(p, false);
            }

            @Override
            public void onReopen(AdminParticipant p) {
                vm.updateStatus(maSK, p.getMaTK(), 1, null);
            }
        });
        rv.setAdapter(adapter);

        vm.getErr().observe(getViewLifecycleOwner(), err -> {
            if (err != null && !err.isEmpty()) {
                Toast.makeText(getContext(), "Loi tai danh sach: " + err, Toast.LENGTH_SHORT).show();
            }
        });
        vm.getParticipants().observe(getViewLifecycleOwner(), list -> adapter.setData(list, allowApprove));

        getParentFragmentManager().setFragmentResultListener("participant_updated", getViewLifecycleOwner(), (requestKey, bundle) -> {
            if (maSK != 0) vm.load(maSK);
        });

        if (maSK != 0) {
            vm.load(maSK);
        }
    }

    private void openVerification(AdminParticipant p) {
        openVerification(p, null);
    }

    private void openVerification(AdminParticipant p, Boolean defaultApprove) {
        if (getActivity() == null) return;
        Bundle args = new Bundle();
        args.putString("arg_student_name", p.getHoTen());
        args.putString("arg_student_id", p.getMaSV());
        args.putString("arg_faculty", p.getKhoa() + " - " + p.getLop());
        args.putString("arg_avatar", p.getAVT());
        args.putString("arg_proof", p.getAnhMinhChung());
        args.putString("arg_proof_address", p.getDiaDiemMinhChung());
        args.putString("arg_event_address", p.getDiaDiemSK());
        args.putInt("arg_trang_thai", p.getTrangThaiMinhChung());
        args.putInt("arg_ma_sk", maSK);
        args.putInt("arg_ma_tk", p.getMaTK());
        if (defaultApprove != null) {
            args.putBoolean("arg_default_approve", defaultApprove);
        }
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.studentVerificationFragment, args);
    }
}
