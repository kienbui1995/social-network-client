package com.joker.hoclazada.Ultil;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joker on 2/14/17.
 */

public class PutParamFacebook {
    public String name;
    public PutParamFacebook(AccessToken accessToken, Bundle bundle, final String method) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
               if (method == "name"){
                    name=getName(object);
                   Log.d("tennguoi", name);
               }else {
                   if (method == "id"){
                       getId(object);
                       Log.d("tennguoi", getId(object));
                   }
               }
            }
        });
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }
    public String getStringName(){
//        String names = name;
        return name;
    }
    public String getName(JSONObject objects){
        String name ="";
        try {
            name = objects.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    public String getId(JSONObject object){
        String id ="";
        try {
            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }


}
