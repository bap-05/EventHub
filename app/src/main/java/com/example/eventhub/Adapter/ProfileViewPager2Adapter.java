package com.example.eventhub.Adapter;

import android.os.Bundle;
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


    public ProfileViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, int userId) {
        super(fragmentActivity);
        this.userId = userId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Tạo Bundle để đóng gói userId gửi đi
        Bundle args = new Bundle();
        args.putInt("USER_ID", userId); // Key "USER_ID" này phải khớp với Fragment con

        Fragment fragment;
        if (position == 0) {
            fragment = new SapThamGiaFragment();
        } else {
            fragment = new DaThamGiaFragment();
        }

        // Gắn Bundle vào Fragment
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}