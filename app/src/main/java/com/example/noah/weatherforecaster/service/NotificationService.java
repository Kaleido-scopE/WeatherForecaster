package com.example.noah.weatherforecaster.service;

import android.app.*;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.example.noah.weatherforecaster.R;

public class NotificationService extends IntentService {
    private static final String TAG = "NotificationService";

    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "There is a new Intent");

        String textStr = intent.getStringExtra("text");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this)
                                                .setSmallIcon(R.drawable.app_icon)
                                                .setContentTitle(textStr)
                                                .setContentText(getString(R.string.app_name))
                                                .setWhen(System.currentTimeMillis())
                                                .setDefaults(Notification.DEFAULT_VIBRATE)//设置震动
                                                .setChannelId(PUSH_CHANNEL_ID)
                                                .setAutoCancel(true)
                                                .build();

        if (notificationManager != null)
            notificationManager.notify(0, notification);
    }

    /**
     * 设置定时推送服务
     * @param context 调用者context
     * @param isOn 标记是否打开推送服务
     * @param notificationStr 推送字符串
     */
    public static void setServiceAlarm(Context context, boolean isOn, String notificationStr) {
        Intent contentIntent = new Intent(context, NotificationService.class);
        contentIntent.putExtra("text", notificationStr);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (isOn) {
            Log.i(TAG, "activated");
            try {
                pendingIntent.send();
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Log.i(TAG, "canceled");
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

}
