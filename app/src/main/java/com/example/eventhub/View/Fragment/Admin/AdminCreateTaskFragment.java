package com.example.eventhub.View.Fragment.Admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.eventhub.Model.AdminCreateEventRequest;
import com.example.eventhub.Model.SessionManager;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.AdminCreateTaskViewModel;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;


public class AdminCreateTaskFragment extends Fragment {

    private EditText edtTenSK, edtMoTa, edtDiaDiem, edtDiaChi;
    private EditText edtNgayBatDau, edtGioBatDau, edtNgayKetThuc, edtGioKetThuc;
    private EditText edtLoaiSK, edtSoLuong, edtDiemCong, edtCoSo;
    private LinearLayout layoutUploadImage;
    private ImageView btnBack, imgPosterPreview;
    private Button btnCreateTask;
    private SessionManager sessionManager;
    private AdminCreateTaskViewModel viewModel;

    private Uri selectedImageUri;

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

    public AdminCreateTaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_create_task, container, false);

        sessionManager = SessionManager.getInstance(requireContext());
        initViews(view);
        viewModel = new ViewModelProvider(this).get(AdminCreateTaskViewModel.class);
        observeViewModel();
        setupClickListeners();
        setupUI(view);

        return view;
    }

    private void initViews(View view) {
        btnBack = view.findViewById(R.id.btn_back);
        layoutUploadImage = view.findViewById(R.id.layout_upload_image);
        imgPosterPreview = (ImageView) layoutUploadImage.getChildAt(0);

        edtTenSK = view.findViewById(R.id.edt_ten_su_kien);
        edtMoTa = view.findViewById(R.id.edt_mo_ta);
        edtLoaiSK = view.findViewById(R.id.edt_loai_sk);

        edtSoLuong = view.findViewById(R.id.edt_so_luong);
        edtSoLuong.setInputType(InputType.TYPE_CLASS_NUMBER); // T???i ??u ch??? nh???p s???

        edtDiemCong = view.findViewById(R.id.edt_diem_cong);
        edtDiemCong.setInputType(InputType.TYPE_CLASS_NUMBER); // T???i ??u ch??? nh???p s???

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

        String[] optionsLoai = {"V??n ngh???", "H???i th???o", "Ch??? nh???t xanh"};
        edtLoaiSK.setOnClickListener(v -> showOptionsDialog("Ch???n lo???i s??? ki???n", optionsLoai, edtLoaiSK));

        String[] optionsCoSo = {"C?? s??? 1", "C?? s??? 2"};
        edtCoSo.setOnClickListener(v -> showOptionsDialog("Ch???n c?? s???", optionsCoSo, edtCoSo));

        // ??p d???ng m???t n??? dd/mm/yyyy cho nh???p tay
        applyDateMask(edtNgayBatDau);
        applyDateMask(edtNgayKetThuc);

        // B???t s??? ki???n click v??o icon l???ch (b??n ph???i EditText) ????? hi???n Picker
        setupCalendarIconTouch(edtNgayBatDau);
        setupCalendarIconTouch(edtNgayKetThuc);

        edtGioBatDau.setOnClickListener(v -> showTimePicker(edtGioBatDau));
        edtGioKetThuc.setOnClickListener(v -> showTimePicker(edtGioKetThuc));

        layoutUploadImage.setOnClickListener(v -> mGetContent.launch("image/*"));
        btnCreateTask.setOnClickListener(v -> validateAndCreateEvent());
    }

    private void setupCalendarIconTouch(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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

    private void validateAndCreateEvent() {
        if (selectedImageUri == null) {
            Toast.makeText(getContext(), "Vui l??ng ch???n h??nh ???nh s??? ki???n", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isFieldEmpty(edtTenSK, "T??n s??? ki???n")) return;
        if (isFieldEmpty(edtMoTa, "M?? t??? s??? ki???n")) return;
        if (isFieldEmpty(edtLoaiSK, "Lo???i s??? ki???n")) return;
        if (isFieldEmpty(edtSoLuong, "S??? l?????ng")) return;
        if (isFieldEmpty(edtDiemCong, "??i???m c???ng")) return;
        if (isFieldEmpty(edtCoSo, "C?? s???")) return;
        if (isFieldEmpty(edtNgayBatDau, "Ng??y b???t ?????u")) return;
        if (isFieldEmpty(edtGioBatDau, "Gi??? b???t ?????u")) return;
        if (isFieldEmpty(edtNgayKetThuc, "Ng??y k???t th??c")) return;
        if (isFieldEmpty(edtGioKetThuc, "Gi??? k???t th??c")) return;

        callApiCreateEvent();
    }

    private void callApiCreateEvent() {
        File file = getFileFromUri(selectedImageUri);
        if (file == null) return;

        String userId = sessionManager.getUserId();
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(getContext(), "Loi: Khong tim thay ID nguoi dang. Vui long dang nhap lai!", Toast.LENGTH_LONG).show();
            return;
        }

        AdminCreateEventRequest request = AdminCreateEventRequest.fromForm(
                file,
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
                userId
        );
        viewModel.createEvent(request);
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

    private void observeViewModel() {
        viewModel.getErr().observe(getViewLifecycleOwner(), err -> {
            if (err != null && !err.isEmpty()) {
                Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getCreated().observe(getViewLifecycleOwner(), created -> {
            if (Boolean.TRUE.equals(created)) {
                Toast.makeText(getContext(), "T??????o s?????? ki??????n thA??nh cA'ng!", Toast.LENGTH_SHORT).show();
                viewModel.clearCreated();
                getParentFragmentManager().setFragmentResult("admin_event_updated", new Bundle());
                requireActivity().onBackPressed();
            }
        });
    }

    private void showOptionsDialog(String title, String[] items, EditText target) {
        new AlertDialog.Builder(requireContext()).setTitle(title).setItems(items, (dialog, which) -> target.setText(items[which])).show();
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
            editText.setError("Vui l??ng nh???p/ch???n " + fieldName);
            editText.requestFocus();
            return true;
        }
        return false;
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
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }
}


