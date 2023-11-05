package com.example.test_blank;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest.permission.*;
import android.widget.Toast;

public class LocationConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_confirmed);

        // Get user permission for precise location
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request permission both coarse and fine location
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Permission has already been granted
        }

        // if not granted, show WhyLocationRequired activity
        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            // start WhyLocationRequired activity
            android.content.Intent intent = new android.content.Intent(LocationConfirmed.this, WhyLocationRequired.class);
            startActivity(intent);
            return;
        }

        // if granted, show toast with Location permission available
        Toast.makeText(getApplicationContext(), "Location permission available", Toast.LENGTH_SHORT).show();

    }
}