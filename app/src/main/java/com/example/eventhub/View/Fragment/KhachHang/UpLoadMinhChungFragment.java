package com.example.eventhub.View.Fragment.KhachHang;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.eventhub.Model.MinhChung;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;

import java.util.HashMap;
import java.util.Map;

public class UpLoadMinhChungFragment extends Fragment {
    private TextView txt_tensk, txt_thoigian;
    private ImageView img_poster, img_minhchung;
    private Button btn_ql, btn_xacnhan;
    private Uri imageUri;
    private String anhSauUpLoad;
    private boolean isCloudinaryInit = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_up_load_minh_chung, container, false);
        addView(v);
        initCloudinary();
        Bundle args = getArguments();
        if (args != null)
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                // Code cho Android 13+ (Quy định rõ kiểu class là Uri.class để an toàn hơn)
                imageUri = args.getParcelable("anh", Uri.class);
            } else {
                // Code cho Android cũ hơn
                imageUri = args.getParcelable("anh");
            }
        }
        if (imageUri!=null)
            upLoadAnh(imageUri);
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.setThongBaoUpload(null);
        suKienViewModel.getSukiencantim().observe(getViewLifecycleOwner(),suKien -> {
            if(suKien!=null)
            {
                Glide.with(requireView()).load(suKien.getPoster()).into(img_poster);
                txt_tensk.setText(suKien.getTenSK());
                txt_thoigian.setText(suKien.getThoiGianBatDau());
            }
        });
        suKienViewModel.getThongBaoUpload().observe(getViewLifecycleOwner(),tb->{
            if(tb!=null)
            {
                Toast.makeText(requireContext(),tb,Toast.LENGTH_LONG).show();
                suKienViewModel.setSukiencantim(null);
                Navigation.findNavController(v).navigate(R.id.nav_profile);
            }

        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinhChung minhChung = new MinhChung(suKienViewModel.getSukiencantim().getValue().getMaSK(),anhSauUpLoad);
                suKienViewModel.uploadMinhChung(TaiKhoanViewModel.getTaikhoan().getValue().getMaTk(),minhChung);
            }
        });
        btn_ql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suKienViewModel.setSukiencantim(null);
                Navigation.findNavController(v).navigate(R.id.nav_profile);
            }
        });
        return v;
    }

    private void addView(View v) {
        txt_tensk = v.findViewById(R.id.txt_minhchung_tensk);
        txt_thoigian = v.findViewById(R.id.txt_minhchung_thoigian);
        img_minhchung = v.findViewById(R.id.img_minhchung);
        img_poster = v.findViewById(R.id.img_minhchung_poster);
        btn_ql = v.findViewById(R.id.btn_minhchung_ql);
        btn_xacnhan = v.findViewById(R.id.btn_minhchung_xacnhan);
        btn_xacnhan.setEnabled(false);
        btn_xacnhan.setBackgroundResource(R.drawable.btn_outline_gray);
        btn_xacnhan.setTextColor(Color.parseColor("#808080"));
    }
    private void initCloudinary() {
        // Kiểm tra xem đã init chưa để tránh crash app
        try {
            MediaManager.get();
            isCloudinaryInit = true; // Đã init rồi thì thôi
        } catch (IllegalStateException e) {
            // Chưa init thì tiến hành cấu hình
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "dtcxoncos");
            config.put("api_key", "377214999613828");
            config.put("api_secret", "nXlq4zx9OmZ5ybPEdNnDH1Z7ZjM");
            // config.put("secure", "true"); // Để trả về link https

            MediaManager.init(requireContext(), config);
            isCloudinaryInit = true;
        }
    }
    private void upLoadAnh(Uri anh){
        Toast.makeText(requireContext(), "Đang upload...", Toast.LENGTH_SHORT).show();
        MediaManager.get().upload(anh)
                .option("folder","MinhChung")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Log.d("Cloudinary", "Bắt đầu...");
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        if (!isAdded() || getActivity() == null) return;

                        anhSauUpLoad = (String) resultData.get("secure_url");

                        // Dùng requireActivity() để chạy trên luồng chính
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "Upload Xong!", Toast.LENGTH_SHORT).show();
                            Log.d("Link", anhSauUpLoad);
                            Glide.with(requireView()).load(anhSauUpLoad).into(img_minhchung);
                            btn_xacnhan.setEnabled(true);
                            btn_xacnhan.setBackgroundResource(R.drawable.bg_outline_green);
                            btn_xacnhan.setTextColor(Color.parseColor("#28C76F"));
                        });
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Toast.makeText(requireContext(), "Lỗi khi up ảnh", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                    }
                }).dispatch();
    }
}