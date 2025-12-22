package com.example.eventhub.View.Fragment.KhachHang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import androidx.navigation.Navigation;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiMessageResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.ForgotPasswordRequest;
import com.example.eventhub.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {

    private static final String ARG_EMAIL = "arg_email";

    private EditText emailInput;
    private Button sendOtpButton;
    private ImageView btn_back;
    private IAPI iapi;


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
        iapi = ApiClient.getClient().create(IAPI.class);
        btn_back.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        sendOtpButton.setOnClickListener(v -> sendOtp(v));
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                toggleButtonState();
            }
        });
    }


    private void toggleButtonState() {
        String email = emailInput.getText().toString().trim();
        boolean enabled = !TextUtils.isEmpty(email);
        sendOtpButton.setEnabled(enabled);
        sendOtpButton.setAlpha(enabled ? 1f : 0.5f);
    }

    private void sendOtp(View view) {
        String email = emailInput.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Vui long nhap email.", Toast.LENGTH_SHORT).show();
            return;
        }

        sendOtpButton.setEnabled(false);
        sendOtpButton.setAlpha(0.5f);

        Call<ApiMessageResponse> call = iapi.sendOtp(new ForgotPasswordRequest(email));
        call.enqueue(new Callback<ApiMessageResponse>() {
            @Override
            public void onResponse(Call<ApiMessageResponse> call, Response<ApiMessageResponse> response) {
                toggleButtonState();
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Bundle args = new Bundle();
                    args.putString(ARG_EMAIL, email);
                    Navigation.findNavController(view).navigate(R.id.otpVerifyFragment, args);
                    return;
                }
                String message = response.body() != null ? response.body().getMessage() : "Khong gui duoc OTP.";
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiMessageResponse> call, Throwable t) {
                toggleButtonState();
                Toast.makeText(requireContext(), "Loi ket noi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
