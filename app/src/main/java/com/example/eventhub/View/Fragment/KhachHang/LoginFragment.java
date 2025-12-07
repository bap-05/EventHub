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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.eventhub.R;
import com.example.eventhub.View.MainActivity;

public class LoginFragment extends Fragment {

    static final String AUTH_PREFS = "eventhub_prefs";
    static final String KEY_REMEMBER = "remember";
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";

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
        populateSavedCredentials();


        loginButton.setOnClickListener(v -> {
//            if(emailInput.getText().toString().isEmpty() && passwordInput.getText().toString().isEmpty())
//            {
//                Toast.makeText(view.getContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
//            }
//            else{
                handleRememberState();
                ((MainActivity)requireActivity()).frsave = new HomeFragment();
                ((MainActivity)requireActivity()).addFragment(((MainActivity)requireActivity()).frsave );
                ((MainActivity)requireActivity()).addFooter(new FooterFragment());
//            }
        });
        forgotPassword.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).addFragment(new ForgotPasswordFragment());
        });
    }

    private void populateSavedCredentials() {
        boolean remember = preferences.getBoolean(KEY_REMEMBER, false);
        rememberSwitch.setChecked(remember);
        if (remember) {
            emailInput.setText(preferences.getString(KEY_EMAIL, ""));
            passwordInput.setText(preferences.getString(KEY_PASSWORD, ""));
        }
    }
    private void handleRememberState() {
        SharedPreferences.Editor editor = preferences.edit();
        if (rememberSwitch.isChecked()) {
            editor.putBoolean(KEY_REMEMBER, true);
            editor.putString(KEY_EMAIL, emailInput.getText().toString().trim());
            editor.putString(KEY_PASSWORD, passwordInput.getText().toString().trim());
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_EMAIL);
            editor.remove(KEY_PASSWORD);
        }
        editor.apply();
    }


}
