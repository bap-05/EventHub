package com.example.eventhub.View.Fragment.KhachHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.eventhub.Adapter.ProfileViewPager2Adapter; // <-- Import adapter của ViewPager

import com.example.eventhub.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ProfileViewPager2Adapter viewPager2Adapter;

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container, Bundle savedIntanceState){
        return inflater.inflate (R.layout.fragment_profile,container,false);


    }
    @Override
    public void  onViewCreated(@NonNull View view, @Nullable Bundle savedIntancesState){
        super.onViewCreated(view , savedIntancesState);
        initViews(view);
        setUpEventTabLayout();
    }



    public void   initViews(View view){
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPager2=view.findViewById(R.id.viewPager);
    }
    private void setUpEventTabLayout() {
        viewPager2Adapter = new ProfileViewPager2Adapter(requireActivity());
        viewPager2.setAdapter(viewPager2Adapter);
        new TabLayoutMediator(tabLayout, viewPager2,(tab,position)->{
            if(position==0){
                tab.setText("Sắp tham gia");
            }
            else{
                tab.setText("Đã tham gia");
            }
        }).attach();

    }
}
