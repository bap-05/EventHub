package com.example.eventhub.View.Fragment.Admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.eventhub.R;

import java.util.Calendar;
import java.util.Locale;

public class AdminCreateTaskFragment extends Fragment {

    private EditText edtTenSK, edtMoTa, edtDiaDiem, edtDiaChi;
    private EditText edtNgayBatDau, edtGioBatDau, edtNgayKetThuc, edtGioKetThuc;
    private EditText edtLoaiSK, edtSoLuong, edtDiemCong, edtCoSo;
    private LinearLayout layoutUploadImage;
    private ImageView btnBack, imgPosterPreview;
    private Button btnCreateTask;

    private Uri selectedImageUri;

    // Launcher chọn ảnh
    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgPosterPreview.setImageURI(uri);
                    // Sau khi chọn ảnh, bỏ tint xanh và cho ảnh phủ kín
                    imgPosterPreview.setImageTintList(null);
                    imgPosterPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_create_task, container, false);

        initViews(view);
        setupClickListeners();

        // Cài đặt xử lý xóa focus khi nhấn ra ngoài
        setupUI(view);

        return view;
    }

    private void initViews(View view) {
        btnBack = view.findViewById(R.id.btn_back);
        layoutUploadImage = view.findViewById(R.id.layout_upload_image);
        imgPosterPreview = view.findViewById(R.id.img_poster_preview);

        edtTenSK = view.findViewById(R.id.edt_ten_su_kien);
        edtMoTa = view.findViewById(R.id.edt_mo_ta);
        edtLoaiSK = view.findViewById(R.id.edt_loai_sk);
        edtSoLuong = view.findViewById(R.id.edt_so_luong);
        edtDiemCong = view.findViewById(R.id.edt_diem_cong);
        edtCoSo = view.findViewById(R.id.edt_co_so);
        edtDiaDiem = view.findViewById(R.id.edt_dia_diem);
        edtDiaChi = view.findViewById(R.id.edt_dia_chi);

        edtNgayBatDau = view.findViewById(R.id.tv_ngay_bat_dau);
        edtGioBatDau = view.findViewById(R.id.tv_gio_bat_dau);
        edtNgayKetThuc = view.findViewById(R.id.tv_ngay_ket_thuc);
        edtGioKetThuc = view.findViewById(R.id.tv_gio_ket_thuc);

        btnCreateTask = view.findViewById(R.id.btn_create_task);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        edtLoaiSK.setOnClickListener(v -> showOptionsDialog("Chọn loại", new String[]{"Học thuật", "Thể thao", "Văn nghệ"}, edtLoaiSK));
        edtCoSo.setOnClickListener(v -> showOptionsDialog("Chọn cơ sở", new String[]{"Cơ sở 1", "Cơ sở 2"}, edtCoSo));

        edtNgayBatDau.setOnClickListener(v -> showDatePicker(edtNgayBatDau));
        edtGioBatDau.setOnClickListener(v -> showTimePicker(edtGioBatDau));
        edtNgayKetThuc.setOnClickListener(v -> showDatePicker(edtNgayKetThuc));
        edtGioKetThuc.setOnClickListener(v -> showTimePicker(edtGioKetThuc));

        layoutUploadImage.setOnClickListener(v -> mGetContent.launch("image/*"));

        btnCreateTask.setOnClickListener(v -> validateData());
    }

    // --- LOGIC XỬ LÝ FOCUS VÀ BÀN PHÍM ---
    private void setupUI(View view) {
        // Nếu nhấn vào cái gì không phải EditText thì ẩn bàn phím
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard();
                v.clearFocus();
                return false;
            });
        }

        // Nếu là container (ViewGroup), duyệt qua các con để gán sự kiện
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    private void hideSoftKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void validateData() {
        if (edtTenSK.getText().toString().isEmpty()) {
            edtTenSK.setError("Không được để trống");
            return;
        }
        if (selectedImageUri == null) {
            Toast.makeText(getContext(), "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getContext(), "Dữ liệu sẵn sàng gửi đi!", Toast.LENGTH_SHORT).show();
    }

    private void showOptionsDialog(String title, String[] items, EditText target) {
        new AlertDialog.Builder(requireContext()).setTitle(title).setItems(items, (dialog, which) -> target.setText(items[which])).show();
    }

    private void showDatePicker(EditText editText) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, y, m, d) -> editText.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", d, m + 1, y)), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker(EditText editText) {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(requireContext(), (view, h, min) -> editText.setText(String.format(Locale.getDefault(), "%02d:%02d", h, min)), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }
}