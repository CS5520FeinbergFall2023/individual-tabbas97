package com.example.test_blank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.about_me_button);
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showToast("Thameem Abbas Ibrahim Bathusha\nibrahimbathusha.t@northeastern.edu");
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // Code for the button
    public void buttonClick(android.view.View view) {
        showToast("Thameem Abbas Ibrahim Bathusha\nibrahimbathusha.t@northeastern.edu");
    }
}