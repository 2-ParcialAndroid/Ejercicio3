package com.example.farmzaragoza;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double cityLatitude = getIntent().getDoubleExtra("cityLatitude", 0);
        double cityLongitude = getIntent().getDoubleExtra("cityLongitude", 0);
        ArrayList<Pharmacy> pharmacies = getIntent().getParcelableArrayListExtra("pharmacies");

        LatLng cityLocation = new LatLng(cityLatitude, cityLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 12));

        if (pharmacies != null) {
            for (Pharmacy pharmacy : pharmacies) {
                LatLng pharmacyLocation = new LatLng(pharmacy.getLatitude(), pharmacy.getLongitude());
                mMap.addMarker(new MarkerOptions().position(pharmacyLocation).title(pharmacy.getName()));
            }
        }
    }
}