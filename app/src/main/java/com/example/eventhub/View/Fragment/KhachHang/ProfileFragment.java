package com.example.eventhub.View.Fragment.KhachHang;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.eventhub.Adapter.ProfileViewPager2Adapter;
import com.example.eventhub.Model.SessionManager;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.ThamGiaSuKien;
import com.example.eventhub.R;
import com.example.eventhub.View.CaptureActivityPortrait;
import com.example.eventhub.View.FileUtils;
import com.example.eventhub.ViewModel.SuKienViewModel;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileFragment extends Fragment {

    private enum PendingAction { NONE, SCAN, CAMERA }
    private CircleImageView avatarProfile;
    private TextView txtTenTK,txtMaSV,txtKhoa,txtdiem;
    private ProgressBar pgbDiem;
    private TaiKhoanViewModel taiKhoanViewModel;
    private SessionManager sessionManager;
    private int currentUserId;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ProfileViewPager2Adapter viewPager2Adapter;
    private ImageButton imgbtnQr;
    private PendingAction pendingAction = PendingAction.NONE;
    private FusedLocationProviderClient fusedLocationClient;
    private Uri currentPhotoUri;
    private Double lastLat;
    private Double lastLon;
    private String lastAddress = "";

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

    private final ActivityResultLauncher<String[]> requestLocationPermissionsLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                Boolean fine = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarse = permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if (Boolean.TRUE.equals(fine) || Boolean.TRUE.equals(coarse)) {
                    fetchLocationThenCamera();
                } else {
                    Toast.makeText(getContext(), "Cần quyền vị trí để gắn lên ảnh", Toast.LENGTH_SHORT).show();
                    openCameraInternal(); // vẫn mở camera nếu từ chối, nhưng không có vị trí
                }
            });

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Đã hủy quét", Toast.LENGTH_SHORT).show();
                } else {
//                    if ("1".equals(result.getContents())) {
//                        Toast.makeText(getContext(), "Đã xác nhận bạn tham gia sự kiện id: 1", Toast.LENGTH_LONG).show();
//                        ensureLocationAndCamera();
//                    } else {
//                        Toast.makeText(getContext(), "Mã QR không hợp lệ", Toast.LENGTH_SHORT).show();
//                    }
                    int qrValue = Integer.parseInt(result.getContents());
                    Log.d("sukien",""+qrValue);
                    ThamGiaSuKien thamGiaSuKien = new ThamGiaSuKien(TaiKhoanViewModel.getTaikhoan().getValue().getMaTk(),qrValue);
                    SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
                    suKienViewModel.timSuKien(thamGiaSuKien);
                }
            });

    private final ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
                if (success && currentPhotoUri != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("EXTRA_PHOTO_URI", currentPhotoUri);
                    if (lastAddress != null && !lastAddress.isEmpty()) {
                        bundle.putString("EXTRA_ADDRESS", lastAddress);
                    }
                    try {
                        Navigation.findNavController(requireView())
                                .navigate(R.id.previewPhotoFragment, bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Lỗi chuyển màn hình", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Đã hủy chụp ảnh", Toast.LENGTH_SHORT).show();
                }
                currentPhotoUri = null;
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taiKhoanViewModel = new ViewModelProvider(this).get(TaiKhoanViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedIntancesState) {
        super.onViewCreated(view, savedIntancesState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        initViews(view);
        SuKienViewModel suKienViewModel = new ViewModelProvider(requireActivity()).get(SuKienViewModel.class);
        suKienViewModel.getSukiencantim().observe(getViewLifecycleOwner(),suKien -> {
            if(suKien!= null)
            {
                ensureLocationAndCamera();

            }
        });
        suKienViewModel.getThongBaoTimSK().observe(getViewLifecycleOwner(),thongbao ->{
            if(thongbao!=null)
            {
                Toast.makeText(getContext(),thongbao,Toast.LENGTH_LONG).show();
            }
        });

        TaiKhoanViewModel.getDiem().observe(getViewLifecycleOwner(),diem ->{
            if(diem!=null)
            {
                pgbDiem.setProgress(diem);
                txtdiem.setText(""+diem);
            }

        });
        taiKhoanViewModel.diemtichluy(TaiKhoanViewModel.getTaikhoan().getValue().getMaTk());
//        sessionManager = SessionManager.getInstance(requireContext());
//        if (!sessionManager.isLoggedIn()) {
//            Toast.makeText(getContext(), "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
//            Navigation.findNavController(view).navigate(R.id.loginFragment);
//            return;
//        }try {
//            String idStr = sessionManager.getUserId();
//            currentUserId = Integer.parseInt(idStr);
//        } catch (NumberFormatException e) {
//            Log.e("ProfileFragment", "Lỗi convert ID: " + e.getMessage());
//            sessionManager.clear();
//            Navigation.findNavController(view).navigate(R.id.loginFragment);
//            return;
//        }
        TaiKhoan currentAccount = TaiKhoanViewModel.getTaikhoan().getValue();
        if (currentAccount != null) {
            currentUserId = currentAccount.getMaTk();
            // (Tuỳ chọn) Gọi API load lại profile để chắc chắn dữ liệu mới nhất
            // taiKhoanViewModel.loadUserProfile(currentUserId);
        } else {
            // Trường hợp ViewModel bị null (rất hiếm nếu App chưa bị kill)
            Toast.makeText(getContext(), "Chưa có thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
        }
        if (avatarProfile != null) {
            avatarProfile.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        }


        setUpEventTabLayout();
        observeViewModel();
        setupQrButton();
    }



    private void initViews(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);
        imgbtnQr = view.findViewById(R.id.imgbtn_Qr);
        avatarProfile = view.findViewById(R.id.avatar_profile);
        txtTenTK = view.findViewById(R.id.textView);
        txtMaSV = view.findViewById(R.id.txtMaSV);
        txtKhoa = view.findViewById(R.id.txtKhoa);
        pgbDiem = view.findViewById(R.id.pgb_Diem);
        txtdiem = view.findViewById(R.id.txt_profile_diem);
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

    private void ensureLocationAndCamera() {
        if (hasLocationPermission()) {
            fetchLocationThenCamera();
        } else {
            requestLocationPermissionsLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void fetchLocationThenCamera() {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            lastLat = location.getLatitude();
                            lastLon = location.getLongitude();
                            lastAddress = reverseGeocode(location);
                        } else {
                            lastLat = null;
                            lastLon = null;
                            lastAddress = "";
                            Toast.makeText(getContext(), "Không lấy được vị trí, vẫn mở camera", Toast.LENGTH_SHORT).show();
                        }
                        openCameraInternal();
                    })
                    .addOnFailureListener(requireActivity(), e -> {
                        lastLat = null;
                        lastLon = null;
                        lastAddress = "";
                        Toast.makeText(getContext(), "Lỗi lấy vị trí, vẫn mở camera", Toast.LENGTH_SHORT).show();
                        openCameraInternal();
                    });
        } catch (SecurityException ex) {
            Toast.makeText(getContext(), "Thiếu quyền vị trí", Toast.LENGTH_SHORT).show();
            openCameraInternal();
        }
    }

    private String reverseGeocode(Location location) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address addr = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <= addr.getMaxAddressLineIndex(); i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(addr.getAddressLine(i));
                }
                return sb.toString();
            }
        } catch (IOException e) {
            // ignore
        }
        return "";
    }

    private void openCameraInternal() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pendingAction = PendingAction.CAMERA;
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
            return;
        }
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "EventHub_Photo_" + System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo captured by EventHub");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, android.os.Environment.DIRECTORY_PICTURES + "/EventHub");
        }
        currentPhotoUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (currentPhotoUri != null) {
            takePictureLauncher.launch(currentPhotoUri);
        } else {
            Toast.makeText(getContext(), "Không tạo được file ảnh", Toast.LENGTH_SHORT).show();
        }
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
    private void observeViewModel() {
        taiKhoanViewModel.getTaikhoan().observe(getViewLifecycleOwner(), taiKhoan -> {
            if(taiKhoan != null){
                currentUserId = taiKhoan.getMaTk();

                txtTenTK.setText(taiKhoan.getHoTen());

                txtMaSV.setText(taiKhoan.getMaSV());
                txtKhoa.setText(taiKhoan.getKhoa());

                if(getContext() != null){
                    Glide.with(getContext())
                            .load(taiKhoan.getAVT())
                            .placeholder(R.drawable.avatar)
                            .error(R.drawable.avatar)
                            .into(avatarProfile);
                }
            }
    });
        taiKhoanViewModel.getErr().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty() && getContext() != null) {
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri ->{
                if(uri !=null){
                    uploadImageToSever(uri);
                }
            }
    );

    private void uploadImageToSever(Uri uri) {
        if(getContext() == null) return;
        TaiKhoan currentAccount = TaiKhoanViewModel.getTaikhoan().getValue();
        if(currentAccount == null) {
            Toast.makeText(getContext(), "Lỗi: Không tìm thấy thông tin tài khoản!", Toast.LENGTH_SHORT).show();
            return;
        }
        int idToUpload = currentAccount.getMaTk();
        String strRealPath = FileUtils.getRealPath(getContext(), uri);
        if(strRealPath == null) {
            Toast.makeText(getContext(), "Lỗi: Không tìm thấy đường dẫn ảnh!", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(strRealPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
        taiKhoanViewModel.uploadAvatar(idToUpload,part);
        Toast.makeText(getContext(), "Đang cập nhật ảnh...", Toast.LENGTH_SHORT).show();
    }
}
