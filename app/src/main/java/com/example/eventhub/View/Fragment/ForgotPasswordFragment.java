package com.example.eventhub.View.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
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


import java.util.Locale;

public class ForgotPasswordFragment extends BaseAuthFragment {

    private EditText emailInput;
    private Button sendOtpButton;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailInput = view.findViewById(R.id.emailInput);
        sendOtpButton = view.findViewById(R.id.btnSendOtp);

        prefillDemoEmail();

        view.findViewById(R.id.backBtn)
                .setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                toggleButtonState();
            }
        });

        sendOtpButton.setOnClickListener(v -> sendOtp());
        toggleButtonState();
    }

    private void prefillDemoEmail() {
        if (TextUtils.isEmpty(emailInput.getText())) {
            emailInput.setText(AuthDemoData.USER_EMAIL);
            emailInput.setSelection(emailInput.getText().length());
        }
    }

    private void toggleButtonState() {
        String email = emailInput.getText().toString().trim();
        boolean enabled = !TextUtils.isEmpty(email);
        sendOtpButton.setEnabled(enabled);
        sendOtpButton.setAlpha(enabled ? 1f : 0.5f);
    }

    private void sendOtp() {
        String email = emailInput.getText().toString().trim().toLowerCase(Locale.US);
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Vui long nhap email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Email khong hop le.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthDemoData.matchesEmail(email)) {
            Toast.makeText(requireContext(), "Chi ho tro tai khoan demo: " + AuthDemoData.USER_EMAIL, Toast.LENGTH_SHORT).show();
            return;
        }
        sendOtpButton.setEnabled(false);
        sendOtpButton.setAlpha(0.5f);

        sendOtpButton.postDelayed(() -> {
            restoreButton();
            Toast.makeText(requireContext(),
                    "OTP demo (" + AuthDemoData.OTP_CODE + ") da gui toi " + email,
                    Toast.LENGTH_SHORT).show();
            getAuthNavigator().showOtpVerify(email);
        }, 400);
    }

    private void restoreButton() {
        sendOtpButton.setEnabled(true);
        sendOtpButton.setAlpha(1f);
    }
}
