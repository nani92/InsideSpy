package com.nataliajastrzebska.insidespy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by nataliajastrzebska on 31/01/16.
 */
public class LocationBtsService extends Service {

    int cid, lac, mnc, mcc;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getLocationVariables(intent);
        displayLocation();
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    private void getLocationVariables(Intent intent){
        cid = intent.getExtras().getInt("cid");
        lac = intent.getExtras().getInt("lac");
        mnc = intent.getExtras().getInt("mnc");
        mcc = intent.getExtras().getInt("mcc");
    }

    private void displayLocation(){
        final DisplayLocation displayLocation = new DisplayLocation();
        Thread locationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    displayLocation.count(cid, lac);
                } catch(Exception e){
                }
            }
        });
        locationThread.run();
    }
}
