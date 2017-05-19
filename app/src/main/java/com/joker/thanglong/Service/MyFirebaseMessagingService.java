package com.joker.thanglong.Service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.joker.thanglong.CommentPostFullActivity;
import com.joker.thanglong.R;

import static android.R.attr.id;

/**
 * Created by joker on 5/8/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(this, CommentPostFullActivity.class);
        intent.putExtra("idPost", Integer.parseInt(remoteMessage.getData().get("id")));
        Log.d("idPost",remoteMessage.getData().get("id"));
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*build the notification here this is only supported for API 11. Since we've targeted API 11 there will be no problem on this*/
        NotificationCompat.Builder notify = new NotificationCompat.Builder(this)
                .setContentTitle("Thang long ")
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.heart)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.heart))
                .setVibrate(new long[]{500,500,500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setTicker("Echo: your file is ready for download")
                .setContentIntent(pIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT<16){
            /*build notification for HoneyComb to ICS*/
            notificationManager.notify(id, notify.getNotification());
        }if(Build.VERSION.SDK_INT>15){
            /*Notification for Jellybean and above*/
            notificationManager.notify(id, notify.build());
        }

    }
}
