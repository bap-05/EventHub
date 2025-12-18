package com.example.eventhub.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eventhub.View.Fragment.Admin.AdminEventListFragment;

public class AdminEventPagerAdapter extends FragmentStateAdapter {

    public AdminEventPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return AdminEventListFragment.newInstance(AdminEventListFragment.Type.ONGOING);
        } else if (position == 2) {
            return AdminEventListFragment.newInstance(AdminEventListFragment.Type.DONE);
        } else {
            return AdminEventListFragment.newInstance(AdminEventListFragment.Type.ALL);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
