package com.example.eventhub.View.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventhub.Model.AuthDemoData;
import com.example.eventhub.R;

public class OtpVerifyFragment extends BaseAuthFragment {

    private static final String ARG_EMAIL = "arg_email";
    private static final int OTP_LENGTH = 6;

    private EditText[] otpInputs;
    private String email;
    private Button verifyButton;
    private TextView resendCodeView;

    public static OtpVerifyFragment newInstance(String email) {
        OtpVerifyFragment fragment = new OtpVerifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            email = args.getString(ARG_EMAIL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.otp_verify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (TextUtils.isEmpty(email)) {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
            return;
        }

        view.findViewById(R.id.backBtn)
                .setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        verifyButton = view.findViewById(R.id.btnVerify);
        resendCodeView = view.findViewById(R.id.resendCode);
        TextView subtitleView = view.findViewById(R.id.subtitle);
        subtitleView.setText(getString(R.string.otp_subtitle, email));
        setResendEnabled(true);

        otpInputs = new EditText[]{
                view.findViewById(R.id.otp1),
                view.findViewById(R.id.otp2),
                view.findViewById(R.id.otp3),
                view.findViewById(R.id.otp4),
                view.findViewById(R.id.otp5),
                view.findViewById(R.id.otp6)
        };
        setupOtpInputs();

        verifyButton.setOnClickListener(v -> verifyOtpOnServer());
        resendCodeView.setOnClickListener(v -> resendOtp());
    }

    private void setupOtpInputs() {
        for (int i = 0; i < otpInputs.length; i++) {
            final int index = i;
            otpInputs[i].addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < otpInputs.length - 1) {
                        otpInputs[index + 1].requestFocus();
                    }
                }
            });

            otpInputs[i].setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    if (otpInputs[index].getText().length() == 0 && index > 0) {
                        otpInputs[index - 1].requestFocus();
                    }
                }
                return false;
            });
        }
    }

    private void verifyOtpOnServer() {
        String code = collectOtp();
        if (code.length() != OTP_LENGTH) {
            Toast.makeText(requireContext(), "Vui long nhap du " + OTP_LENGTH + " chu so.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthDemoData.OTP_CODE.equals(code)) {
            Toast.makeText(requireContext(), "Ma OTP demo khong chinh xac.", Toast.LENGTH_SHORT).show();
            return;
        }
        getAuthNavigator().showResetPassword(email, code);
    }

    private void resendOtp() {
        setResendEnabled(false);
        resendCodeView.postDelayed(() -> {
            setResendEnabled(true);
            Toast.makeText(requireContext(),
                    "OTP demo (" + AuthDemoData.OTP_CODE + ") da gui lai toi " + email,
                    Toast.LENGTH_SHORT).show();
        }, 800);
    }

    private String collectOtp() {
        StringBuilder builder = new StringBuilder();
        for (EditText input : otpInputs) {
            builder.append(input.getText().toString().trim());
        }
        return builder.toString();
    }

    private void setResendEnabled(boolean enabled) {
        if (resendCodeView != null) {
            resendCodeView.setEnabled(enabled);
            resendCodeView.setAlpha(enabled ? 1f : 0.5f);
        }
    }
}
