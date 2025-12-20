package com.example.eventhub.View.Fragment.KhachHang;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventhub.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Đổi tên class thành XemTruocAnhFragment
public class PreviewPhotoFragment extends Fragment {

    // Đổi tên các hằng số (Key)
    public static final String KHOA_URI_ANH = "EXTRA_PHOTO_URI";

    public static final String KHOA_DIA_CHI = "EXTRA_ADDRESS";

    // Đổi tên biến View
    private ImageView imgXemTruoc;
    private TextView tvHienThiDiaChi;
    private Button btnLuuAnh;

    // Đổi tên biến dữ liệu
    private Uri uriAnh;
    private String chuoiDiaChi = "";
    private Bitmap anhDaXuLy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Chỉ inflate View, KHÔNG xử lý Navigation ở đây để tránh lỗi Crash
        return inflater.inflate(R.layout.fragment_preview_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Ánh xạ View
        imgXemTruoc = view.findViewById(R.id.img_preview);
        tvHienThiDiaChi = view.findViewById(R.id.tv_address_overlay);
        btnLuuAnh = view.findViewById(R.id.btn_save_photo);

        // 2. Nhận dữ liệu
        if (getArguments() != null) {
            uriAnh = getArguments().getParcelable(KHOA_URI_ANH);
            chuoiDiaChi = getArguments().getString(KHOA_DIA_CHI);
        }

        // 3. Kiểm tra dữ liệu (Làm ở đây mới an toàn cho Navigation)
        if (uriAnh == null) {
            Toast.makeText(getContext(), "Lỗi: Không nhận được ảnh", Toast.LENGTH_SHORT).show();
            // Quay lại màn hình trước đó
            Navigation.findNavController(view).popBackStack();
            return;
        }

        // 4. Xử lý ảnh (Load và Xoay)
        try {
            anhDaXuLy = taiVaXoayAnh(uriAnh);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (anhDaXuLy == null) {
            Toast.makeText(getContext(), "Lỗi: Không đọc được file ảnh", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).popBackStack();
            return;
        }

        // 5. Vẽ địa chỉ lên ảnh
        if (chuoiDiaChi == null || chuoiDiaChi.isEmpty()) {
            tvHienThiDiaChi.setText("Không có vị trí");
        } else {
            tvHienThiDiaChi.setText(chuoiDiaChi);
            anhDaXuLy = veDiaChiLenAnh(anhDaXuLy, chuoiDiaChi);
        }

        // Hiển thị ảnh
        imgXemTruoc.setImageBitmap(anhDaXuLy);

        // 6. Sự kiện Lưu ảnh và Chuyển màn hình
        btnLuuAnh.setOnClickListener(v -> {
            // Lưu ảnh vào thư viện
            Uri resultUri = luuAnhVaoThuVien(anhDaXuLy);

            if (resultUri != null) {
                // Hiển thị lại ảnh từ Uri mới (nếu cần)
                // imgXemTruoc.setImageURI(resultUri);
                Log.d("KetQua", "File đã lưu tại: " + resultUri.toString());

                // Đóng gói dữ liệu để gửi sang màn hình Upload
                Bundle bundle = new Bundle();

                // --- LƯU Ý QUAN TRỌNG ---
                // Key "anh" này phải khớp với key bên Fragment nhận (UploadFragment)
                // Bên kia bạn phải dùng bundle.getParcelable("anh")
                bundle.putParcelable("anh", resultUri);
                // Điều hướng
                Navigation.findNavController(requireView())
                        .navigate(R.id.upLoadMinhChungFragment, bundle);
            }
        });
    }
    // Hàm tải và xoay ảnh (loadAndRotate)
    private Bitmap taiVaXoayAnh(Uri duongDanAnh) {
        try {
            // Đọc thông tin hướng xoay (orientation)
            int gocXoay = 0;
            try (InputStream luongDauVaoExif = requireActivity().getContentResolver().openInputStream(duongDanAnh)) {
                if (luongDauVaoExif != null) {
                    ExifInterface thongTinExif = new ExifInterface(luongDauVaoExif);
                    int huong = thongTinExif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    gocXoay = chuyenExifSangDo(huong);
                }
            }

            // Cấu hình giải mã ảnh
            BitmapFactory.Options tuyChon = new BitmapFactory.Options();
            tuyChon.inPreferredConfig = Bitmap.Config.ARGB_8888;

            Bitmap anhGoc;
            try (InputStream luongDauVaoAnh = requireActivity().getContentResolver().openInputStream(duongDanAnh)) {
                anhGoc = BitmapFactory.decodeStream(luongDauVaoAnh, null, tuyChon);
            }

            if (anhGoc == null) return null;
            if (gocXoay == 0) return anhGoc;

            // Xoay ảnh nếu cần
            Matrix maTran = new Matrix();
            maTran.postRotate(gocXoay);
            return Bitmap.createBitmap(anhGoc, 0, 0, anhGoc.getWidth(), anhGoc.getHeight(), maTran, true);

        } catch (IOException e) {
            Log.e("XemTruocAnh", "Lỗi tải và xoay ảnh", e);
            return null;
        }
    }

    // Hàm chuyển đổi mã Exif sang độ (exifToDegrees)
    private int chuyenExifSangDo(int huongExif) {
        switch (huongExif) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }

    // Hàm vẽ địa chỉ lên ảnh (drawAddress)
    private Bitmap veDiaChiLenAnh(Bitmap anhNguon, String diaChi) {
        // Tạo bản sao có thể chỉnh sửa (mutable)
        Bitmap anhCoTheSua = anhNguon.copy(Bitmap.Config.ARGB_8888, true);
        int chieuRongAnh = anhCoTheSua.getWidth();
        int chieuCaoAnh = anhCoTheSua.getHeight();
        Canvas bangVe = new Canvas(anhCoTheSua);
        float coChu = chieuRongAnh * 0.04f;
        if (coChu < 30f) coChu = 30f;
        // --- SỬA Ở ĐÂY: Dùng TextPaint thay vì Paint ---
        TextPaint butVe = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        butVe.setColor(android.graphics.Color.WHITE);
        butVe.setTextSize(coChu);
        butVe.setShadowLayer(coChu * 0.3f, coChu * 0.1f, coChu * 0.1f, android.graphics.Color.BLACK);
        int leTraiPhai = (int) (chieuRongAnh * 0.03f); // Cách lề 3%
        int chieuRongKhungChu = chieuRongAnh - (leTraiPhai * 2); // Chiều rộng cho phép của chữ

        // 5. Tạo StaticLayout (Đây là thằng giúp tự xuống dòng)
        StaticLayout boCucChu;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // Code cho Android 6.0 trở lên (Cách mới chuẩn hơn)
            boCucChu = android.text.StaticLayout.Builder.obtain(diaChi, 0, diaChi.length(), butVe, chieuRongKhungChu)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL) // Căn trái
                    .setLineSpacing(0f, 1.0f)
                    .setIncludePad(false)
                    .build();
        } else {
            // Code cho Android cũ hơn (Deprecated nhưng vẫn chạy)
            boCucChu = new StaticLayout(diaChi, butVe, chieuRongKhungChu,
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
        float chieuCaoKhoiChu = boCucChu.getHeight();
        float toaDoX = leTraiPhai;
        float toaDoY = chieuCaoAnh - chieuCaoKhoiChu - (chieuCaoAnh * 0.03f);

        if (toaDoY < 0) toaDoY = 0;

        bangVe.save();
        bangVe.translate(toaDoX, toaDoY);
        boCucChu.draw(bangVe);
        bangVe.restore();

        return anhCoTheSua;
    }

    // Hàm lưu ảnh (saveBitmap)
    private Uri luuAnhVaoThuVien(Bitmap anhBitmap) {
        if (anhBitmap == null) {
            Toast.makeText(requireContext(), "Ảnh không hợp lệ", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Tạo tên file theo thời gian
        String dauThoiGian = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String tenTep = "EventHub_" + dauThoiGian + ".jpg";

        ContentValues giaTri = new ContentValues();
        giaTri.put(MediaStore.Images.Media.DISPLAY_NAME, tenTep);
        giaTri.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        // Xử lý cho Android 10 trở lên (Q)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            giaTri.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "EventHub");
            giaTri.put(MediaStore.Images.Media.IS_PENDING, 1); // Đánh dấu đang ghi
        }

        // Tạo URI trong bộ nhớ
        Uri uriDaLuu = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, giaTri);

        if (uriDaLuu == null) {
            Toast.makeText(requireContext(), "Không tạo được file lưu", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Ghi dữ liệu ảnh vào file
        try (OutputStream luongGhi = requireActivity().getContentResolver().openOutputStream(uriDaLuu)) {
            anhBitmap.compress(Bitmap.CompressFormat.JPEG, 90, luongGhi);

            // Cập nhật trạng thái đã ghi xong (cho Android Q+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                giaTri.clear();
                giaTri.put(MediaStore.Images.Media.IS_PENDING, 0);
                requireActivity().getContentResolver().update(uriDaLuu, giaTri, null, null);
            }
            Toast.makeText(requireContext(), "Đã lưu vào thư viện", Toast.LENGTH_SHORT).show();
            return uriDaLuu;
        } catch (IOException e) {
            Log.e("XemTruocAnh", "Lỗi lưu ảnh", e);
            Toast.makeText(requireContext(), "Lưu ảnh thất bại", Toast.LENGTH_SHORT).show();
            // Xóa file hỏng nếu cần
            requireActivity().getContentResolver().delete(uriDaLuu, null, null);
            return null;
        }
    }
}