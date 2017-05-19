package com.joker.thanglong.Ultil;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.R;

import org.json.JSONException;
import org.json.JSONObject;

import Entity.EntityUserProfile;

/**
 * Created by joker on 4/18/17.
 */

public class ApiHelper {
    public EntityUserProfile entityUserProfile;

    public void setEntityUserProfile(EntityUserProfile entityUserProfile) {
        this.entityUserProfile = entityUserProfile;
    }

    public ApiHelper() {
    }

    public void requestInfoUser(String Uid, Activity activity){
        VolleyHelper volleyHelper;
        final EntityUserProfile profile = new EntityUserProfile();
        volleyHelper = new VolleyHelper(activity,activity.getResources().getString(R.string.url));
        volleyHelper.get("users/"+Uid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject infoUser;
//                Log.d("getInfoUser",response.getJSONObject("data").getString("full_name"));
                try {
                    infoUser = response.getJSONObject("data");
                    profile.setFull_name(infoUser.getString("full_name"));
                    profile.setuID(infoUser.getString("id"));
                    profile.setUserName(infoUser.getString("username"));
                    setEntityUserProfile(profile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getInfoUser",VolleyHelper.checkErrorCode(error)+"");
            }
        });
    }
    public EntityUserProfile getInfoUser(){
        return entityUserProfile;
    }
}
