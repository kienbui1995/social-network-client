package com.joker.thanglong.Model;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.Ultil.PostUlti;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("findUser", VolleySingleton.getInstance(activity).checkErrorCode(error)+"");
            }
        });
    }
    public void getProfile(final PostUlti.VolleyCallBackJson callback){
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getInfoUser", VolleySingleton.getInstance(activity).checkErrorCode(error) + "");
            }
        });
    }

    public void getNewfeed(final PostUlti.VolleyCallbackListStatus callback,String type){
        final ArrayList<EntityStatus> items = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("news_feed" + "?limit=10"+type, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityStatus entityStatus = new EntityStatus();
                        entityStatus.setuId(Integer.parseInt(jsonObject.getString("userid")));
                        entityStatus.setContent(jsonObject.getString("message"));
                        entityStatus.setCreatedTime(Long.parseLong(jsonObject.getString("created_at")));
                        entityStatus.setIdStatus(Integer.parseInt(jsonObject.getString("id")));
                        entityStatus.setNameId(jsonObject.getString("full_name"));
                        entityStatus.setLike(Boolean.parseBoolean(jsonObject.getString("is_liked")));
                        entityStatus.setStatus(jsonObject.getInt("status"));
                        entityStatus.setCanEdit(jsonObject.getBoolean("can_edit"));
                        entityStatus.setCanDelete(jsonObject.getBoolean("can_delete"));
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("findUser",VolleySingleton.getInstance(activity).checkErrorCode(error)+"");
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
