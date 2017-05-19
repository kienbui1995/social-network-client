package com.joker.thanglong.Ultil;

import android.app.Activity;

import Entity.EntityUserProfile;
import io.realm.Realm;

/**
 * Created by joker on 5/9/17.
 */

public class DatabaseUtil  {
    Realm realm;
    EntityUserProfile entityUserProfile;

    public DatabaseUtil() {
    }

    public DatabaseUtil(Activity activity) {
        Realm.init(activity);
        //Khoi tao doi tuong Realm
        realm = Realm.getDefaultInstance();
    }

    public EntityUserProfile getProfile(){
        entityUserProfile = realm.where(EntityUserProfile.class).findFirst();
        return entityUserProfile;
    }
}
