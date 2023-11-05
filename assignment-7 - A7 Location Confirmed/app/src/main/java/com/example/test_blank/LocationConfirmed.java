package com.example.test_blank;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.gms.location.LocationRequest;
import android.os.Bundle;
import android.Manifest.permission.*;
import android.os.Looper;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;
import java.util.List;

public class LocationConfirmed extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private GPSDistanceTracker gpsDistanceTracker;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    static final int new_loc_distance = 5;
    private List<Location> locations;

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
//            android.content.Intent intent = new android.content.Intent(LocationConfirmed.this, WhyLocationRequired.class);
//            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Location permission not available", Toast.LENGTH_SHORT).show();
        } else {
            // Permission has already been granted
            Toast.makeText(getApplicationContext(), "Location permission available", Toast.LENGTH_SHORT).show();
        }

        // make another attempt to get permission
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request permission both coarse and fine location
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Permission has already been granted
        }

        // if not granted now, exit activity
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request permission both coarse and fine location
            Toast.makeText(getApplicationContext(), "Location permission not available. Activity cannot function. Returning to home page", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Permission has already been granted
            Toast.makeText(getApplicationContext(), "Location permission available", Toast.LENGTH_SHORT).show();
        }

        // Start the normal expected activity
        // TODO: Create a listener for gps location - on change - update the lat and long

        fusedLocationClient = new FusedLocationProviderClient(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000);
        } else {
            Toast.makeText(getApplicationContext(), "Android version is too low. Cannot use location services", Toast.LENGTH_SHORT).show();
            System.out.println("Android version is too low. Cannot use location services");
            finish();
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                System.out.println("Location callback called");
                if (locationResult == null) {
                    System.out.println("Location result is null");
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    System.out.println("Location: " + location.toString());
                    if (locations.size() < 1) {
                        locations.add(location);
                    } else if (getDistanceFromLastPoint(location) >= new_loc_distance) {
                        locations.add(location);
                    }
                    // Update the UI with the current location
                    // Update the UI with the distance travelled
                    updateUI();
                }
            }
        };

        locations = new java.util.ArrayList<Location>();

        startLocationUpdates();

        System.out.println("Requesting location updates");

        Button reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(v -> {
            locations.clear();
            updateUI();
        });
    }

    // Suppressing missing permission because the permission is checked before this method is called
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        );
    }

    @SuppressLint("MissingPermission")
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void updateUI() {
        if (locations.size() < 1) {
            // If the list is empty, do nothing
            return;
        }
        // Update the UI with the current location
        Location lastObtained = locations.get(locations.size() - 1);

        // Update the UI with the distance travelled
        double totalDistance = getTotalDistanceTravelled(locations);

        runOnUiThread(() -> {
            TextView lat = findViewById(R.id.current_latitude);
            TextView lon = findViewById(R.id.current_longitude);
            TextView dist = findViewById(R.id.total_distance_travelled);
            // Update the UI with the current location
            lat.setText(String.valueOf(lastObtained.getLatitude()));
            lon.setText(String.valueOf(lastObtained.getLongitude()));
            // Update the UI with the distance travelled
            dist.setText(String.valueOf(totalDistance));
        });
    }

    private double getDistanceBetweenPoints(Location l1, Location l2) {
        return l1.distanceTo(l2);
    }

    private double getTotalDistanceTravelled(List<Location> locations) {
        double totalDistance = 0;
        for (int i = 0; i < locations.size() - 1; i++) {
            totalDistance += getDistanceBetweenPoints(locations.get(i), locations.get(i + 1));
        }
        return totalDistance;
    }

    private double getDistanceFromLastPoint(Location l1) {
        return getDistanceBetweenPoints(locations.get(locations.size() - 1), l1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resuming activity");
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("locations", (ArrayList<? extends Parcelable>) locations);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        locations = savedInstanceState.getParcelableArrayList("locations");
        updateUI();
    }
}