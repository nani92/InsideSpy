package com.nataliajastrzebska.insidespy.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.nataliajastrzebska.insidespy.codes.CodeGetGps;

public class LocationGpsService extends Service {
    private float latitude, longitude;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getLocationVariables(intent);
        openGoogleMaps(getUrl());

        return Service.START_NOT_STICKY;
    }

    private void openGoogleMaps(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(browserIntent);
    }

    private void getLocationVariables(Intent intent) {
        latitude = intent.getExtras().getFloat(CodeGetGps.EXTRA_LAT);
        longitude = intent.getExtras().getFloat(CodeGetGps.EXTRA_LON);
    }

    private String getUrl(){
        String base = "http://maps.google.com/maps?q=loc:";
        return base + latitude + "," + longitude;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
