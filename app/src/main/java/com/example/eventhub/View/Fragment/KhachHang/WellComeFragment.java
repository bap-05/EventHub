package com.example.eventhub.View.Fragment.KhachHang;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;

public class WellComeFragment extends Fragment {
    private LinearLayout btn_next;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_well_come, container, false);
        btn_next = v.findViewById(R.id.btn_wellcome_buttonContinue);
        btn_next.setOnClickListener(view->{
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });
        return v;
    }
}