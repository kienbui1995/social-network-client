package com.joker.thanglong.Ultil;

import android.content.Context;

import Entity.EntityUserProfile;
import io.realm.Realm;

/**
 * Created by joker on 5/11/17.
 */

public class ProfileInstance {
    private static ProfileInstance profileInstance = null;
    static Realm realm;
    private EntityUserProfile profile;
    private Context context;
    private ProfileInstance(Context context){
        this.context =context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }
    public static ProfileInstance getProfileInstance(Context context){

        if(profileInstance == null)
        {
            profileInstance = new ProfileInstance(context);
        }
        return profileInstance;
    }
    public EntityUserProfile getProfile(){
        profile = realm.where(EntityUserProfile.class).findFirst();
        return profile;
    }

    public boolean checkLogin(){
        profile = realm.where(EntityUserProfile.class).findFirst();
        if (profile ==null)
        {
            return false;
        }else {
            return true;
        }
    }

}
