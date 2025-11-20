package com.example.eventhub;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public final class SessionManager {

    private static final String PREFS_NAME = "eventhub_session";
    private static final String KEY_ID = "user_id";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_AVATAR = "user_avatar";

    private static SessionManager instance;
    private final SharedPreferences preferences;

    private SessionManager(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveUser(String id, String email, String fullName, String avatar) {
        preferences.edit()
                .putString(KEY_ID, id)
                .putString(KEY_EMAIL, email)
                .putString(KEY_NAME, fullName)
                .putString(KEY_AVATAR, avatar)
                .apply();
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(preferences.getString(KEY_ID, ""));
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    public String getUserId() {
        return preferences.getString(KEY_ID, "");
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, "");
    }

    public String getFullName() {
        return preferences.getString(KEY_NAME, "");
    }

    public String getAvatar() {
        return preferences.getString(KEY_AVATAR, "");
    }
}
