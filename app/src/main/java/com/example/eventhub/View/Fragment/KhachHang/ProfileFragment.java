package com.example.eventhub.View.Fragment.KhachHang;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.eventhub.Adapter.ProfileViewPager2Adapter;
import com.example.eventhub.R;
import com.example.eventhub.View.CaptureActivityPortrait;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ProfileFragment extends Fragment {

    private enum PendingAction { NONE, SCAN, CAMERA }

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ProfileViewPager2Adapter viewPager2Adapter;
    private ImageButton imgbtnQr;
    private PendingAction pendingAction = PendingAction.NONE;

    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    if (pendingAction == PendingAction.CAMERA) {
                        openCameraInternal();
                    } else {
                        startScanner();
                    }
                } else {
                    Toast.makeText(getContext(), "Cần quyền camera để tiếp tục", Toast.LENGTH_SHORT).show();
                }
                pendingAction = PendingAction.NONE;
            });

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Đã hủy quét", Toast.LENGTH_SHORT).show();
                } else {
                    if ("1".equals(result.getContents())) {
                        Toast.makeText(getContext(), "Đã xác nhận bạn tham gia sự kiện id: 1", Toast.LENGTH_LONG).show();
                        launchCamera();
                    } else {
                        Toast.makeText(getContext(), "Mã QR không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<Void> takePicturePreviewLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
                if (bitmap != null) {
                    Toast.makeText(getContext(), "Đã mở camera và chụp ảnh xem trước.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đã thoát camera hoặc không chụp ảnh.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedIntancesState) {
        super.onViewCreated(view, savedIntancesState);
        initViews(view);
        setUpEventTabLayout();
        setupQrButton();
    }

    private void initViews(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);
        imgbtnQr = view.findViewById(R.id.imgbtn_Qr);
    }

    private void setupQrButton() {
        if (imgbtnQr == null) {
            return;
        }
        imgbtnQr.setOnClickListener(v -> checkCameraPermissionAndStartScanner());
    }

    private void checkCameraPermissionAndStartScanner() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startScanner();
        } else {
            pendingAction = PendingAction.SCAN;
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void startScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Di chuyển camera tới mã QR để quét");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivityPortrait.class);
        qrCodeLauncher.launch(options);
    }

    private void launchCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCameraInternal();
        } else {
            pendingAction = PendingAction.CAMERA;
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openCameraInternal() {
        takePicturePreviewLauncher.launch(null);
    }

    private void setUpEventTabLayout() {
        viewPager2Adapter = new ProfileViewPager2Adapter(requireActivity());
        viewPager2.setAdapter(viewPager2Adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Sắp tham gia");
            } else {
                tab.setText("Đã tham gia");
            }
        }).attach();

    }
}
