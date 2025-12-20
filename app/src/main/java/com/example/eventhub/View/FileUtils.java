package com.example.eventhub.View;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    // Hàm này sẽ copy ảnh từ Uri vào thư mục Cache của ứng dụng
    // và trả về đường dẫn của file copy đó.
    public static String getRealPath(Context context, Uri uri) {
        if (uri == null) return null;

        // Tạo file tạm trong thư mục cache
        File file = new File(context.getCacheDir(), getFileName(context, uri));
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            // Trả về đường dẫn tuyệt đối của file tạm
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e("FileUtils", "Lỗi copy file: " + e.getMessage());
            return null;
        }
    }

    // Hàm phụ để lấy tên file gốc (nếu có) hoặc tạo tên ngẫu nhiên
    private static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    // Cố gắng lấy tên file thật
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if(index >= 0) {
                        result = cursor.getString(index);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        // Nếu vẫn không lấy được tên thì đặt tên mặc định
        if (result == null || result.isEmpty()) {
            result = "temp_avatar_" + System.currentTimeMillis() + ".jpg";
        }
        return result;
    }
}