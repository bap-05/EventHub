package com.example.eventhub.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventhub.Model.AuthDemoData;
import com.example.eventhub.R;
import com.example.eventhub.SessionManager;
import com.example.eventhub.View.MainActivity;

import java.util.Locale;

public class LoginFragment extends BaseAuthFragment {

    static final String AUTH_PREFS = "eventhub_prefs";
    static final String KEY_REMEMBER = "remember";
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";

    private EditText emailInput;
    private EditText passwordInput;
    private View loginButton;
    private Switch rememberSwitch;
    private SharedPreferences preferences;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_activity, container, false);
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
        populateSavedCredentials();
        applyDemoDefaultsIfEmpty();

        loginButton.setOnClickListener(v -> attemptLogin());
        forgotPassword.setOnClickListener(v -> getAuthNavigator().showForgotPassword());
    }

    private void populateSavedCredentials() {
        boolean remember = preferences.getBoolean(KEY_REMEMBER, false);
        rememberSwitch.setChecked(remember);
        if (remember) {
            emailInput.setText(preferences.getString(KEY_EMAIL, ""));
            passwordInput.setText(preferences.getString(KEY_PASSWORD, ""));
        }
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim().toLowerCase(Locale.US);
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "Vui long nhap day du email va mat khau.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Email khong hop le.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthDemoData.matchesCredentials(requireContext(), email, password)) {
            Toast.makeText(requireContext(), "Sai tai khoan hoac mat khau demo.", Toast.LENGTH_SHORT).show();
            return;
        }

        handleRememberState(email, password);
        SessionManager.getInstance(requireContext()).saveUser(
                AuthDemoData.USER_ID,
                AuthDemoData.USER_EMAIL,
                AuthDemoData.USER_NAME,
                ""
        );
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void handleRememberState(String email, String password) {
        SharedPreferences.Editor editor = preferences.edit();
        if (rememberSwitch.isChecked()) {
            editor.putBoolean(KEY_REMEMBER, true);
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_EMAIL);
            editor.remove(KEY_PASSWORD);
        }
        editor.apply();
    }

    private void applyDemoDefaultsIfEmpty() {
        if (TextUtils.isEmpty(emailInput.getText())) {
            emailInput.setText(AuthDemoData.USER_EMAIL);
        }
        if (TextUtils.isEmpty(passwordInput.getText())) {
            passwordInput.setText(AuthDemoData.getPassword(requireContext()));
        }
    }
}
