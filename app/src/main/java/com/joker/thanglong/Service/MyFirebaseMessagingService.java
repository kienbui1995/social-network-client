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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 5/8/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final int LIKE = 1;
    public static final int COMMENT = 2;
    public static final int MENTION = 4;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String token = PreferenceManager.getDefaultSharedPreferences(this).getString("token","");
        Map<String, String> notication = remoteMessage.getData();
        try {
            JSONObject actor = new JSONObject(notication.get("actor"));
            JSONObject post = new JSONObject(notication.get("last_post"));
            switch (Integer.parseInt(notication.get("action"))){
                case LIKE:
                    showNotification(post.getInt("id"),"Thông báo",actor.getString("full_name"),
                            actor.getString("avatar"),"like bài viết của bạn",
                            post.getString("message"), Integer.parseInt(notication.get("total_action")));
                    break;
                case COMMENT:
                    break;
                case MENTION:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Map<String,String> actor = splitString(remoteMessage.getData().get("actor"));
//        Map <String,Object> map1 = new HashMap<>();
//        map1.putAll(notication);
//        Map<String,String>  post = splitString(remoteMessage.getData().get("last_post"));
//        try {
//            Log.d("notifcation",new JSONObject(notication.get("actor")).getString("avatar"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if(token != null){
//
////            Object remoteMessage1 = remoteMessage.getData().get("data");
//            EntityNotification entityNotification = new EntityNotification();
//            entityNotification.setId(remoteMessage.getData().get("data").get);
//
////            DialogUtil.showPopUpAlert(getApplicationContext());
//



    }

    public void showNotification(int id,String title,String Actor,String avatar,String message,String postContent,int total){
        String and = null;
        if (total == 1){
            and = "";
        }else {
            and = " và " + (total -1)+ " người khác";
        }
        Intent intent = new Intent(this, CommentPostFullActivity.class);
        intent.putExtra("idPost", id);
        intent.putExtra("type", 2);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bitmap = getBitmapFromURL(avatar);
        /*build the notification here this is only supported for API 11. Since we've targeted API 11 there will be no problem on this*/
        NotificationCompat.Builder notify = new NotificationCompat.Builder(this)
                .setContentTitle("Thang long")
                .setContentText(Actor + and +" đã " + message + ": "+postContent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.heart)
                .setLargeIcon(bitmap)
                .setVibrate(new long[]{500,500,500})
//                .setOngoing(true)
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
    public Map<String,String>splitString(String value){
        value = value.substring(1, value.length()-1).replace("\"","");           //remove curly brackets
        String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
        Map<String,String> map = new HashMap<>();

        for(String pair : keyValuePairs)                        //iterate over the pairs
        {
            String[] entry = pair.split(":");                   //split the pairs to get key and value
            map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
        }
        return map;
    }
}
