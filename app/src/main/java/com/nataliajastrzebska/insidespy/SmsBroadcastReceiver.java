package com.nataliajastrzebska.insidespy;

/**
 * Created by nataliajastrzebska on 29/01/16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.nataliajastrzebska.insidespy.codes.AnsGet;
import com.nataliajastrzebska.insidespy.codes.AnsGetGps;
import com.nataliajastrzebska.insidespy.codes.Code;
import com.nataliajastrzebska.insidespy.codes.CodeGet;
import com.nataliajastrzebska.insidespy.codes.CodeGetGps;
import com.nataliajastrzebska.insidespy.contact.Contact;
import com.nataliajastrzebska.insidespy.contact.ContactDataSource;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;

import java.util.List;


public class SmsBroadcastReceiver extends BroadcastReceiver {

    static final String SMS_BUNDLE = "pdus";

    ContactDataSource contactDataSource;
    List<Contact> contactList;

    Context context;
    String number;

    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (doExtrasExist(intent.getExtras())) {

            populateAllowedToSpyNumbersList();
            Object[] sms = getSmsObjectFromExtras(intent.getExtras());

            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = getSmsMessageFromBundle(i, intent.getExtras(), sms);

                String smsBody = smsMessage.getMessageBody().toString();
                number = smsMessage.getOriginatingAddress();

                if(isNumberAllowedToSpy(number) && isMessageRequestCode(smsBody)){
                    proceedWithRequestCodeToGetAnswerContent(Code.fromString(smsBody.substring(SmsBuilder.CODE_START.length())), number);
                }
                else if (isMessageAnswer(smsBody)){
                    proceedWithAnswer(smsBody);
                }

            }
        }
    }

    private boolean doExtrasExist(Bundle bundle) {
        return bundle != null;
    }

    private void populateAllowedToSpyNumbersList() {
        contactDataSource = new ContactDataSource(context);
        contactDataSource.open();

        contactList = contactDataSource.getContactsSpyOnMe();

        contactDataSource.close();
    }

    private Object[] getSmsObjectFromExtras(Bundle bundle) {
        return  (Object[]) bundle.get(SMS_BUNDLE);
    }

    private String getFormat(Bundle bundle) {
        return bundle.getString("format");
    }

    private SmsMessage getSmsMessageFromBundle(int iterator, Bundle bundle, Object[] sms) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = getFormat(bundle);

            return SmsMessage.createFromPdu((byte[]) sms[iterator], format);
        }

        return SmsMessage.createFromPdu((byte[]) sms[iterator]);
    }

    private Boolean isNumberAllowedToSpy(String number){
        for(Contact contact : contactList) {
            if (number.equals(contact.getNumber())) {
                return true;
            }
        }
        return false;
    }

    private Boolean isMessageRequestCode(String message){
        if (message.startsWith(SmsBuilder.CODE_START))
            return true;
        return false;
    }

    private Boolean isMessageAnswer(String message){
        if (message.startsWith(SmsBuilder.ANSWER_START))
            return true;
        return false;
    }

    private void proceedWithRequestCodeToGetAnswerContent(Code code, String number){
        switch (code){
            case GET:
                new CodeGet(context, number);
                break;
            case GETGPS:
                new CodeGetGps(context, number);
                break;
        }
    }

    private void proceedWithAnswer(String answer){
        switch (retrieveCodeFromAnswer(answer)){
            case GET:
                new AnsGet(context, number, answer);
                break;
            case GETGPS:
                new AnsGetGps(context, number, answer);
                break;
        }
    }

    private Code retrieveCodeFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Code.fromString(parts[1]);
    }
}
