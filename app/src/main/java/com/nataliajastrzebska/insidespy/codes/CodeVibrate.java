package com.nataliajastrzebska.insidespy.codes;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by nataliajastrzebska on 18/02/16.
 */
public class CodeVibrate {

    private Context context;
    private long defaultVibrationTime = 300;

    public CodeVibrate(Context context) {
        this.context = context;

        vibrate(this.defaultVibrationTime);
    }

    public CodeVibrate(long customTime, Context context) {
        this.context = context;

        vibrate(customTime);
    }

    private void vibrate(long time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    public static long getCustomVibrationTimeFromSms(String sms) {
        return Long.parseLong(sms.substring(sms.indexOf("(")+1, sms.lastIndexOf(")")));
    }
}
