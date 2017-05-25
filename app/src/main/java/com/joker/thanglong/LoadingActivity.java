package com.joker.thanglong;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import Entity.EntitySetting;
import io.realm.Realm;

public class LoadingActivity extends AwesomeSplash {
    Realm realm;
    EntitySetting entitySetting;
    @Override
    public void initSplash(ConfigSplash configSplash) {
        /* you don't have to override every property */
        configSplash.setBackgroundColor(R.color.bgToolbar); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.ic_facebook_logo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInDown); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Title
        configSplash.setTitleSplash("Thăng Long Social");
        configSplash.setTitleTextColor(R.color.colorWhite);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
//        configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located
    }

    @Override
    public void animationsFinished() {
//        checkLoad =true;
        if (ProfileInstance.getProfileInstance(this).getProfile() ==null)
        {
            finish();
            startActivity(new Intent(this,SignUpIn.class));
        }else {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    public void initSetting(){
        entitySetting = new EntitySetting();
        entitySetting.setNewsfeed(1);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(entitySetting);
            }
        });
    }

    public void initRealm(){
        realm.init(this);
        realm = Realm.getDefaultInstance();
    }


}
