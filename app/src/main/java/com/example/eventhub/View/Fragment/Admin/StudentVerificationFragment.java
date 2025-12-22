package com.example.eventhub.View.Fragment.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.AdminParticipantViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentVerificationFragment extends Fragment {

    private ImageView imgHeader;
    private CircleImageView imgAvatar;
    private ImageView imgProof;
    private TextView txtStudentName;
    private TextView txtStudentId;
    private TextView txtFaculty;
    private TextView txtDecisionStatus;
    private EditText edtComment;
    private Button btnApprove;
    private Button btnReject;
    private ImageButton btnSendComment;

    private String studentName = "";
    private String studentId = "";
    private String faculty = "";
    private String avatarUrl = "";
    private String proofUrl = "";
    private String proofAddress = "";
    private String eventAddress = "";
    private int trangThai = 0;
    private int maSK = 0;
    private int maTK = 0;
    private Boolean defaultApprove = null;

    private AdminParticipantViewModel vm;

    public StudentVerificationFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            studentName = getArguments().getString("arg_student_name", studentName);
            studentId = getArguments().getString("arg_student_id", studentId);
            faculty = getArguments().getString("arg_faculty", faculty);
            avatarUrl = getArguments().getString("arg_avatar", "");
            proofUrl = getArguments().getString("arg_proof", "");
            proofAddress = getArguments().getString("arg_proof_address", "");
            eventAddress = getArguments().getString("arg_event_address", "");
            trangThai = getArguments().getInt("arg_trang_thai", 0);
            maSK = getArguments().getInt("arg_ma_sk", 0);
            maTK = getArguments().getInt("arg_ma_tk", 0);
            if (getArguments().containsKey("arg_default_approve")) {
                defaultApprove = getArguments().getBoolean("arg_default_approve");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(AdminParticipantViewModel.class);
        bindViews(view);
        bindDataToViews();
        setupListeners();
    }

    private void bindViews(View view) {
        imgHeader = view.findViewById(R.id.imgHeader);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgProof = view.findViewById(R.id.imgProof);
        txtStudentName = view.findViewById(R.id.txtStudentName);
        txtStudentId = view.findViewById(R.id.txtStudentId);
        txtFaculty = view.findViewById(R.id.txtFaculty);
        txtDecisionStatus = view.findViewById(R.id.txtDecisionStatus);
        edtComment = view.findViewById(R.id.edtComment);
        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);
        btnSendComment = view.findViewById(R.id.btnSendComment);
    }

    private void bindDataToViews() {
        txtStudentName.setText(studentName);
        txtStudentId.setText("Ma Sinh Vien: " + studentId);
        txtFaculty.setText("Khoa: " + faculty);

        Glide.with(this).load(avatarUrl).placeholder(R.drawable.avatar).error(R.drawable.avatar).into(imgAvatar);
        Glide.with(this).load(proofUrl).placeholder(R.drawable.upload).error(R.drawable.upload).into(imgProof);
        imgHeader.setImageResource(R.drawable.backgroud);

        if (defaultApprove != null) {
            updateButtonStyles(defaultApprove);
        } else {
            updateButtonStyles(trangThai == 2);
        }
        txtDecisionStatus.setText(statusLabel(trangThai));
    }

    private void setupListeners() {
        btnSendComment.setOnClickListener(v -> sendCommentOnly());
        btnApprove.setOnClickListener(v -> handleDecision(true));
        btnReject.setOnClickListener(v -> handleDecision(false));
        imgProof.setOnClickListener(v -> Toast.makeText(requireContext(), "Phóng to ảnh minh chứng sau", Toast.LENGTH_SHORT).show());
    }

    private void sendCommentOnly() {
        String comment = edtComment.getText().toString().trim();
        if (comment.isEmpty()) {
            Toast.makeText(requireContext(), "Vui long nhap binh luan", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(requireContext(), "Da gui binh luan", Toast.LENGTH_SHORT).show();
    }

    private void handleDecision(boolean approved) {
        if (maSK == 0 || maTK == 0) {
            Toast.makeText(requireContext(), "Thieu ma su kien/ma tai khoan", Toast.LENGTH_SHORT).show();
            return;
        }
        String comment = edtComment.getText().toString().trim();
        int trangThaiMoi = approved ? 2 : 3;
        if (!approved && comment.isEmpty()) {
            Toast.makeText(requireContext(), "Nhap ly do tu choi", Toast.LENGTH_SHORT).show();
            return;
        }
        vm.updateStatus(maSK, maTK, trangThaiMoi, comment);
        txtDecisionStatus.setText(statusLabel(trangThaiMoi));
        updateButtonStyles(approved);
        if (!approved) {
            edtComment.setText(comment);
        }
        Toast.makeText(requireContext(), approved ? "Da phe duyet" : "Da tu choi", Toast.LENGTH_SHORT).show();
        requireActivity().runOnUiThread(() -> {
            getParentFragmentManager().setFragmentResult("participant_updated", new Bundle());
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
        });
    }

    private void updateButtonStyles(boolean approved) {
        if (approved) {
            btnApprove.setBackgroundResource(R.drawable.bg_status_green);
            btnApprove.setTextColor(Color.WHITE);
            btnReject.setBackgroundResource(R.drawable.bg_outline_red);
            btnReject.setTextColor(Color.parseColor("#EA5455"));
            txtDecisionStatus.setText("Trang thai: Chap nhan");
        } else {
            btnReject.setBackgroundResource(R.drawable.bg_status_red);
            btnReject.setTextColor(Color.WHITE);
            btnApprove.setBackgroundResource(R.drawable.bg_outline_green);
            btnApprove.setTextColor(Color.parseColor("#28C76F"));
            txtDecisionStatus.setText("Trang thai: Tu choi");
        }
    }

    private String statusLabel(int st) {
        switch (st) {
            case 2: return "Trang thai: Da duyet";
            case 3: return "Trang thai: Tu choi";
            case 1: return "Trang thai: Cho duyet";
            default: return "Trang thai: Chua check-in";
        }
    }
}
