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


    public ProfileViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return SapThamGiaFragment.newInstance();
        } else {
            return DaThamGiaFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}