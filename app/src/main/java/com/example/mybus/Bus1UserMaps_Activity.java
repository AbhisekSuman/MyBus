package com.example.mybus;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mybus.databinding.Bus1locationMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mybus.databinding.ActivityBus1UserMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bus1UserMaps_Activity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityBus1UserMapsBinding binding;


    private DatabaseReference reference;
    private LocationManager manager;

    private final int MIN_TIME=1000;
    private final int MIN_DISTANCE = 1;
    Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBus1UserMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager= (LocationManager) getSystemService(LOCATION_SERVICE);

        reference= FirebaseDatabase.getInstance().getReference().child("User-101");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        readChanges();
    }

    private void readChanges() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        MyLocation location = dataSnapshot.getValue(MyLocation.class);
                        if (location != null) {
                            myMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
//                            LatLng Bus = new LatLng(location.getLatitude(), location.getLongitude());
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Bus, 18), 5000, null);
                            LatLng Bam = new LatLng(19.314962, 84.794090);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Bam,6),3000,null);

                        }
                    }catch (Exception e){
                        Toast.makeText(Bus1UserMaps_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        mMap = googleMap;

        // Add a marker in Berhampur and move the camera
        LatLng Bam = new LatLng(19.314962, 84.794090);

        myMarker = mMap.addMarker(new MarkerOptions().position(Bam).title("Marker in Bam")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_baseline_directions_bus_24)));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Bam, 18), 3000, null);
    }


    //For Bus Marker
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawables = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawables.setBounds(0,0, vectorDrawables.getIntrinsicWidth(), vectorDrawables.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawables.getIntrinsicWidth(), vectorDrawables.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawables.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location!=null){
            saveLocation(location);
        }else{
            Toast.makeText(this, "No location", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLocation(Location location) {

        reference.setValue(location);
    }
}