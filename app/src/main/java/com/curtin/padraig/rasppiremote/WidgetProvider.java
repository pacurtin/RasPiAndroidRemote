package com.curtin.padraig.rasppiremote;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by padraig on 19/02/2017.
 */

public class WidgetProvider extends AppWidgetProvider {
    public static String ACTION_WIDGET_ON = "Lights turning on";
    public static String ACTION_WIDGET_OFF = "Lights turning off";
    private String serverUrl = "http://109.255.243.13:8080"; //put your server URL here

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent active = new Intent(context, WidgetProvider.class);
        active.setAction(ACTION_WIDGET_ON);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.onButton, actionPendingIntent);

        active = new Intent(context, WidgetProvider.class);
        active.setAction(ACTION_WIDGET_OFF);
        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.offButton, actionPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_WIDGET_ON)) {
            Log.i("onReceive", ACTION_WIDGET_ON);
            new RestPostUtil().execute(serverUrl + "/lightsOn");
        } else if (intent.getAction().equals(ACTION_WIDGET_OFF)) {
            Log.i("onReceive", ACTION_WIDGET_OFF);
            new RestPostUtil().execute(serverUrl + "/lightsOff");
        } else {
            super.onReceive(context, intent);
        }
    }

}
