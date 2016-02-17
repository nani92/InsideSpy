package com.nataliajastrzebska.insidespy.codes;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.nataliajastrzebska.insidespy.R;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class CodeGetGps implements LocationListener {

    public static final String EXTRA_LAT = "lat";
    public static final String EXTRA_LON = "lon";

    Context context;
    String number;

    LocationManager locationManager;

    public CodeGetGps(Context context, String number) {
        this.context = context;
        this.number = number;

        setupLocationManager();
        registerLocationListener();
        Location location = getLocation();

        if (location != null) {
            SmsBuilder.sendAnswerSms(number, Code.GETGPS, location.getLatitude() + SmsBuilder.DATA_SEPARATOR + location.getLongitude());

            return;
        }

        SmsBuilder.sendAnswerSms(number, Code.GETGPS, context.getResources().getString(R.string.no_location_data));
    }

    private void setupLocationManager() {

        if (android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    private void registerLocationListener() {

        if (android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    private Location getLocation() {

        if (android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        unregisterListener();

        if(number == null) {
            return;
        }

        SmsBuilder.sendAnswerSms(number, Code.GETGPS, location.getLatitude() + SmsBuilder.DATA_SEPARATOR + location.getLongitude());
        number = null;
    }

    private void unregisterListener() {

        if (android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.removeUpdates(this);
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
}
