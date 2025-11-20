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


public class FooterFragment extends Fragment {
    private ImageButton btnNotification;
    private ImageButton btnUser;

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

        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).addFragment(new ThongBaoFragment(), true);
                }
            });
        }
        if(btnUser !=null){
            btnUser.setOnClickListener( v ->{
                if(getActivity() instanceof  MainActivity){
                    ((MainActivity) getActivity()).addFragment(new ProfileFragment(),true);
                }
            });
        }
    }
}