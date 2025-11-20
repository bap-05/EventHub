package com.example.eventhub.View.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventhub.Model.AuthDemoData;
import com.example.eventhub.R;

public class ResetPasswordFragment extends BaseAuthFragment {

    private static final String ARG_EMAIL = "arg_email";
    private static final String ARG_OTP = "arg_otp";

    private EditText newPasswordInput;
    private EditText confirmPasswordInput;
    private Button updateButton;
    private String email;
    private String otp;

    public static ResetPasswordFragment newInstance(String email, String otp) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_OTP, otp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            email = args.getString(ARG_EMAIL);
            otp = args.getString(ARG_OTP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(otp)) {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
            return;
        }

        newPasswordInput = view.findViewById(R.id.newPass);
        confirmPasswordInput = view.findViewById(R.id.confirmPass);
        updateButton = view.findViewById(R.id.btnUpdate);

        view.findViewById(R.id.backBtn)
                .setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        updateButton.setOnClickListener(v -> updatePassword());
    }

    private void updatePassword() {
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(requireContext(), "Vui long nhap du 2 truong mat khau.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() < 6) {
            Toast.makeText(requireContext(), "Mat khau can it nhat 6 ky tu.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(requireContext(), "Mat khau khong khop.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AuthDemoData.matchesEmail(email) || !AuthDemoData.OTP_CODE.equals(otp)) {
            Toast.makeText(requireContext(), "Khong the xac thuc yeu cau dat lai.", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthDemoData.updatePassword(requireContext(), newPassword);
        updateRememberedPassword(newPassword);
        getAuthNavigator().showSuccess();
    }

    private void updateRememberedPassword(String newPassword) {
        SharedPreferences prefs = requireContext().getSharedPreferences(LoginFragment.AUTH_PREFS, requireContext().MODE_PRIVATE);
        boolean remember = prefs.getBoolean(LoginFragment.KEY_REMEMBER, false);
        String storedEmail = prefs.getString(LoginFragment.KEY_EMAIL, "");
        if (remember && email.equalsIgnoreCase(storedEmail)) {
            prefs.edit().putString(LoginFragment.KEY_PASSWORD, newPassword).apply();
        }
    }
}
