package com.example.geoquizapp;

import androidx.fragment.app.FragmentActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.geoquizapp.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private Marker markerInit;
    private String country = "null";
    private TextView timer;
    public List<Address> addresses;
    private CountDownTimer countDownTimer;
    private long timeLeftMilliseconds = 30000;
    private final LatLng initialPosition = new LatLng(0, 0);

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
            }
        }.start();
    }

    public void updateTimer() {
        int secs = (int) timeLeftMilliseconds / 1000;
        String timeLeftString;
        if(secs < 10)
            timeLeftString = "0" + secs;
        else
            timeLeftString = "" + secs;

        timer.setText(timeLeftString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        timer = (TextView) findViewById(R.id.maps_timer);
        startTimer();

        Button selectButton = (Button) findViewById(R.id.map_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    if (!addresses.isEmpty()){
                        country = addresses.get(0).getCountryName();
                    }
                    else{
                        country = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                if(country != null){
                    intent.putExtra("mapsAnswer", country.split("\\s+"));
                } else {
                    intent.putExtra("mapsAnswer", "null");
                }
                setResult(1, intent);
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(ContentValues.TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(ContentValues.TAG, "Can't find style. Error: ", e);
        }

        mMap = googleMap;

        // Add a marker in Lat, Lang 0, 0
        MarkerOptions markerOptionsInit = new MarkerOptions();
        markerOptionsInit.position(initialPosition);
        markerInit = mMap.addMarker(markerOptionsInit);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mMap.getCameraPosition().target);
        marker = mMap.addMarker(markerOptions);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if(markerInit != null)
                    markerInit.remove();
                marker.setPosition(mMap.getCameraPosition().target);
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(initialPosition));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}