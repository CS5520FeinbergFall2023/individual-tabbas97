package com.example.test_blank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.test_blank.ui.main.AboutMeFragment;

public class about_me extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AboutMeFragment.newInstance())
                    .commitNow();
        }
    }
}