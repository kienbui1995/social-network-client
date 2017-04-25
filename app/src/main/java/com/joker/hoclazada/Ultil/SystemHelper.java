package com.joker.hoclazada.Ultil;

import Entity.EntityUserProfile;
import io.realm.Realm;

/**
 * Created by joker on 4/17/17.
 */

public class SystemHelper {
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
    public static boolean isLogin(Realm realm)
    {
        EntityUserProfile profile = new EntityUserProfile();
        profile = realm.where(EntityUserProfile.class).findFirst();
        if (profile ==null)
        {
           return false;
        }else {
            return true;
        }
    }

}
