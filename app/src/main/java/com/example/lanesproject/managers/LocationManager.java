package com.example.lanesproject.managers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class LocationManager{
    public final static int REQUEST_CODE= 100;

    private static LocationManager _instance = null;
    private Double latitude = 0.0;
    private Double longitude = 0.0;

    android.location.LocationManager location;

    private LocationManager(Context context) {
        location = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {}

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
        String coarse = "android.permission.ACCESS_COARSE_LOCATION";
        String fine = "android.permission.ACCESS_FINE_LOCATION";
        if (ContextCompat.checkSelfPermission(context, coarse) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{coarse}, 101);
        }
        if (ContextCompat.checkSelfPermission(context, fine) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{fine}, 102);
        }
        location.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 5000, 10, (android.location.LocationListener) locationListener);
        Location locNew = location.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);

        if (locNew != null) {
            this.latitude = locNew.getLatitude();
            this.longitude = locNew.getLongitude();

            System.out.println("LATITUDE: " + locNew.getLatitude());
            System.out.println("LONGITUDE: " + locNew.getLongitude());
        } else {
            this.latitude = 0.0;
            this.longitude = 0.0;

            System.out.println("LOCATION IS NULL, 0,0 values given");
        }
    }

    public Double getLatitude(){
        return this.latitude;
    }
    public Double getLongitude(){
        return this.longitude;
    }

    public static synchronized void init(Context context){
        if ( _instance == null) {
            _instance = new LocationManager(context);
        }
    }

    public static synchronized LocationManager getInstance(){
        return _instance;
    }

}
