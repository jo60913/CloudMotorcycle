package com.example.cloudclient.clients;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by jo on 2017/4/15.
 */

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent I = new Intent(context,Reservation.class);
        PendingIntent PI = PendingIntent.getActivity(context,0,I,0);
        Notification NC = new NotificationCompat.Builder(context)
                .setContentTitle("今日預約")
                .setContentText("點擊查看今天預約")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.reservation)
                .setContentIntent(PI)
                .setAutoCancel(true)
                .build();
        manager.notify(0,NC);
        Log.d("Notification","notitfication show");
    }
}
