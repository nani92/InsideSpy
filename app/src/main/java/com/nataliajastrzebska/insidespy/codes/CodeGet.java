package com.nataliajastrzebska.insidespy.codes;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.nataliajastrzebska.insidespy.helpers.BtsLocation;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class CodeGet {


    public static final String EXTRA_CID = "cid";
    public static final String EXTRA_LAC = "lac";
    public static final String EXTRA_MCC = "mcc";
    public static final String EXTRA_MNC = "mnc";

    Context context;
    String number;

    public CodeGet(Context context, String number) {
        this.context = context;
        this.number = number;

        BtsLocation location = getBtsLocation();
        SmsBuilder.sendAnswerSms(number,Code.GET, getBtsLocationBody(location));
    }

    private BtsLocation getBtsLocation(){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();

        return new BtsLocation(getCidForCellLocation(gsmCellLocation),
                getLacForCellLocation(gsmCellLocation),
                getMccFromNetworkOperator(getMccFromNetworkOperator(telephonyManager.getNetworkOperator())),
                getMncFromNetworkOperator(getMncFromNetworkOperator(telephonyManager.getNetworkOperator())));
    }

    private int getCidForCellLocation(GsmCellLocation gsmCellLocation){
        return gsmCellLocation.getCid();
    }

    private int getLacForCellLocation(GsmCellLocation gsmCellLocation){
        return gsmCellLocation.getLac();
    }

    private String getMccFromNetworkOperator(String networkOperator){
        return networkOperator.substring(0, 3);
    }

    private String getMncFromNetworkOperator(String networkOperator){
        return networkOperator.substring(3);
    }

    private String getBtsLocationBody(BtsLocation location) {
        return location.getCid() + SmsBuilder.DATA_SEPARATOR
                + location.getLac() + SmsBuilder.DATA_SEPARATOR
                + location.getMcc() + SmsBuilder.DATA_SEPARATOR
                + location.getMnc();
    }

}
