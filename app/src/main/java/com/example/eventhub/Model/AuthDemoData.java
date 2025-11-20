package com.example.eventhub.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public final class AuthDemoData {

    public static final String USER_ID = "1";
    public static final String USER_EMAIL = "1120@sv.ute.udn.vn";
    public static final String USER_NAME = "EventHub Member";
    public static final String OTP_CODE = "000000";

    private static final String PREFS = "auth_demo_prefs";
    private static final String KEY_PASSWORD = "demo_password";
    private static final String DEFAULT_PASSWORD = "123456";

    private AuthDemoData() {
    }

    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static String getPassword(Context context) {
        return prefs(context).getString(KEY_PASSWORD, DEFAULT_PASSWORD);
    }

    public static void updatePassword(Context context, String newPassword) {
        prefs(context).edit().putString(KEY_PASSWORD, newPassword).apply();
    }

    public static boolean matchesCredentials(Context context, String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            return false;
        }
        return USER_EMAIL.equalsIgnoreCase(email.trim()) && getPassword(context).equals(password);
    }

    public static boolean matchesEmail(String email) {
        return !TextUtils.isEmpty(email) && USER_EMAIL.equalsIgnoreCase(email.trim());
    }
}
