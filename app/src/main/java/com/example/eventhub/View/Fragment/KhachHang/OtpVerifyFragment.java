package com.example.eventhub.View.Fragment.KhachHang;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiMessageResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.ForgotPasswordRequest;
import com.example.eventhub.Model.VerifyOtpRequest;
import com.example.eventhub.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerifyFragment extends Fragment {

    private static final String ARG_EMAIL = "arg_email";
    private static final int OTP_LENGTH = 6;

    private EditText[] otpInputs;
    private String email;
    private Button verifyButton;
    private TextView resendCodeView;
    private IAPI iapi;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.otp_verify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.backBtn)
                .setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }
        iapi = ApiClient.getClient().create(IAPI.class);

        verifyButton = view.findViewById(R.id.btnVerify);
        resendCodeView = view.findViewById(R.id.resendCode);
        TextView subtitleView = view.findViewById(R.id.subtitle);
        if (TextUtils.isEmpty(email)) {
            subtitleView.setText(getString(R.string.otp_subtitle, "email"));
        } else {
            subtitleView.setText(getString(R.string.otp_subtitle, email));
        }
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
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Khong tim thay email.", Toast.LENGTH_SHORT).show();
            return;
        }

        verifyButton.setEnabled(false);
        verifyButton.setAlpha(0.5f);
        Call<ApiMessageResponse> call = iapi.verifyOtp(new VerifyOtpRequest(email, code));
        call.enqueue(new Callback<ApiMessageResponse>() {
            @Override
            public void onResponse(Call<ApiMessageResponse> call, Response<ApiMessageResponse> response) {
                verifyButton.setEnabled(true);
                verifyButton.setAlpha(1f);
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Bundle args = new Bundle();
                    args.putString(ARG_EMAIL, email);
                    Navigation.findNavController(requireView()).navigate(R.id.resetPasswordFragment, args);
                    return;
                }
                String message = response.body() != null ? response.body().getMessage() : "OTP khong dung.";
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiMessageResponse> call, Throwable t) {
                verifyButton.setEnabled(true);
                verifyButton.setAlpha(1f);
                Toast.makeText(requireContext(), "Loi ket noi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOtp() {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Khong tim thay email.", Toast.LENGTH_SHORT).show();
            return;
        }
        setResendEnabled(false);
        Call<ApiMessageResponse> call = iapi.sendOtp(new ForgotPasswordRequest(email));
        call.enqueue(new Callback<ApiMessageResponse>() {
            @Override
            public void onResponse(Call<ApiMessageResponse> call, Response<ApiMessageResponse> response) {
                setResendEnabled(true);
                String message = response.body() != null ? response.body().getMessage() : "Da gui lai OTP.";
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiMessageResponse> call, Throwable t) {
                setResendEnabled(true);
                Toast.makeText(requireContext(), "Loi ket noi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
