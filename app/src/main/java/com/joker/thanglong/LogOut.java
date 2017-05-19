package com.joker.thanglong;

import Entity.EntityUserProfile;
import io.realm.Realm;

/**
 * Created by joker on 4/21/17.
 */

public class LogOut {
    public LogOut(Realm realm) {
        EntityUserProfile entityUserProfile = realm.where(EntityUserProfile.class).findFirst();

    }
}
