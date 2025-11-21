package com.example.eventhub.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;


public class FooterFragment extends Fragment implements View.OnClickListener{
    private ImageButton btnNotification, btn_home, btn_setting;
    private ImageButton btnUser;
    public static int id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_footer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNotification = view.findViewById(R.id.btn_notification);
        btnUser = view.findViewById(R.id.btn_user);
        btn_home = view.findViewById(R.id.btn_footer_home);
        btn_setting = view.findViewById(R.id.btn_footer_setting);
        btnUser.setOnClickListener(this);
        btnNotification.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        id = R.id.btn_footer_home;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_notification && id!= R.id.btn_notification )
        {
            ((MainActivity)requireActivity()).addFragment(new ThongBaoFragment());
            btnNotification.setImageResource(R.drawable.bell1);
            btn_home.setImageResource(R.drawable.home1);
            btn_setting.setImageResource(R.drawable.settings);
            btnUser.setImageResource(R.drawable.user);
            id =R.id.btn_notification;
        }
        if(v.getId()==R.id.btn_user && id != R.id.btn_user)
        {
            ((MainActivity)requireActivity()).addFragment(new ProfileFragment());
            btnNotification.setImageResource(R.drawable.bell);
            btn_home.setImageResource(R.drawable.home1);
            btn_setting.setImageResource(R.drawable.settings);
            btnUser.setImageResource(R.drawable.user2);
            id =R.id.btn_user;
        }
        if(v.getId()==R.id.btn_footer_home && id != R.id.btn_footer_home)
        {
            ((MainActivity)requireActivity()).addFragment(new HomeFragment());
            btnNotification.setImageResource(R.drawable.bell);
            btn_home.setImageResource(R.drawable.home);
            btn_setting.setImageResource(R.drawable.settings);
            btnUser.setImageResource(R.drawable.user);
            id =R.id.btn_footer_home;
        }
        if(v.getId()==R.id.btn_footer_setting && id != R.id.btn_footer_setting)
        {
            ((MainActivity)requireActivity()).addFragment(new SettingFragment());
            btnNotification.setImageResource(R.drawable.bell);
            btn_home.setImageResource(R.drawable.home1);
            btn_setting.setImageResource(R.drawable.settings1);
            btnUser.setImageResource(R.drawable.user);
            id =R.id.btn_footer_setting;
        }
    }

}