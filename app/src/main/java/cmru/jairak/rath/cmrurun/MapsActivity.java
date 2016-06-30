package cmru.jairak.rath.cmrurun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double cmruLatADouble =18.807089, cmruLngADouble = 98.986621;
    private double userLatADouble, userLngADouble;
    private LocationManager locationManager;
    private Criteria criteria;
    private String userIDString, userNameString;
    private static final String urlEditLocation = "http://swiftcodingthai.com/cmru/edit_location_rathjairak.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        //Setup
        userLatADouble = cmruLatADouble;
        userLngADouble = cmruLngADouble;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

        //Get Value from intent
        userIDString = getIntent().getStringExtra("userID");
        userNameString = getIntent().getStringExtra("Name");
        Log.d("30JuneV1", "userID ==>" + userIDString);
        Log.d("30JuneV1", "userName ==>" + userNameString);

        editLocation();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } //Main Method

    private void editLocation() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", userIDString)
                .add("Lat", Double.toString(userLatADouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlEditLocation).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });

    } // editLocation

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        Location networkLocation = myFindLocation(locationManager.NETWORK_PROVIDER);

        if (networkLocation != null) {

            userLatADouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();


        } //if

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();

        }


    }// on resume

    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);


    }

    public Location myFindLocation (String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);

            location = locationManager.getLastKnownLocation(strProvider);


        } else {
            Log.d("29Junev1", "Cannot Find Location");
        }

        return location;
    }


    public LocationListener locationListener = new LocationListener() {
        @Override //ขยับตำแหน่งทำงาน
        public void onLocationChanged(Location location) {

            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();


        } //onlocationchange

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


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

        // Setup to CMRU
        LatLng latLng = new LatLng(cmruLatADouble, cmruLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        // ตัวเลข 16 คือใกล้โลกเท่าไหร่

        createStationMarker();

        myLoop();

    } //Onmapready

    private void myLoop() {
        //ใส่ log D
        Log.d("29JuneV1", "userLas ==>" + userLatADouble);
        Log.d("29JuneV1", "userLng ==>" + userLngADouble);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoop();
            }
        }, 3000);

    }//myloop

    //comment
    private void createStationMarker() {

        MyData myData = new MyData();
        double[] latDoubles = myData.getLatStationDoubles();
        double[] lngDoubles = myData.getLngStationDoubles();
        int[] iconInts = myData.getIconStationInts();


        for (int i=0;i<latDoubles.length;i++) {
            LatLng latLng = new LatLng(latDoubles[i], lngDoubles[i]);
            mMap.addMarker(new MarkerOptions().position(latLng)
            .icon(BitmapDescriptorFactory.fromResource(iconInts [i]))
            .title("ด่านที่ "+ Integer.toString(i+1)));
        }



    } // createStationMarker


}// Main Class
