package com.nataliajastrzebska.insidespy.codes;

import android.content.Context;
import android.content.Intent;

import com.nataliajastrzebska.insidespy.services.LocationBtsService;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class AnsGet {

    Context context;
    String number;

    public AnsGet(Context context, String number, String message) {
        this.context = context;
        this.number = number;

        displayLocation(retrieveCidFromAnswer(message),
                retrieveLacFromAnswer(message),
                retrieveMccFromAnswer(message),
                retrieveMncFromAnswer(message));
    }

    private int retrieveCidFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Integer.parseInt(parts[2].split(SmsBuilder.DATA_SEPARATOR)[0]);
    }

    private int retrieveLacFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Integer.parseInt(parts[2].split(SmsBuilder.DATA_SEPARATOR)[1]);
    }

    private int retrieveMccFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Integer.parseInt(parts[2].split(SmsBuilder.DATA_SEPARATOR)[2]);
    }

    private int retrieveMncFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Integer.parseInt(parts[2].split(SmsBuilder.DATA_SEPARATOR)[3]);
    }

    private void displayLocation(int cid, int lac, int mcc, int mnc){
        Intent service = new Intent(context, LocationBtsService.class);
        service.putExtra(CodeGet.EXTRA_CID, cid);
        service.putExtra(CodeGet.EXTRA_LAC, lac);
        service.putExtra(CodeGet.EXTRA_MCC, mcc);
        service.putExtra(CodeGet.EXTRA_MNC, mnc);

        context.startService(service);
    }
}
