package com.example.eventhub.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class
MainActivity extends AppCompatActivity {
    public Fragment frsave;
    public BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
// 2. Tìm BottomNavigationView
        bottomNav = findViewById(R.id.bottom_navigation);
        int mauCuaToi = android.graphics.Color.parseColor("#FFFFFFFF");
        bottomNav.setItemActiveIndicatorColor(ColorStateList.valueOf(mauCuaToi));
// 3. QUAN TRỌNG: Liên kết chúng lại với nhau
        NavigationUI.setupWithNavController(bottomNav, navController);
        Gson gson = new Gson();
        SharedPreferences preferences = getSharedPreferences("eventhub_prefs",MODE_PRIVATE);
        String tk = preferences.getString("TaiKhoan", "");
// Lúc này tk không bao giờ bị null nữa, code chạy an toàn
        TaiKhoanViewModel.getTaikhoan().observe(this,taiKhoan -> {
            if(taiKhoan!=null){
                if(taiKhoan.getVaiTro().equals("SinhVien"))
                {
                    bottomNav.getMenu().clear();
                    bottomNav.inflateMenu(R.menu.bottom_nav_menu);
                }
                else
                {
                    bottomNav.getMenu().clear();
                    bottomNav.inflateMenu(R.menu.bottom_nav_menu_admin);
                }
            }
        });
        if(!tk.isEmpty())
        {
            try {
                TaiKhoan taiKhoandn = gson.fromJson(tk, TaiKhoan.class);
                TaiKhoanViewModel.setTaikhoan(taiKhoandn);
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.wellComeFragment, true)
                        .build();
                if (taiKhoandn.getVaiTro().equals("SinhVien"))
                {

                    navController.navigate(R.id.nav_home, null, navOptions);

                }

                else{

                    navController.navigate(R.id.nav_home_admin, null, navOptions);
                }

            } catch (Exception e) {
                // Phòng trường hợp chuỗi JSON bị lỗi format
                e.printStackTrace();
            }
        }


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.wellComeFragment
                    || destination.getId() == R.id.loginFragment || destination.getId() == R.id.forgotPasswordFragment
                    || destination.getId() == R.id.otpVerifyFragment || destination.getId() == R.id.resetPasswordFragment  // Thêm ID màn hình đăng nhập của bạn vào đây
                    || destination.getId() == R.id.successFragment) {
                bottomNav.setVisibility(View.GONE);
            } else {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }



}