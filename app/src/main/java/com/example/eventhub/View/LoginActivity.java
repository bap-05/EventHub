package com.example.eventhub.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventhub.Model.AuthNavigator;
import com.example.eventhub.R;
import com.example.eventhub.View.Fragment.ForgotPasswordFragment;
import com.example.eventhub.View.Fragment.LoginFragment;
import com.example.eventhub.View.Fragment.OtpVerifyFragment;
import com.example.eventhub.View.Fragment.ResetPasswordFragment;
import com.example.eventhub.View.Fragment.SuccessFragment;

public class LoginActivity extends AppCompatActivity implements AuthNavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            showLogin();
        }
    }

    @Override
    public void showLogin() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        replaceFragment(LoginFragment.newInstance(), false);
    }

    @Override
    public void showForgotPassword() {
        replaceFragment(ForgotPasswordFragment.newInstance(), true);
    }

    @Override
    public void showOtpVerify(@NonNull String email) {
        replaceFragment(OtpVerifyFragment.newInstance(email), true);
    }

    @Override
    public void showResetPassword(@NonNull String email, @NonNull String otp) {
        replaceFragment(ResetPasswordFragment.newInstance(email, otp), true);
    }

    @Override
    public void showSuccess() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        replaceFragment(SuccessFragment.newInstance(), false);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.authContainer, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }
}
