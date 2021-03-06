package Utilities;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by rasmusmadsen on 17/05/2017.
 */

public class GPSManager {
    private Location deviceLocation;
    private LocationManager locationManager;
    private Context context;

    private static final String TIME = "10000";

    /**
     * Constructor for GPSManager which gets the current location of the device
     * @param context the context
     */
    public GPSManager(Context context) {
        this.context = context;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Long.parseLong(TIME), Float.MIN_VALUE, getLocationListenter());

        if (checkPermission()) {
            deviceLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    /**
     * Returns the current location
     * @return current location
     */
    public Location getDeviceLocation(){
        return deviceLocation;
    }

    /**
     * Check permission for the device to access to the GPS
     * @return permission for accesing the GPS on a device
     */
    private boolean checkPermission(){
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * A listener for when a location has changed
     * @return locationlistner
     */
    private LocationListener getLocationListenter() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                deviceLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
}
