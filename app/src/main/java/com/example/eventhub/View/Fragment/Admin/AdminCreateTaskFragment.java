package com.example.eventhub.View.Fragment.Admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.R;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class AdminCreateTaskFragment extends Fragment {

    // Khai báo các thành phần UI
    private EditText edtTenSK, edtMoTa, edtDiaDiem, edtDiaChi;
    private EditText edtNgayBatDau, edtGioBatDau, edtNgayKetThuc, edtGioKetThuc;
    private EditText edtLoaiSK, edtSoLuong, edtDiemCong, edtCoSo;
    private LinearLayout layoutUploadImage;
    private ImageView btnBack, imgPosterPreview;
    private Button btnCreateTask;

    private Uri selectedImageUri; // Lưu URI ảnh đã chọn

    // Launcher để chọn ảnh từ thư viện
    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgPosterPreview.setImageURI(uri);
                    imgPosterPreview.setImageTintList(null); // Bỏ màu tint xanh ban đầu
                    imgPosterPreview.setScaleType(ImageView.ScaleType.CENTER_CROP); // Phủ kín khung
                }
            }
    );

    public AdminCreateTaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_create_task, container, false);

        initViews(view);
        setupClickListeners();
        setupUI(view); // Kích hoạt xóa focus khi chạm ngoài

        return view;
    }

    private void initViews(View view) {
        btnBack = view.findViewById(R.id.btn_back);
        layoutUploadImage = view.findViewById(R.id.layout_upload_image);
        // Lấy ImageView đầu tiên trong layout_upload_image để làm preview
        imgPosterPreview = (ImageView) layoutUploadImage.getChildAt(0);

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

        // Chọn Loại sự kiện
        String[] optionsLoai = {"Học thuật", "Thể thao", "Văn nghệ", "Hội thảo", "Tình nguyện"};
        edtLoaiSK.setOnClickListener(v -> showOptionsDialog("Chọn loại sự kiện", optionsLoai, edtLoaiSK));

        // Chọn Cơ sở
        String[] optionsCoSo = {"Cơ sở 1", "Cơ sở 2"};
        edtCoSo.setOnClickListener(v -> showOptionsDialog("Chọn cơ sở", optionsCoSo, edtCoSo));

        // Picker Ngày/Giờ
        edtNgayBatDau.setOnClickListener(v -> showDatePicker(edtNgayBatDau));
        edtGioBatDau.setOnClickListener(v -> showTimePicker(edtGioBatDau));
        edtNgayKetThuc.setOnClickListener(v -> showDatePicker(edtNgayKetThuc));
        edtGioKetThuc.setOnClickListener(v -> showTimePicker(edtGioKetThuc));

        // Mở thư viện chọn ảnh
        layoutUploadImage.setOnClickListener(v -> mGetContent.launch("image/*"));

        // Nút Thêm sự kiện
        btnCreateTask.setOnClickListener(v -> validateAndCreateEvent());
    }

    // Logic ẩn bàn phím và xóa focus khi nhấn ra ngoài
    private void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard();
                v.clearFocus();
                return false;
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setupUI(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    private void hideSoftKeyboard() {
        View focusView = requireActivity().getCurrentFocus();
        if (focusView != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }

    // Kiểm tra tính hợp lệ của toàn bộ dữ liệu
    private void validateAndCreateEvent() {
        if (selectedImageUri == null) {
            Toast.makeText(getContext(), "Vui lòng chọn hình ảnh sự kiện", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isFieldEmpty(edtTenSK, "Tên sự kiện")) return;
        if (isFieldEmpty(edtMoTa, "Mô tả sự kiện")) return;
        if (isFieldEmpty(edtLoaiSK, "Loại sự kiện")) return;
        if (isFieldEmpty(edtSoLuong, "Số lượng")) return;
        if (isFieldEmpty(edtDiemCong, "Điểm cộng")) return;
        if (isFieldEmpty(edtCoSo, "Cơ sở")) return;
        if (isFieldEmpty(edtNgayBatDau, "Ngày bắt đầu")) return;
        if (isFieldEmpty(edtGioBatDau, "Giờ bắt đầu")) return;

        Toast.makeText(getContext(), "Thông tin hợp lệ! Sẵn sàng kết nối Backend.", Toast.LENGTH_LONG).show();
        // Bước tiếp theo sẽ gọi Retrofit gửi dữ liệu
        callApiCreateEvent();
    }

    private boolean isFieldEmpty(EditText editText, String fieldName) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError("Vui lòng nhập/chọn " + fieldName);
            editText.requestFocus();
            return true;
        }
        return false;
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
    private File getFileFromUri(Uri uri) {
        try {
            // Tạo một file tạm trong bộ nhớ cache của ứng dụng
            File file = new File(requireContext().getCacheDir(), "temp_poster.jpg");
            java.io.InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            java.io.FileOutputStream outputStream = new java.io.FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void callApiCreateEvent() {
        // 1. Khởi tạo API service
        IAPI apiService = ApiClient.getClient().create(IAPI.class);

        // 2. Chuẩn bị file ảnh từ Uri
        File file = getFileFromUri(selectedImageUri);
        if (file == null) {
            Toast.makeText(getContext(), "Không thể xử lý tệp tin ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        // ĐÃ SỬA: File trước, MediaType sau
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part bodyPoster = MultipartBody.Part.createFormData("poster", file.getName(), requestFile);

        // 3. Đóng gói các trường Text (ĐÃ SỬA: String trước, MediaType sau)
        RequestBody rbTen = RequestBody.create(edtTenSK.getText().toString(), MediaType.parse("text/plain"));
        RequestBody rbMoTa = RequestBody.create(edtMoTa.getText().toString(), MediaType.parse("text/plain"));
        RequestBody rbLoai = RequestBody.create(edtLoaiSK.getText().toString(), MediaType.parse("text/plain"));
        RequestBody rbSoLuong = RequestBody.create(edtSoLuong.getText().toString(), MediaType.parse("text/plain"));
        RequestBody rbDiem = RequestBody.create(edtDiemCong.getText().toString(), MediaType.parse("text/plain"));
        RequestBody rbCoSo = RequestBody.create(edtCoSo.getText().toString(), MediaType.parse("text/plain"));
        RequestBody rbDiaDiem = RequestBody.create(edtDiaDiem.getText().toString(), MediaType.parse("text/plain"));

        // Định dạng ngày giờ chuẩn yyyy-MM-dd HH:mm:ss
        String thoiGianBD = formatDateTime(edtNgayBatDau.getText().toString(), edtGioBatDau.getText().toString());
        String thoiGianKT = formatDateTime(edtNgayKetThuc.getText().toString(), edtGioKetThuc.getText().toString());

        RequestBody rbBD = RequestBody.create(thoiGianBD, MediaType.parse("text/plain"));
        RequestBody rbKT = RequestBody.create(thoiGianKT, MediaType.parse("text/plain"));
        RequestBody rbNguoiDang = RequestBody.create("1", MediaType.parse("text/plain")); // Tạm thời để ID = 1

        // 4. Thực thi Request
        apiService.createSuKien(bodyPoster, rbTen, rbMoTa, rbLoai, rbSoLuong, rbDiem, rbCoSo, rbDiaDiem, rbBD, rbKT, rbNguoiDang)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Tạo sự kiện thành công!", Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed(); // Quay lại màn hình quản lý
                        } else {
                            Toast.makeText(getContext(), "Lỗi Server: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private String formatDateTime(String date, String time) {
        String[] parts = date.split("/"); // dd/MM/yyyy
        return parts[2] + "-" + parts[1] + "-" + parts[0] + " " + time + ":00";
    }


}