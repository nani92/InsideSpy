package com.nataliajastrzebska.insidespy.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.nataliajastrzebska.insidespy.Code.CodeGet;

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
        cid = intent.getExtras().getInt(CodeGet.EXTRA_CID);
        lac = intent.getExtras().getInt(CodeGet.EXTRA_LAC);
        mnc = intent.getExtras().getInt(CodeGet.EXTRA_MNC);
        mcc = intent.getExtras().getInt(CodeGet.EXTRA_MCC);
    }

    private void displayLocation(){

    }
}
