package com.nataliajastrzebska.insidespy.helpers;

import android.telephony.SmsManager;

import com.nataliajastrzebska.insidespy.codes.Code;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class SmsBuilder {
    public static final String CODE_START = "spy_";
    public static final String ANSWER_START = "spyAns_";
    public static final String DATA_SEPARATOR = ";";
    public static final String PART_SEPARATOR = "_";

    private static void sendSMS(String number, String body){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, body, null, null);
    }

    public static void sendRequestCodeSms(String number, Code code) {
        sendSMS(number, CODE_START + code);
    }

    public static void sendAnswerSms(String number, Code code, String body) {
        sendSMS(number, ANSWER_START + code + PART_SEPARATOR + body);
    }
}
