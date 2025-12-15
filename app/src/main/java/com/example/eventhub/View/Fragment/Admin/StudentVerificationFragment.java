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

import com.example.eventhub.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentVerificationFragment extends Fragment {

    private static final String ARG_NAME = "arg_student_name";
    private static final String ARG_ID = "arg_student_id";
    private static final String ARG_FACULTY = "arg_faculty";

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

    private String studentName = "Đinh Thu Hương";
    private String studentId = "2311505312213";
    private String faculty = "Công Nghệ Thông Tin";

    public StudentVerificationFragment() {
        // Required empty public constructor
    }

    public static StudentVerificationFragment newInstance(String name, String id, String faculty) {
        StudentVerificationFragment fragment = new StudentVerificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_ID, id);
        args.putString(ARG_FACULTY, faculty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            studentName = getArguments().getString(ARG_NAME, studentName);
            studentId = getArguments().getString(ARG_ID, studentId);
            faculty = getArguments().getString(ARG_FACULTY, faculty);
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
        txtStudentId.setText("Mã Sinh Viên: " + studentId);
        txtFaculty.setText("Khoa: " + faculty);

        imgHeader.setImageResource(R.drawable.backgroud);
        imgAvatar.setImageResource(R.drawable.avatar);
        imgProof.setImageResource(R.drawable.upload);
    }

    private void setupListeners() {
        btnSendComment.setOnClickListener(v -> sendComment());
        btnApprove.setOnClickListener(v -> handleDecision(true));
        btnReject.setOnClickListener(v -> handleDecision(false));
        imgProof.setOnClickListener(v -> Toast.makeText(requireContext(), "Phóng to ảnh minh chứng sau", Toast.LENGTH_SHORT).show());
    }

    private void sendComment() {
        String comment = edtComment.getText().toString().trim();
        if (comment.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(requireContext(), "Đã gửi bình luận", Toast.LENGTH_SHORT).show();
        edtComment.setText("");
    }

    private void handleDecision(boolean approved) {
        String stateText = approved ? "Chấp nhận" : "Từ chối";
        txtDecisionStatus.setText("Trạng thái: " + stateText);
        updateButtonStyles(approved);
        Toast.makeText(requireContext(),
                approved ? "Đã chấp nhận minh chứng" : "Đã từ chối minh chứng",
                Toast.LENGTH_SHORT).show();
    }

    private void updateButtonStyles(boolean approved) {
        if (approved) {
            btnApprove.setBackgroundResource(R.drawable.bg_status_green);
            btnApprove.setTextColor(Color.WHITE);
            btnReject.setBackgroundResource(R.drawable.bg_outline_red);
            btnReject.setTextColor(Color.parseColor("#EA5455"));
        } else {
            btnReject.setBackgroundResource(R.drawable.bg_status_red);
            btnReject.setTextColor(Color.WHITE);
            btnApprove.setBackgroundResource(R.drawable.bg_outline_green);
            btnApprove.setTextColor(Color.parseColor("#28C76F"));
        }
    }
}
