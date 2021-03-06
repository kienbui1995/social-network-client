package com.joker.thanglong.Ultil;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.Date;

import Entity.EntityUserProfile;
import io.realm.Realm;

/**
 * Created by joker on 4/17/17.
 */

public class SystemHelper extends Application{

    static Realm realm;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Vừa xong";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "Một phút trước";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " phút trước";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "Một giờ trước";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " giờ trước";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Hôm qua";
        } else {
            return convertTime(time);
        }
    }
    public static long getTimeStamp(){
        return System.currentTimeMillis()/1000;
    }
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    public static boolean isLogin()
    {
        realm = Realm.getDefaultInstance();
        EntityUserProfile profile = new EntityUserProfile();
        profile = realm.where(EntityUserProfile.class).findFirst();
        if (profile ==null)
        {
           return false;
        }else {
            return true;
        }
    }
    public static String convertTime(Long unixtime) {
        Date dateObject = new Date(unixtime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy hh:mmaa");
        return dateFormatter.format(dateObject);
    }

    public static String convertDate(Long unixtime) {
        Date dateObject = new Date(unixtime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
        return dateFormatter.format(dateObject);
    }

}
