package com.example.eventhub.View.Fragment.Admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.AdminUpdateEventRequest;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.AdminEditTaskViewModel;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminEditTaskFragment extends Fragment {

    private EditText edtTenSK, edtMoTa, edtDiaDiem, edtDiaChi;
    private EditText edtNgayBatDau, edtGioBatDau, edtNgayKetThuc, edtGioKetThuc;
    private EditText edtLoaiSK, edtSoLuong, edtDiemCong, edtCoSo, edtTrangThai;
    private LinearLayout layoutUploadImage;
    private ImageView btnBack, imgPosterPreview;
    private Button btnUpdateTask;
    private AdminEditTaskViewModel viewModel;

    private Uri selectedImageUri;
    private SuKien currentEvent;
    private String statusText;

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgPosterPreview.setImageURI(uri);
                    imgPosterPreview.setImageTintList(null);
                    imgPosterPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
    );

    public AdminEditTaskFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Object raw = getArguments().getSerializable("event");
            if (raw instanceof SuKien) {
                currentEvent = (SuKien) raw;
            }
            statusText = getArguments().getString("status_text", "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_edit_task, container, false);

        initViews(view);
        viewModel = new ViewModelProvider(this).get(AdminEditTaskViewModel.class);
        observeViewModel();
        setupClickListeners();
        setupUI(view);
        bindEventData();

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
        edtSoLuong.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtDiemCong = view.findViewById(R.id.edt_diem_cong);
        edtDiemCong.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtCoSo = view.findViewById(R.id.edt_co_so);
        edtDiaDiem = view.findViewById(R.id.edt_dia_diem);
        edtDiaChi = view.findViewById(R.id.edt_dia_chi);
        edtTrangThai = view.findViewById(R.id.edt_trang_thai);

        edtNgayBatDau = view.findViewById(R.id.tv_ngay_bat_dau);
        edtGioBatDau = view.findViewById(R.id.tv_gio_bat_dau);
        edtNgayKetThuc = view.findViewById(R.id.tv_ngay_ket_thuc);
        edtGioKetThuc = view.findViewById(R.id.tv_gio_ket_thuc);

        btnUpdateTask = view.findViewById(R.id.btn_create_task);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        layoutUploadImage.setOnClickListener(v -> mGetContent.launch("image/*"));

        String[] optionsLoai = {"Âm nhạc", "Thể thao", "Chủ nhật xanh"};
        edtLoaiSK.setOnClickListener(v -> showOptionsDialog("Chon loai su kien", optionsLoai, edtLoaiSK));

        String[] optionsCoSo = {"Cở sở 1", "Cở sở 2"};
        edtCoSo.setOnClickListener(v -> showOptionsDialog("Chon co so", optionsCoSo, edtCoSo));

        String[] optionsStatus = {"Sắp diễn ra", "Đang diễn ra", "Đã diễn ra"};
        edtTrangThai.setOnClickListener(v -> showOptionsDialog("Chon trang thai", optionsStatus, edtTrangThai));

        applyDateMask(edtNgayBatDau);
        applyDateMask(edtNgayKetThuc);
        setupCalendarIconTouch(edtNgayBatDau);
        setupCalendarIconTouch(edtNgayKetThuc);

        edtGioBatDau.setOnClickListener(v -> showTimePicker(edtGioBatDau));
        edtGioKetThuc.setOnClickListener(v -> showTimePicker(edtGioKetThuc));

        btnUpdateTask.setOnClickListener(v -> validateAndUpdateEvent());
    }

    private void bindEventData() {
        if (currentEvent == null) return;

        edtTenSK.setText(currentEvent.getTenSK());
        edtMoTa.setText(currentEvent.getMoTa());
        edtLoaiSK.setText(currentEvent.getLoaiSuKien());
        edtSoLuong.setText(String.valueOf(currentEvent.getSoLuongGioiHan()));
        edtDiemCong.setText(String.valueOf(currentEvent.getDiemCong()));
        edtCoSo.setText(currentEvent.getCoSo());
        edtDiaDiem.setText(currentEvent.getDiaDiem());
        edtDiaChi.setText(currentEvent.getDiaDiem());

        setDateTimeFields(currentEvent.getThoiGianBatDau(), edtNgayBatDau, edtGioBatDau);
        setDateTimeFields(currentEvent.getThoiGianKetThuc(), edtNgayKetThuc, edtGioKetThuc);

        if (!TextUtils.isEmpty(currentEvent.getPoster())) {
            Glide.with(this)
                    .load(currentEvent.getPoster())
                    .placeholder(R.drawable.bg_dashed_border)
                    .into(imgPosterPreview);
            imgPosterPreview.setImageTintList(null);
            imgPosterPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        String displayStatus = !TextUtils.isEmpty(statusText)
                ? statusText
                : normalizeStatusLabel(currentEvent.getTrangThai());
        if (!TextUtils.isEmpty(displayStatus)) {
            edtTrangThai.setText(displayStatus);
        }
    }

    private void validateAndUpdateEvent() {
        if (currentEvent == null) {
            Toast.makeText(getContext(), "Loi du lieu su kien", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedImageUri == null && TextUtils.isEmpty(currentEvent.getPoster())) {
            Toast.makeText(getContext(), "Vui long chon hinh anh su kien", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFieldEmpty(edtTenSK, "Ten su kien")) return;
        if (isFieldEmpty(edtMoTa, "Mo ta su kien")) return;
        if (isFieldEmpty(edtLoaiSK, "Loai su kien")) return;
        if (isFieldEmpty(edtSoLuong, "So luong")) return;
        if (isFieldEmpty(edtDiemCong, "Diem cong")) return;
        if (isFieldEmpty(edtCoSo, "Co so")) return;
        if (isFieldEmpty(edtNgayBatDau, "Ngay bat dau")) return;
        if (isFieldEmpty(edtGioBatDau, "Gio bat dau")) return;
        if (isFieldEmpty(edtNgayKetThuc, "Ngay ket thuc")) return;
        if (isFieldEmpty(edtGioKetThuc, "Gio ket thuc")) return;
        if (isFieldEmpty(edtTrangThai, "Trang thai")) return;

        File posterFile = selectedImageUri != null ? getFileFromUri(selectedImageUri) : null;
        AdminUpdateEventRequest request = AdminUpdateEventRequest.fromForm(
                currentEvent.getMaSK(),
                posterFile,
                edtTenSK.getText().toString(),
                edtMoTa.getText().toString(),
                edtLoaiSK.getText().toString(),
                edtSoLuong.getText().toString(),
                edtDiemCong.getText().toString(),
                edtCoSo.getText().toString(),
                edtDiaDiem.getText().toString(),
                edtNgayBatDau.getText().toString(),
                edtGioBatDau.getText().toString(),
                edtNgayKetThuc.getText().toString(),
                edtGioKetThuc.getText().toString(),
                edtTrangThai.getText().toString()
        );
        viewModel.updateEvent(request);
    }

    private void observeViewModel() {
        viewModel.getErr().observe(getViewLifecycleOwner(), err -> {
            if (err != null && !err.isEmpty()) {
                Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUpdated().observe(getViewLifecycleOwner(), updated -> {
            if (Boolean.TRUE.equals(updated)) {
                Toast.makeText(getContext(), "Cap nhat su kien thanh cong", Toast.LENGTH_SHORT).show();
                viewModel.clearUpdated();
                requireActivity().onBackPressed();
            }
        });
    }

    private void setupCalendarIconTouch(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            final int drawableRight = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[drawableRight].getBounds().width())) {
                    showDatePicker(editText);
                    return true;
                }
            }
            return false;
        });
    }

    private void applyDateMask(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d]", "");
                    String cleanC = current.replaceAll("[^\\d]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i <= 4; i += 2) sel++;
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        String ddmmyyyy = "DDMMYYYY";
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : Math.min(year, 2100);
                        cal.set(Calendar.YEAR, year);
                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format(Locale.getDefault(), "%02d%02d%04d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2), clean.substring(2, 4), clean.substring(4, 8));
                    current = clean;
                    et.setText(current);
                    et.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void showOptionsDialog(String title, String[] items, EditText target) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setItems(items, (dialog, which) -> target.setText(items[which]))
                .show();
    }

    private void showDatePicker(EditText editText) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, y, m, d) ->
                editText.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", d, m + 1, y)),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker(EditText editText) {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(requireContext(), (view, h, min) ->
                editText.setText(String.format(Locale.getDefault(), "%02d:%02d", h, min)),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    private boolean isFieldEmpty(EditText editText, String fieldName) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError("Vui long nhap/chon " + fieldName);
            editText.requestFocus();
            return true;
        }
        return false;
    }

    private void setDateTimeFields(String raw, EditText dateField, EditText timeField) {
        if (TextUtils.isEmpty(raw)) return;
        try {
            SimpleDateFormat src = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date d = src.parse(raw);
            if (d != null) {
                dateField.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(d));
                timeField.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(d));
                return;
            }
        } catch (ParseException ignored) {
        }
        String[] parts = raw.split(" ");
        if (parts.length >= 2) {
            dateField.setText(parts[0]);
            timeField.setText(parts[1].substring(0, Math.min(5, parts[1].length())));
        }
    }

    private String normalizeStatusLabel(String raw) {
        if (raw == null) return "";
        String lower = raw.toLowerCase(Locale.getDefault());
        if (lower.contains("sap")) return "Sap dien ra";
        if (lower.contains("dang")) return "Dang dien ra";
        if (lower.contains("da")) return "Da dien ra";
        return raw;
    }

    private File getFileFromUri(Uri uri) {
        try {
            File file = new File(requireContext().getCacheDir(), "temp_poster.jpg");
            java.io.InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            java.io.FileOutputStream outputStream = new java.io.FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, read);
            outputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard();
                v.clearFocus();
                return false;
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) setupUI(((ViewGroup) view).getChildAt(i));
        }
    }

    private void hideSoftKeyboard() {
        View focusView = requireActivity().getCurrentFocus();
        if (focusView != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }
}
