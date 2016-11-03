package com.wkdgusdn3.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.wkdgusdn3.broadcastreceiver.ReceiverClear;
import com.wkdgusdn3.broadcastreceiver.ReceiverMusicPlay;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundDown;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundMute;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundUp;
import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.soundcontroller.R;

public class SoundService extends Service {

    Context context;
    int[] functionId = {R.id.function1, R.id.function2, R.id.function3, R.id.function4};

    @Override
    public void onCreate() {

        InfoManager.setData(getApplicationContext());
        context = getApplicationContext();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            notificationJellyBean();
        } else {
            notificationIceCreamSandwich();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    void notificationIceCreamSandwich() {
        NotificationManager notificationManager;
        Notification notification;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification(com.wkdgusdn3.soundcontroller.R.drawable.sound_icon, "soundcontroller", System.currentTimeMillis());

        if (!InfoManager.boolean_icon) {
            notification.priority = Notification.PRIORITY_MIN;
        }
        notification.flags = Notification.FLAG_NO_CLEAR;

        RemoteViews views = new RemoteViews(this.getPackageName(), com.wkdgusdn3.soundcontroller.R.layout.sound_notification);
        views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_icon, com.wkdgusdn3.soundcontroller.R.drawable.sound_icon);

        for (int i = 0; i < 4; i++) {
            switch (InfoManager.functions[i]) {
                case 0:
                    setMusicPlay(views, functionId[i]);
                    break;
                case 1:
                    setSoundUp(views, functionId[i]);
                    break;
                case 2:
                    setSoundDown(views, functionId[i]);
                    break;
                case 3:
                    setSoundMute(views, functionId[i]);
                    break;
            }
        }

        notification.contentView = views;
        notificationManager.notify(3, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void notificationJellyBean() {
        NotificationManager notificationManager;
        Notification notification;
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setSmallIcon(R.drawable.sound_icon);
        builder.setTicker("SoundController");
        if (!InfoManager.boolean_icon) {
            builder.setPriority(Notification.PRIORITY_MIN);
        }
        builder.setOngoing(true);
        notification = builder.build();

        RemoteViews views = new RemoteViews(this.getPackageName(), com.wkdgusdn3.soundcontroller.R.layout.sound_notification);
        views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_icon, com.wkdgusdn3.soundcontroller.R.drawable.sound_icon);

        for (int i = 0; i < 4; i++) {
            switch (InfoManager.functions[i]) {
                case 0:
                    setMusicPlay(views, functionId[i]);
                    break;
                case 1:
                    setSoundUp(views, functionId[i]);
                    break;
                case 2:
                    setSoundDown(views, functionId[i]);
                    break;
                case 3:
                    setSoundMute(views, functionId[i]);
                    break;
                case 4:
                    break;
            }
        }

        notification.contentView = views;
        notificationManager.notify(3, notification);
    }

    void setMusicPlay(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.music_play);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundUp(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundUp.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.sound_up);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundDown(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundDown.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.sound_down);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundMute(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundMute.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.sound_mute);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setClear(RemoteViews views, int id) {
        Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
        PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
        views.setImageViewResource(id, R.drawable.clear);
        views.setOnClickPendingIntent(id, pendingIntent_clear);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
