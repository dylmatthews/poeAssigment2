package am.dx.varsityspecials.www.varsityspecials;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Journey.IJourneyFinderListener;
import Journey.Journey;
import Journey.JourneyFinder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, IJourneyFinderListener {

    private GoogleMap mMap;
    private String destination = "30 Oxfrod drive durban north";
    private String origin = "16 Savell Avenue glenashley";
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longitude;
    private String area = "";
    private String name = "";
    private LocationListener listener;

 private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            area = getIntent().getStringExtra("area");
            name = getIntent().getStringExtra("name");
            destination = name + "," + area;
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



            listener = new LocationListener() { //listeneer for location change
                @Override
                public void onLocationChanged(Location locatio) { //when location changes


                    // Called when a new location is found by the network location provider.
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }


                    latitude = locatio.getLatitude(); //sets latitude
                    longitude = locatio.getLongitude(); //sets longitude




                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); //asks user to enable location
                    startActivity(i);
                }


            };
        } catch (Exception e) {
            Toast.makeText(this, "Shit " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void onFindPath(View view)
    {
        try {

            sendRequest();  //method to check location access
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void sendRequest() throws InterruptedException {

        locationManager.requestLocationUpdates("gps", 100, 5, listener); //sets update to update ever 5 meters
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



        origin = latitude + "," + longitude; //sets origin co ordinates


        if (origin.isEmpty()) { //checks to see if origin is empty
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new JourneyFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE); //sets logion manager
        Criteria criteria = new Criteria(); //new cirteria
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //sets accuracy
        locationManager.requestLocationUpdates("gps", 100, 5, listener); //sets location request to every 5 meters



        LatLng cur = new LatLng(latitude, longitude); //makes long lat viable and sets

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 18)); //sets zome
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Current location")
                .position(cur)));  //makes markers

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


    }


    @Override
    public void onDirectionFinderStart() {

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onPause() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        super.onPause();
        locationManager.removeUpdates(listener);//stops updates for location
    }



    @Override
    public void onDirectionFinderSuccess(List<Journey> routes) { //this makes the polines
        //this code was taken ands adapted from https://github.com/hiepxuan2008/GoogleMapDirectionSimple/tree/master/app/src/main/java/Modules

        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Journey journey : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(journey.startLocation, 13));
            //  ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            //((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_flag_black_24dp))
                    .title(journey.startAddress)
                    .position(journey.startLocation))); //sets marker for start
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_flag_black_24dp))
                    .title(journey.endAddress)
                    .position(journey.endLocation))); //sets marker for end

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(10); //sets colour of polyline

            for (int i = 0; i < journey.points.size(); i++)
                polylineOptions.add(journey.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

}

