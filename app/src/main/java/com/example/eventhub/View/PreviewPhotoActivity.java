package com.example.eventhub.View;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.example.eventhub.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PreviewPhotoActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO_URI = "PHOTO_URI";
    public static final String EXTRA_LAT = "EXTRA_LAT";
    public static final String EXTRA_LON = "EXTRA_LON";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";

    private ImageView imgPreview;
    private TextView tvAddress;
    private Button btnSave;

    private Uri photoUri;
    private String addressString = "";
    private Bitmap processedBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);

        imgPreview = findViewById(R.id.img_preview);
        tvAddress = findViewById(R.id.tv_address_overlay);
        btnSave = findViewById(R.id.btn_save_photo);

        photoUri = getIntent().getParcelableExtra(EXTRA_PHOTO_URI);
        addressString = getIntent().getStringExtra(EXTRA_ADDRESS);

        if (photoUri == null) {
            Toast.makeText(this, "Không nhận được ảnh", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        processedBitmap = loadAndRotate(photoUri);
        if (processedBitmap == null) {
            Toast.makeText(this, "Không đọc được ảnh", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Vẽ địa chỉ lên bitmap nếu có
        if (addressString == null || addressString.isEmpty()) {
            tvAddress.setText("Không có vị trí");
        } else {
            tvAddress.setText(addressString);
            processedBitmap = drawAddress(processedBitmap, addressString);
        }
        imgPreview.setImageBitmap(processedBitmap);

        btnSave.setOnClickListener(v -> saveBitmap(processedBitmap));
    }

    private Bitmap loadAndRotate(Uri uri) {
        try {
            // Đọc orientation
            int rotation = 0;
            try (InputStream exifStream = getContentResolver().openInputStream(uri)) {
                if (exifStream != null) {
                    ExifInterface exif = new ExifInterface(exifStream);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    rotation = exifToDegrees(orientation);
                }
            }

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bmp;
            try (InputStream imgStream = getContentResolver().openInputStream(uri)) {
                bmp = BitmapFactory.decodeStream(imgStream, null, opts);
            }
            if (bmp == null) return null;
            if (rotation == 0) return bmp;

            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        } catch (IOException e) {
            Log.e("PreviewPhoto", "loadAndRotate error", e);
            return null;
        }
    }

    private int exifToDegrees(int exifOrientation) {
        switch (exifOrientation) {
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

    private Bitmap drawAddress(Bitmap source, String address) {
        Bitmap mutable = source.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutable);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFFFFFF);
        paint.setTextSize(42f);
        paint.setShadowLayer(6f, 4f, 4f, 0x80000000);

        float x = 32f;
        float y = mutable.getHeight() - 48f;
        canvas.drawText(address, x, y, paint);
        return mutable;
    }

    private void saveBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Toast.makeText(this, "Ảnh không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "EventHub_" + timeStamp + ".jpg";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "EventHub");
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri savedUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (savedUri == null) {
            Toast.makeText(this, "Không lưu được ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        try (OutputStream os = getContentResolver().openOutputStream(savedUri)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                getContentResolver().update(savedUri, values, null, null);
            }
            Toast.makeText(this, "Đã lưu vào thư viện", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            Log.e("PreviewPhoto", "saveBitmap error", e);
            Toast.makeText(this, "Lưu ảnh thất bại", Toast.LENGTH_SHORT).show();
            // Xóa file hỏng nếu cần
            getContentResolver().delete(savedUri, null, null);
        }
    }
}
