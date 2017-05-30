package com.joker.thanglong.Model;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.joker.thanglong.Ultil.SettingUtil;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Entity.EntityFollow;
import Entity.EntityStatus;
import Entity.EntityUserProfile;
import Entity.EntityUserSearch;

/**
 * Created by joker on 5/12/17.
 */

public class UserModel {
    String uId;
    Activity activity;
    public UserModel(Activity activity, String uId){
        this.uId = uId;
        this.activity = activity;
    }
    public UserModel(Activity activity){
        this.activity = activity;
    }

    public void unFollow(){
        VolleySingleton.getInstance(activity).delete(activity,"users/" +uId + "/subscriptions", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("follow",response.toString());
            }
        });
    }

    public void Follow(){
        VolleySingleton.getInstance(activity).post(activity,"users/" +uId + "/subscriptions", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("follow",response.toString());
            }
        });
    }



    public void getListFollow(String type,final VolleyCallBackFollow callback){
        final ArrayList<EntityFollow> items = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("users/"+ uId+ "/" + type, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityFollow item = new EntityFollow();
                        item.setId(jsonObject.getString("id"));
                        item.setUsername(jsonObject.getString("username"));
                        item.setAvatar(jsonObject.getString("avatar"));
                        item.setFull_name(jsonObject.getString("full_name"));
                        item.setIs_followed(jsonObject.getBoolean("is_followed"));
                        items.add(item);
                    }
                    callback.onSuccess(items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },activity);
    }
    public void getProfile(final PostModel.VolleyCallBackJson callback){
        VolleySingleton.getInstance(activity).get("users/" + uId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject infoUser = response.getJSONObject("data");
                    callback.onSuccess(infoUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },activity);
    }

    public void getNewfeed(final PostModel.VolleyCallbackListStatus callback, String type){
        final ArrayList<EntityStatus> items = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("news_feed" + "?limit=10"+type+ SettingUtil.getSettingUtil(activity).getNewsfeed(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject jsonOwner = jsonObject.getJSONObject("owner");
                        EntityStatus entityStatus = new EntityStatus();
                        entityStatus.setuId(Integer.parseInt(jsonOwner.getString("id")));
                        entityStatus.setContent(jsonObject.getString("message"));
                        entityStatus.setCreatedTime(Long.parseLong(jsonObject.getString("created_at")));
                        entityStatus.setIdStatus(Integer.parseInt(jsonObject.getString("id")));
                        entityStatus.setNameId(jsonOwner.getString("full_name"));
                        entityStatus.setLike(Boolean.parseBoolean(jsonObject.getString("is_liked")));
                        entityStatus.setStatus(jsonObject.getInt("status"));
                        entityStatus.setCanEdit(jsonObject.getBoolean("can_edit"));
                        entityStatus.setCanDelete(jsonObject.getBoolean("can_delete"));
                        if (jsonOwner.has("avatar")){
                            entityStatus.setAvatar(jsonOwner.getString("avatar"));
                        }
                        if (jsonObject.has("photo")){
                            entityStatus.setImage(jsonObject.getString("photo"));
                        }
                        if (!jsonObject.isNull("comments"))
                        {
                            entityStatus.setNumberComment(jsonObject.getInt("comments"));
                        }
                        if (!jsonObject.isNull("likes"))
                        {
                            entityStatus.setNumberLike(jsonObject.getInt("likes"));
                        }
                        items.add(entityStatus);
                    }
                    callback.onSuccess(items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },activity);
    }

    public void search(CharSequence sequence,final VolleyCallBackSearch callback){
        final ArrayList<EntityUserSearch> listUser = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("find_user?name="+ sequence, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    if(!response.getJSONArray("data").equals("null")) {
                        //Value is not null
                        for (int i =0; i< jsonArray.length();i++)
                        {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            EntityUserSearch item = new EntityUserSearch();
                            item.setId(jsonObject.getString("id"));
                            item.setUsername(jsonObject.getString("username"));
                            item.setAvatar(jsonObject.getString("avatar"));
                            item.setFull_name(jsonObject.getString("full_name"));
                            item.setIs_followed(jsonObject.getBoolean("is_followed"));
                            listUser.add(item);
                        }
                    }else {
                        System.exit(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(listUser);
            }
        },activity);
    }

    public void updateAvatar(String large, String small, final PostModel.VolleyCallBackCheck callback){
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("large_avatar",large);
        parrams.put("avatar",small);
        VolleySingleton.getInstance(activity).put(activity,"users/" + uId, new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }

    public void updateUser(String full_name, String about, final PostModel.VolleyCallBackCheck callback){
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("full_name",full_name);
        parrams.put("about",about);
        VolleySingleton.getInstance(activity).put(activity,"users/" + uId, new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }

    public interface VolleyCallBackFollow {
        void onSuccess(ArrayList<EntityFollow> listFollow);
    }
    public interface VolleyCallBackProfileUser {
        void onSuccess(EntityUserProfile profile);
    }
    public interface VolleyCallBackSearch {
        void onSuccess(ArrayList<EntityUserSearch> listUser);
    }
}
