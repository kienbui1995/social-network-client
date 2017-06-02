package com.joker.thanglong.Service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.joker.thanglong.CommentPostFullActivity;
import com.joker.thanglong.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by joker on 5/8/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String token = PreferenceManager.getDefaultSharedPreferences(this).getString("token","");
        if(token != null){
//            DialogUtil.showPopUpAlert(getApplicationContext());
            int id = Integer.parseInt(remoteMessage.getData().get("id"));
            Intent intent = new Intent(this, CommentPostFullActivity.class);
            intent.putExtra("idPost", id);
            intent.putExtra("type", 2);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bitmap = getBitmapFromURL("https://firebasestorage.googleapis.com/v0/b/hoclazada.appspot.com/o/images%2Favatar%2Fsmall%2F1495434748_null?alt=media&token=f845459a-9f67-4109-a236-6c95cf458d0d");
        /*build the notification here this is only supported for API 11. Since we've targeted API 11 there will be no problem on this*/
            NotificationCompat.Builder notify = new NotificationCompat.Builder(this)
                    .setContentTitle("Thang long ")
                    .setContentText(remoteMessage.getData().get("message"))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.heart)
                    .setLargeIcon(bitmap)
                    .setVibrate(new long[]{500,500,500})
//                    .setOngoing(true)
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
    public Bitmap getBitmapFromURL(String strURL) {
        Bitmap myBitmap = null;
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return myBitmap;
    }
}
