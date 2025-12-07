package com.example.eventhub.View.Fragment.KhachHang;

import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FooterFragment extends Fragment {
    private BottomNavigationView bottomNav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_footer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v1, windowInsets) -> {
            // Lấy thông số của các thanh hệ thống (Status bar, Nav bar)
            Insets insets = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).toPlatformInsets();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), insets.bottom);
            }

            return windowInsets;
        });
        bottomNav = view.findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item->{
             item.getItemId();
            if(item.getItemId() == R.id.nav_home )
            {
                ((MainActivity)requireActivity()).frsave = new HomeFragment();
                ((MainActivity)requireActivity()).addFragment(((MainActivity)requireActivity()).frsave );
                return true;
            }
            if(item.getItemId() == R.id.nav_tb)
            {
                ((MainActivity)requireActivity()).addFragment(new ThongBaoFragment());
                return true;
            }
            if(item.getItemId() == R.id.nav_setting)
            {
                ((MainActivity)requireActivity()).addFragment(new SettingFragment());
                return true;
            }
            if(item.getItemId() == R.id.nav_profile)
            {
                ((MainActivity)requireActivity()).addFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }


}