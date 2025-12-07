package com.example.eventhub.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventhub.R;
import com.example.eventhub.View.Fragment.KhachHang.FooterFragment;
import com.example.eventhub.View.Fragment.KhachHang.HomeFragment;
import com.example.eventhub.View.Fragment.KhachHang.WellComeFragment;

public class
MainActivity extends AppCompatActivity {
    public Fragment frsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("eventhub_prefs",MODE_PRIVATE);
        String email = preferences.getString("email",null);
        if(email !=null)
        {
            frsave = new HomeFragment();
            addFragment(frsave);
            addFooter(new FooterFragment());
        }
        else {
            frsave = new WellComeFragment();
            addFragment(frsave);
        }
    }

    public void addFragment(Fragment fr)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body,fr);
        if(fr.getClass().equals(frsave.getClass())){
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else
            transaction.addToBackStack(null);
        transaction.commit();
    }
    public void addFooter(Fragment fr)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_footer,fr,"Footer");
        transaction.commit();
    }


}