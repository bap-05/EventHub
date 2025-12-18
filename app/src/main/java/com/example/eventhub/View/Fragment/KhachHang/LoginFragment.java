package com.example.eventhub.View.Fragment.KhachHang;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.eventhub.Model.SessionManager;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoan;
import com.example.eventhub.Model.TaiKhoanDN;
import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;
import com.example.eventhub.ViewModel.TaiKhoanViewModel;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {

    static final String AUTH_PREFS = "eventhub_prefs";
    static final String KEY_REMEMBER = "remember";
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";
    private SessionManager sessionManager;
    private EditText emailInput;
    private EditText passwordInput;
    private View loginButton;
    private Switch rememberSwitch;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passInput);
        loginButton = view.findViewById(R.id.loginBtn);
        rememberSwitch = view.findViewById(R.id.switchRemember);
        TextView forgotPassword = view.findViewById(R.id.forgotPass);

        preferences = requireContext().getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE);

        TaiKhoanViewModel taiKhoanViewModel = new ViewModelProvider(requireActivity()).get(TaiKhoanViewModel.class);
        taiKhoanViewModel.getTaikhoan().observe(getViewLifecycleOwner(), taiKhoan -> {
            if (taiKhoan != null) {
                // Lưu trạng thái đăng nhập (SharedPreferences...)
                handleRememberState(taiKhoan);
                Navigation.findNavController(requireView()).navigate(R.id.nav_home);
            }
        });

        taiKhoanViewModel.getErr().observe(getViewLifecycleOwner(), errMessage -> {
            if (errMessage != null && !errMessage.isEmpty()) {
                Toast.makeText(getContext(), errMessage, Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(v -> {
            if(emailInput.getText().toString().isEmpty() && passwordInput.getText().toString().isEmpty())
            {
                Toast.makeText(view.getContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
            }
            else {
                TaiKhoanDN taiKhoanDN = new TaiKhoanDN(emailInput.getText().toString().trim(),passwordInput.getText().toString().trim());
                taiKhoanViewModel.ktraLogin(taiKhoanDN);
            }
        });
        forgotPassword.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.forgotPasswordFragment);
        });
    }
    private void handleRememberState(TaiKhoan taiKhoan) {
        SharedPreferences.Editor editor = preferences.edit();
        if (rememberSwitch.isChecked()) {
            Gson gson = new Gson();
            String tk = gson.toJson(taiKhoan);
            editor.putString("TaiKhoan", tk);
            editor.putBoolean(KEY_REMEMBER,true);
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.clear();
        }
        editor.apply();
    }


}
