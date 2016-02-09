package com.nataliajastrzebska.insidespy;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class LocationService extends Service {
    private float lat, lon;

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
        lat = intent.getExtras().getFloat("lat");
        lon = intent.getExtras().getFloat("lon");
    }

    private String getUrl(){
        String base = "https://www.google.com/maps/preview/@";
        return base + lat + "," + lon + ",15z";
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
