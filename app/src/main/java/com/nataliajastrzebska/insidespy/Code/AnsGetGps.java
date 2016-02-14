package com.nataliajastrzebska.insidespy.Code;

import android.content.Context;
import android.content.Intent;
import com.nataliajastrzebska.insidespy.Services.LocationGpsService;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class AnsGetGps {

    String number;
    Context context;

    public AnsGetGps(Context context, String number,  String message) {
        this.number = number;
        this.context = context;

        displayLocation(retrieveLatFromAnswer(message), retrieveLonFromAnswer(message));
    }

    private float retrieveLatFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Float.parseFloat(parts[2].split(SmsBuilder.DATA_SEPARATOR)[0]);
    }
    private float retrieveLonFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Float.parseFloat(parts[2].split(SmsBuilder.DATA_SEPARATOR)[1]);
    }

    private void displayLocation(float lat, float lon){
        Intent service = new Intent(context, LocationGpsService.class);
        service.putExtra(CodeGetGps.EXTRA_LAT, lat);
        service.putExtra(CodeGetGps.EXTRA_LON, lon);

        context.startService(service);
    }
}
