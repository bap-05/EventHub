package com.example.eventhub.View.Fragment.KhachHang;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;


public class SettingFragment extends Fragment {
    private Button btn_dangxuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        addView(v);
        btn_dangxuat.setOnClickListener(view ->{
            SharedPreferences preferences = requireActivity().getSharedPreferences("eventhub_prefs",view.getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("remember");
            editor.remove("email");
            editor.remove("password");
            editor.apply();
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });
        return v;
    }

    private void addView(View v) {
        btn_dangxuat = v.findViewById(R.id.btn_setting_dx);
    }
}