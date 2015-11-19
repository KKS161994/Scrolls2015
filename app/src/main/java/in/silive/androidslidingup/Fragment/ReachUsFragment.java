package in.silive.androidslidingup.Fragment;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import in.silive.androidslidingup.R;


/**
 * Created by kone on 12/9/15.
 */
public class ReachUsFragment extends Fragment implements RoutingListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleMap mMap;
    private View rootView;
    private SupportMapFragment mapFragment;
    Location lastlocation;
    private LatLng latLngAKGEC = new LatLng(28.676564, 77.500242), startLocation;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(3000)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.reachus, container, false);
       /* Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.WALKING)
                .withListener(this)
                .waypoints(start, end)
                .build();
        routing.execute();
       */

        setUpGoogleApiClientIfNeeded();
        mGoogleApiClient.connect();
        return rootView;
    }

    private void setUpGoogleApiClientIfNeeded() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        android.support.v4.app.FragmentManager fManager = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fManager.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fManager.beginTransaction().replace(R.id.map, mapFragment).commit();
        }
    }

    @Override
    public void onResume() {
        if (mMap == null) {
            mMap = mapFragment.getMap();
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
            mMap.setMyLocationEnabled(true);
       /*     mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();
       */
        }
        super.onResume();
    }

    @Override
    public void onRoutingFailure() {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "Something went wrong, Try again", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
        progressDialog.dismiss();
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(getResources().getColor(R.color.primary_dark));
        polyOptions.width(10);
        polyOptions.addAll(mPolyOptions.getPoints());
        Polyline polyline = mMap.addPolyline(polyOptions);

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(startLocation);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(latLngAKGEC);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        mMap.addMarker(options);

    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        ;
        lastlocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        startLocation = new LatLng(lastlocation.getLatitude(), lastlocation.getLongitude());
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lastlocation.getLatitude(), lastlocation.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom, 3000, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(getActivity(), "Please wait.",
                        "Fetching route information.", true);
                Routing routing = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.WALKING)
                        .withListener(ReachUsFragment.this)
                        .waypoints(startLocation, latLngAKGEC)
                        .build();
                routing.execute();

            }
        }, 4000);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}