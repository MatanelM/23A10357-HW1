package com.example.lanesproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.lanesproject.R;
import com.example.lanesproject.callbacks.CallBack_userProtocol;
import com.example.lanesproject.managers.LocationManager;
import com.example.lanesproject.views.LocationFragment;
import com.example.lanesproject.views.UserFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class ScoreActivity extends AppCompatActivity implements OnMapReadyCallback {

    private UserFragment userFragment;
    private LocationFragment locationFragment;
    private Button homeButton;

    CallBack_userProtocol callBack_userProtocol = new CallBack_userProtocol() {
        @Override
        public void user(String name) {
            showUserLocation(name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        userFragment = new UserFragment();
        userFragment.setCallback(callBack_userProtocol);

        locationFragment = new LocationFragment();

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener( v -> {
            callMenuIntent();
        });

        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, userFragment).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, locationFragment).commit();

//        LocationManager.getInstance().getLastLocation(this, ScoreActivity.this);
        System.out.println("Here");
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.main_FRAME_map,locationFragment)
                .commit();
//        locationFragment.getMapAsync(this);
    }

    private void showUserLocation(String name) {
//        double latitude = 32.4999;
//        double longitude = 34.5599;
        locationFragment.zoom();
    }

    private void callMenuIntent() {
        Intent intent = new Intent(this, MenuActivity.class);
        setIntentExtras(intent);
        startActivity(intent);
        finish();
    }

    private void setIntentExtras(Intent intent){
//        intent.putExtra("isLevelEasy", this.isLevelEasy);
//        intent.putExtra("isSensorMode", this.isSensorMode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // get the location and print if wait
//        if (requestCode == LocationManager.REQUEST_CODE){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                LocationManager.getInstance().getLastLocation(this, ScoreActivity.this);
//            }else {
//                Toast.makeText(this, "Required Permission . .", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        // activates parent function with - request code (100), permission (str arr), result (int arr)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}