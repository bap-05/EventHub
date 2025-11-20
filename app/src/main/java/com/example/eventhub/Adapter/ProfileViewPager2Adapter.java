package com.example.eventhub.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity; // <-- Thêm import này
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eventhub.View.Fragment.DaThamGiaFragment;
import com.example.eventhub.View.Fragment.SapThamGiaFragment;
//alo
public class ProfileViewPager2Adapter extends FragmentStateAdapter {

    // SỬA CONSTRUCTOR Ở ĐÂY
    public ProfileViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
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