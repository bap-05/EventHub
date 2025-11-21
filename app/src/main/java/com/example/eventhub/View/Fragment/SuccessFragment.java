package com.example.eventhub.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;

public class SuccessFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.success_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn_tieptuc = view.findViewById(R.id.btnBackLogin);
        btn_tieptuc.setOnClickListener(v->{
            ((MainActivity)requireActivity()).frsave = new WellComeFragment();
            ((MainActivity)requireActivity()).addFragment(new LoginFragment() );
        });
    }
}
