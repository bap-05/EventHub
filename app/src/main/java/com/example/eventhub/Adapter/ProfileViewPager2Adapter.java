package com.example.eventhub.Adapter;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity; // <-- Thêm import này
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eventhub.View.Fragment.KhachHang.DaThamGiaFragment;
import com.example.eventhub.View.Fragment.KhachHang.SapThamGiaFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewPager2Adapter extends FragmentStateAdapter {
    private int userId;


    public ProfileViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.userId = userId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SapThamGiaFragment();
            case 1:
                return new DaThamGiaFragment();
            default:
                return new SapThamGiaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}