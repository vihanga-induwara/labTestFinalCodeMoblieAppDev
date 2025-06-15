package com.s23010355.induwara; // Replace with your package

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText addressEditText;
    private Button showLocationButton, goToSensorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize views
        addressEditText = findViewById(R.id.addressEditText);
        showLocationButton = findViewById(R.id.showLocationButton);
        goToSensorButton = findViewById(R.id.goToSensorButton);

        // Obtain the SupportMapFragment and get notified when the map is ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set button click listeners
        showLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressEditText.getText().toString().trim();
                if (!address.isEmpty()) {
                    geocodeAddress(address);
                } else {
                    Toast.makeText(MapsActivity.this, "Please enter an address", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SensorActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng defaultLocation = new LatLng(37.7749, -122.4194); // San Francisco
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
    }

    private void geocodeAddress(String address) {
        if (address.equalsIgnoreCase("1600 Amphitheatre Parkway, Mountain View, CA")) {
            LatLng location = new LatLng(37.4220, -122.0841); // Googleplex
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(location).title(address));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        } else {
            Toast.makeText(this, "Address not found or unsupported", Toast.LENGTH_SHORT).show();
        }
    }
}