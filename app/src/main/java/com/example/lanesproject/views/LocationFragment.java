package com.example.lanesproject.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lanesproject.R;
import com.example.lanesproject.entities.Player;
import com.example.lanesproject.managers.LocationManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment {

    private MaterialTextView map_LBL_title;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        // When clicked on map
                        // Initialize marker options, have latlng, googlemap
                        MarkerOptions markerOptions=new MarkerOptions();
                        // Set position of marker
                        markerOptions.position(latLng);
                        // Set title of marker
                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                        // Remove all marker
                        googleMap.clear();
                        // Animating to zoom the marker

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        // Add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });

            }
        });

        findViews(view);
        return view;
    }

    private void findViews(View view) {
        map_LBL_title = view.findViewById(R.id.map_LBL_title);
    }

    public void zoom() {
        Double latitude = LocationManager.getInstance().getLatitude();
        Double longitude = LocationManager.getInstance().getLongitude();

        // when loaded map -
        // Initialize marker options, have latlng, googlemap
        GoogleMap googleMap = ((SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map)).getMap();
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions=new MarkerOptions();
        // Set position of marker
        markerOptions.position(latLng);
        // Set title of marker
        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
        // Remove all marker
        googleMap.clear();
        // Animating to zoom the marker

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        // Add marker on map
        googleMap.addMarker(markerOptions);

        map_LBL_title.setText(latitude  + "\n" + longitude);
    }
}