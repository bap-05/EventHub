package com.example.eventhub.View.Fragment.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.eventhub.Adapter.AdminEventPagerAdapter;
import com.example.eventhub.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tab_admin_events);
        ViewPager2 viewPager = view.findViewById(R.id.pager_admin_events);

        AdminEventPagerAdapter adapter = new AdminEventPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Tất cả");
            else if (position == 1) tab.setText("Đang diễn ra");
            else tab.setText("Đã diễn ra");
        }).attach();

        super.onViewCreated(view, savedInstanceState);
        Button btnCreateTask = view.findViewById(R.id.btn_create_task);
        btnCreateTask.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.adminCreateTaskFragment);
        });
    }
}
