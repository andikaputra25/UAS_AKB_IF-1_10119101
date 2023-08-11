package com.andikaputra.uasakbif_110119101;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Firebase Notification";

    private void sendNotification(String title, String messageBody){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent,PendingIntent.FLAG_IMMUTABLE);

        String channelsId = "Firebase Notification Cancel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(this, channelsId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelsId,
                    "Judul Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

        }
        notificationManager.notify(0,notificationBuilder.build());

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        String source = message.getFrom();
        String title =message.getNotification().getTitle();
        String content = message.getNotification().getBody();
        Log.d(TAG, "From :" + source);
        if (message.getNotification() != null){
            Log.d(TAG,"Message Notification Title : " + title);
            Log.d(TAG,"Message Notification Content : " + content);
        }
    }
}
