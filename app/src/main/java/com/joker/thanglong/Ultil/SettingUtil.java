package com.joker.thanglong.Ultil;

import android.content.Context;

import Entity.EntitySetting;
import io.realm.Realm;

/**
 * Created by joker on 5/23/17.
 */

public class SettingUtil {
    private static SettingUtil  settingUtil = null;
    static Realm realm;
    private static EntitySetting entitySetting;
    private Context context;

    private SettingUtil(Context context){
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }
    public static SettingUtil getSettingUtil(Context context){
        if(settingUtil == null)
        {
            settingUtil = new SettingUtil(context);
            entitySetting = realm.where(EntitySetting.class).findFirst();
        }
        else {
            entitySetting = realm.where(EntitySetting.class).findFirst();
        }
        return settingUtil;
    }
    public String getNewsfeed(){
        String newsfeedSetting = null;
        switch (entitySetting.getNewsfeed()){
            case 1:
                newsfeedSetting = "&sort=-created_at";
                break;
            case 2:
                newsfeedSetting = "";
                break;
        }
        return newsfeedSetting;
    }
    public void setNewsfeed(int value){
        entitySetting = new EntitySetting();
        entitySetting.setNewsfeed(value);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(entitySetting);
            }
        });
    }
    public EntitySetting getSetting(){
        return entitySetting;
    }
}
