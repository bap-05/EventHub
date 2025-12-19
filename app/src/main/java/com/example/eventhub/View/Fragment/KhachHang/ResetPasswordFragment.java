package com.example.eventhub.View.Fragment.KhachHang;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.eventhub.API.ApiClient;
import com.example.eventhub.API.ApiMessageResponse;
import com.example.eventhub.API.IAPI;
import com.example.eventhub.Model.ResetPasswordRequest;
import com.example.eventhub.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordFragment extends Fragment {

    private static final String ARG_EMAIL = "arg_email";
    private static final String ARG_OTP = "arg_otp";

    private EditText newPasswordInput;
    private EditText confirmPasswordInput;
    private Button updateButton;
    private String email;
    private String otp;
    private IAPI iapi;

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


        newPasswordInput = view.findViewById(R.id.newPass);
        confirmPasswordInput = view.findViewById(R.id.confirmPass);
        updateButton = view.findViewById(R.id.btnUpdate);
        iapi = ApiClient.getClient().create(IAPI.class);

        view.findViewById(R.id.backBtn)
                .setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        updateButton.setOnClickListener(v -> updatePassword(v));
    }

    private void updatePassword(View v) {
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(requireContext(), "Vui long nhap du 2 truong mat khau.", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (newPassword.length() < 6) {
//            Toast.makeText(requireContext(), "Mat khau can it nhat 6 ky tu.", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(requireContext(), "Mat khau khong khop.", Toast.LENGTH_SHORT).show();
            return;
        }



        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Khong tim thay email.", Toast.LENGTH_SHORT).show();
            return;
        }

        updateButton.setEnabled(false);
        updateButton.setAlpha(0.5f);
        Call<ApiMessageResponse> call = iapi.resetPassword(new ResetPasswordRequest(email, newPassword));
        call.enqueue(new Callback<ApiMessageResponse>() {
            @Override
            public void onResponse(Call<ApiMessageResponse> call, Response<ApiMessageResponse> response) {
                updateButton.setEnabled(true);
                updateButton.setAlpha(1f);
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    updateRememberedPassword(newPassword);
                    Navigation.findNavController(v).navigate(R.id.successFragment);
                    return;
                }
                String message = response.body() != null ? response.body().getMessage() : "Khong doi duoc mat khau.";
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiMessageResponse> call, Throwable t) {
                updateButton.setEnabled(true);
                updateButton.setAlpha(1f);
                Toast.makeText(requireContext(), "Loi ket noi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRememberedPassword(String newPassword) {
        SharedPreferences prefs = requireContext().getSharedPreferences(LoginFragment.AUTH_PREFS, requireContext().MODE_PRIVATE);
        boolean remember = prefs.getBoolean(LoginFragment.KEY_REMEMBER, false);
        String storedEmail = prefs.getString(LoginFragment.KEY_EMAIL, "");
        if (remember && email != null && email.equalsIgnoreCase(storedEmail)) {
            prefs.edit().putString(LoginFragment.KEY_PASSWORD, newPassword).apply();
        }
    }
}
