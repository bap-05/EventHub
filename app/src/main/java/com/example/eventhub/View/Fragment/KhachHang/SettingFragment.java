package com.example.eventhub.View.Fragment.KhachHang;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;


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
            editor.clear();
            editor.apply();
            TaiKhoanViewModel.setTaikhoan(null);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true) // Xóa sạch Stack
                    .build();
            Navigation.findNavController(view).navigate(R.id.wellComeFragment,null,navOptions);

        });
        return v;
    }

    private void addView(View v) {
        btn_dangxuat = v.findViewById(R.id.btn_setting_dx);
    }
}