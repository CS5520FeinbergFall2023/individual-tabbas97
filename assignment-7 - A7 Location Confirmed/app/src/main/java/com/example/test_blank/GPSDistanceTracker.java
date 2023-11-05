package com.example.test_blank;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

public class GPSDistanceTracker implements Runnable {

    // Data members
    // List of locations
    private List<android.location.Location> locations;
    private FusedLocationProviderClient fusedLocationClient;
    private Activity callingActivity;
    static final int new_loc_distance = 5;

    // Will use the activity context of the activity that calls this constructor
    public GPSDistanceTracker(Activity activity) {
        locations = new java.util.ArrayList<android.location.Location>();
        callingActivity = activity;
        fusedLocationClient = new FusedLocationProviderClient(callingActivity);
    }

    @Override
    public void run() {
        // This method will constantly update the list of locations in the background
        // Filtering based on the idea that the new location must be at least 10 meters away from the last location
        // If the new location is less than 10 meters away, it will not be added to the list
        // This is to prevent the list from being filled with locations that are too close together,
        // which would very likely come from jittery GPS data

        // Keep checking for the location
        // If list is empty, wait until the location is found
        // If list is not empty, wait until the location is at least 10 meters away from the last location

        if (
                ActivityCompat.checkSelfPermission(callingActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(callingActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(
                callingActivity, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        // check if the location is at least 10 meters away from the last location
                        if (locations.size() == 0) {
                            // If the list is empty, add the current location to the list
                            locations.add(location);
                        } else {
                            // Get the last location
                            android.location.Location lastLocation = locations.get(locations.size() - 1);
                            // Get the current location from the GPS service
                            android.location.Location currentLocation = new android.location.Location("currentLocation");
                            // Add the current location to the list if it is at least 10 meters away from the last location
                            if (currentLocation.distanceTo(lastLocation) >= new_loc_distance) {
                                locations.add(currentLocation);
                            }
                        }
                    }
                }
        );
        fusedLocationClient.getLastLocation().addOnFailureListener(callingActivity, e -> {
                System.out.println("Failed to get location");
                System.out.println("Error: " + e.getMessage());
            }
        );
    }

    // Methods
    // Add a location to the list
    public void addLocation(android.location.Location location) {
        locations.add(location);
    }
    // Get the distance between two locations
    public double getDistance(android.location.Location location1, android.location.Location location2) {
        return location1.distanceTo(location2);
    }

    // Get the distance between the last two locations
    public double getDistance() {
        if (locations.size() < 2) {
            return 0;
        }
        return locations.get(locations.size() - 1).distanceTo(locations.get(locations.size() - 2));
    }

    // Get the total distance between all locations
    public double getTotalDistance() {
        double totalDistance = 0;
        for (int i = 0; i < locations.size() - 1; i++) {
            totalDistance += locations.get(i).distanceTo(locations.get(i + 1));
        }
        return totalDistance;
    }
}
