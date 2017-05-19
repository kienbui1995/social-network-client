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
    private ProfileInstance(){

    }
    public static ProfileInstance getProfileInstance(Context context){

        if (realm ==null){
            Realm.init(context);
            realm = Realm.getDefaultInstance();
        }
        if(profileInstance == null)
        {
            profileInstance = new ProfileInstance();
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
