package com.example.eventhub.View.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;


import java.util.Locale;

public class ForgotPasswordFragment extends Fragment {

    private EditText emailInput;
    private Button sendOtpButton;
    private ImageView btn_back;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forgot_password, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailInput = view.findViewById(R.id.emailInput);
        sendOtpButton = view.findViewById(R.id.btnSendOtp);
        btn_back = view.findViewById(R.id.backBtn);
        btn_back.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        sendOtpButton.setOnClickListener(v ->{

            ((MainActivity) requireActivity()).addFragment(new OtpVerifyFragment());

        });
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                toggleButtonState();
            }
        });


        toggleButtonState();
    }


    private void toggleButtonState() {
        String email = emailInput.getText().toString().trim();
        boolean enabled = !TextUtils.isEmpty(email);
        sendOtpButton.setEnabled(enabled);
        sendOtpButton.setAlpha(enabled ? 1f : 0.5f);
    }



    private void restoreButton() {
        sendOtpButton.setEnabled(true);
        sendOtpButton.setAlpha(1f);
    }
}
