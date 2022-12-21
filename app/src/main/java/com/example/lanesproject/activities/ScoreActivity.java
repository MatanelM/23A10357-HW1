package com.example.lanesproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import com.example.lanesproject.R;
import com.example.lanesproject.callbacks.CallBack_userProtocol;
import com.example.lanesproject.managers.LocationManager;
import com.example.lanesproject.views.LocationFragment;
import com.example.lanesproject.views.UserFragment;
import com.google.android.gms.maps.model.LatLng;

public class ScoreActivity extends AppCompatActivity{

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
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.main_FRAME_map,locationFragment)
                .commit();
    }

    private void showUserLocation(String name) {
        LatLng latLng = new LatLng(
                LocationManager.getInstance().getLatitude(),
                LocationManager.getInstance().getLongitude()
        );
        locationFragment.zoom(latLng);
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}