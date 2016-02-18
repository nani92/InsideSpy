package com.nataliajastrzebska.insidespy.codes;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by nataliajastrzebska on 18/02/16.
 */
public class CodeVibrate {

    private Context context;
    private long defaultVibrationTime = 100;

    public CodeVibrate(Context context) {
        this.context = context;
        vibrate(this.defaultVibrationTime);
    }

    private void vibrate(long time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }
}
