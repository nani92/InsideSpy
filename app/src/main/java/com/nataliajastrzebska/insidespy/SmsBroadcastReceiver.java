package com.nataliajastrzebska.insidespy;

/**
 * Created by nataliajastrzebska on 29/01/16.
 */
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import java.util.List;


public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    String CODE_START = "spy_";
    String ANSWER_START = "spyAns_";
    ContactDataSource contactDataSource;
    List<Contact> contactList;

    Context context;

    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            for (int i = 0; i < sms.length; ++i) {
                populateList();

                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String number = smsMessage.getOriginatingAddress();

                if(isNumberAllowedToSpy(number) && isMessageCode(smsBody)){
                    String answer = ANSWER_START + proceedWithRequestCodeToGetAnswerContent(Code.fromString(smsBody.substring(4)));
                    sendSMS(number, answer);
                }
                else if (isMessageAnswer(smsBody)){
                    proceedWithAnswer(smsBody);
                }

            }
        }
    }

    private void populateList() {
        contactDataSource = new ContactDataSource(context);
        contactDataSource.open();
        contactList = contactDataSource.getAllContacts();
        contactDataSource.close();
    }

    private Boolean isNumberAllowedToSpy(String number){
        for(Contact contact : contactList) {
            if (number.equals(contact.getNumber())) {
                return true;
            }
        }
        return false;
    }

    private Boolean isMessageCode(String message){
        if (message.length() > 5 && message.substring(0,4).equals(CODE_START))
            return true;
        return false;
    }

    private Boolean isMessageAnswer(String message){
        if (message.length() > 8 && message.substring(0,7).equals(ANSWER_START))
            return true;
        return false;
    }

    private String proceedWithRequestCodeToGetAnswerContent(Code code){
        switch (code){
            case GET:
                return code.toString() + "_" + proceedBtsGetLocation();
            case GETGPS:
                return proceedGetLocation();
            default:
                return "";
        }
    }

    private String proceedGetLocation() {

        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        if (android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                android.support.v4.content.ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            return Code.GETGPS.toString() + "_" + location.getLatitude() + ";" + location.getLongitude();
        }
        else {
            return "no location data";
        }
    }

    private void proceedWithAnswer(String answer){
        switch (retrieveCodeFromAnswer(answer)){
            case GET:
                displayLocation(retrieveCidFromAnswer(answer),
                        retrieveLacFromAnswer(answer),
                        retrieveMccFromAnswer(answer),
                        retrieveMncFromAnswer(answer));
                break;
            case GETGPS:
                displayLocation(retrieveLatFromAnswer(answer),
                        retrieveLonFromAnswer(answer));
                break;
            default:
                break;
        }
    }

    private Code retrieveCodeFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Code.fromString(parts[1]);
    }

    private int retrieveCidFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Integer.parseInt(parts[2].split(";")[0]);
    }

    private int retrieveLacFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Integer.parseInt(parts[2].split(";")[1]);
    }

    private int retrieveMccFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Integer.parseInt(parts[2].split(";")[2]);
    }

    private int retrieveMncFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Integer.parseInt(parts[2].split(";")[3]);
    }

    private float retrieveLatFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Float.parseFloat(parts[2].split(";")[0]);
    }
    private float retrieveLonFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Float.parseFloat(parts[2].split(";")[1]);
    }

    private String proceedBtsGetLocation(){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        return getCidForCellLocation(gsmCellLocation) + ";"
                + getLacForCellLocation(gsmCellLocation) + ";"
                + getMccFromNetworkOperator(telephonyManager.getNetworkOperator()) + ";"
                + getMncFromNetworkOperator(telephonyManager.getNetworkOperator());
    }

    private String getCidForCellLocation(GsmCellLocation gsmCellLocation){
        int cid = gsmCellLocation.getCid() & 0xffff;
        return String.valueOf(cid);
    }

    private String getLacForCellLocation(GsmCellLocation gsmCellLocation){
        int cid = gsmCellLocation.getLac() & 0xffff;
        return String.valueOf(cid);
    }

    private String getMccFromNetworkOperator(String networkOperator){
        return networkOperator.substring(0, 3);
    }

    private String getMncFromNetworkOperator(String networkOperator){
        return networkOperator.substring(3);
    }

    private void sendSMS(String number, String body){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, body, null, null);
    }

    private void displayLocation(int cid, int lac, int mcc, int mnc){
        Intent service = new Intent(context, LocationBtsService.class);
        service.putExtra("cid", cid);
        service.putExtra("lac", lac);
        service.putExtra("mcc", mcc);
        service.putExtra("mnc", mnc);
        context.startService(service);
    }

    private void displayLocation(float lat, float lon){
        Intent service = new Intent(context, LocationService.class);
        service.putExtra("lat", lat);
        service.putExtra("lon", lon);
        context.startService(service);
    }
}
