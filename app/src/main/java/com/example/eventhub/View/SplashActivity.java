package com.example.eventhub.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventhub.R;
import com.example.eventhub.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        if (SessionManager.getInstance(this).isLoggedIn()) {
            navigateToMain();
            return;
        }

        applyDrawableIfAvailable((ImageView) findViewById(R.id.logoImg), "logo");
        applyDrawableIfAvailable((ImageView) findViewById(R.id.iconContinueArrow), "ic_arrow_right_white");

        View continueButton = findViewById(R.id.buttonContinue);
        if (continueButton != null) {
            continueButton.setOnClickListener(v -> navigateToLogin());
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Khong mo duoc man hinh dang nhap.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void applyDrawableIfAvailable(ImageView view, String drawableName) {
        if (view == null) {
            throw new IllegalArgumentException("ImageView is null");
        }

        int resId = getResources().getIdentifier(drawableName, "drawable", getPackageName());

        if (resId == 0) {
            throw new Resources.NotFoundException(
                "Drawable with name \"" + drawableName + "\" not found in /res/drawable"
            );
        }

        view.setImageResource(resId);
        view.setVisibility(View.VISIBLE);
    }

}
