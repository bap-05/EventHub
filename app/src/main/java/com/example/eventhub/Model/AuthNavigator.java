package com.example.eventhub.Model;

public interface AuthNavigator {
    void showLogin();
    void showForgotPassword();
    void showOtpVerify(String email);
    void showResetPassword(String email, String otp);
    void showSuccess();
}
