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
        Button button2 = findViewById(R.id.clicky_clicky_button);
        Button button3 = findViewById(R.id.link_collector_button);

        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Launch the about_me fragment
                android.content.Intent intent = new android.content.Intent(MainActivity.this, about_me.class);
                startActivity(intent);
            }
        });

        // button2 launches the clicky_clicky_activity
        button2.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, clicky_clicky_activity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, LinkCollector.class);
                startActivity(intent);
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