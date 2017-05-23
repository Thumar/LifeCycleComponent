package com.androidkt.lifecycle;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by brijesh on 23/5/17.
 */

public class MyLocationManager implements LifecycleObserver {


    public static final String TAG = "MyLocationManager";
    private Context mContext;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public MyLocationManager(LifecycleOwner lifecycleOwner, Context context, LocationListener locationListener) {
        lifecycleOwner.getLifecycle().addObserver(this);
        mContext = context;
        this.locationListener = locationListener;
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void addLocationListener() {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Log.d(TAG, "Location Listener add");

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            locationListener.onLocationChanged(location);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void removeLocationListener() {

        if (locationManager == null) {
            return;
        }
        locationManager.removeUpdates(locationListener);
        locationManager = null;
        Log.d(TAG, "Location Listener Remove");

    }
}
