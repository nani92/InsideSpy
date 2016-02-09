package com.nataliajastrzebska.insidespy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.RemoteViews;

/**
 * Created by nataliajastrzebska on 07/02/16.
 */
public class SendGetGpsWidget extends AppWidgetProvider {
    public static String ACTION = "action";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        ComponentName thisWidget = new ComponentName(context, SendGetGpsWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                    R.layout.send_get_gps_widget);

            Intent intent = new Intent(context, SendGetGpsWidget.class);
            intent.setAction(ACTION);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            remoteView.setOnClickPendingIntent(R.id.sendGetGpsContainer, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteView);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION)) {
            sendSMS("+48510231913", "spy_getgps()");
        }
    }

    private void sendSMS(String number, String body){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, body, null, null);
    }
}
