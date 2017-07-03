package com.joker.thanglong.Model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import Entity.EntityChannel;
import Entity.EntityNotificationChannel;

/**
 * Created by joker on 6/30/17.
 */

public class ChannelModel {
    private Context context;
    private EntityChannel entityChannel;
    public ChannelModel(Context context) {
        this.context = context;
    }
    public void getListChannel(final VolleyCallbackListChannel callback){
        final ArrayList<EntityChannel> listChannel = new ArrayList<>();
        VolleySingleton.getInstance(context).get("channels", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        EntityChannel entityChannel = new EntityChannel();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        entityChannel.setId(jsonObject.getInt("id"));
                        entityChannel.setName(jsonObject.getString("name"));
                        if (jsonObject.has("short_name")) entityChannel.setShort_name(jsonObject.getString("short_name"));
                        if (jsonObject.has("avatar")) entityChannel.setAvatar(jsonObject.getString("avatar"));
                        if (jsonObject.has("followers")) entityChannel.setTotalFollower(jsonObject.getInt("followers"));
                        if (jsonObject.has("cover")) entityChannel.setCover(jsonObject.getString("cover"));
                        if (jsonObject.has("created_at")) entityChannel.setCreated_at(jsonObject.getLong("created_at"));
                        if (jsonObject.has("updated_at")) entityChannel.setUpdated_at(jsonObject.getLong("updated_at"));
                        if (jsonObject.has("description")) entityChannel.setDescription(jsonObject.getString("description"));
                        if (jsonObject.has("is_admin")) entityChannel.setIs_admin(jsonObject.getBoolean("is_admin"));
                        if (jsonObject.has("is_followed")) entityChannel.setIs_followed(jsonObject.getBoolean("is_followed"));
                        listChannel.add(entityChannel);
                    }
                    callback.onSuccess(listChannel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },context);

    }
    public void getMyChannel(final VolleyCallbackListChannel callback){
        final ArrayList<EntityChannel> listChannel = new ArrayList<>();
        VolleySingleton.getInstance(context).get("users/"+ ProfileInstance.getProfileInstance(context).getProfile().getuID()+"/channels", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        EntityChannel entityChannel = new EntityChannel();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        entityChannel.setId(jsonObject.getInt("id"));
                        entityChannel.setName(jsonObject.getString("name"));
                        if (jsonObject.has("short_name")) entityChannel.setShort_name(jsonObject.getString("short_name"));
                        if (jsonObject.has("avatar")) entityChannel.setAvatar(jsonObject.getString("avatar"));
                        if (jsonObject.has("followers")) entityChannel.setTotalFollower(jsonObject.getInt("followers"));
                        if (jsonObject.has("cover")) entityChannel.setCover(jsonObject.getString("cover"));
                        if (jsonObject.has("created_at")) entityChannel.setCreated_at(jsonObject.getLong("created_at"));
                        if (jsonObject.has("updated_at")) entityChannel.setUpdated_at(jsonObject.getLong("updated_at"));
                        if (jsonObject.has("description")) entityChannel.setDescription(jsonObject.getString("description"));
                        if (jsonObject.has("is_admin")) entityChannel.setIs_admin(jsonObject.getBoolean("is_admin"));
                        if (jsonObject.has("is_followed")) entityChannel.setIs_followed(jsonObject.getBoolean("is_followed"));
                        listChannel.add(entityChannel);
                    }
                    callback.onSuccess(listChannel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },context);

    }
    public void getSingleChannel(int id,final VolleyCallbackChannel callback){

        VolleySingleton.getInstance(context).get("channels/"+ id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                EntityChannel entityChannel = new EntityChannel();
                try {
                        JSONObject jsonObject = response.getJSONObject("data");
                        entityChannel.setId(jsonObject.getInt("id"));
                        entityChannel.setName(jsonObject.getString("name"));
                    if (jsonObject.has("short_name")) entityChannel.setShort_name(jsonObject.getString("short_name"));
                        if (jsonObject.has("avatar")) entityChannel.setAvatar(jsonObject.getString("avatar"));
                        if (jsonObject.has("followers")) entityChannel.setTotalFollower(jsonObject.getInt("followers"));
                        if (jsonObject.has("cover")) entityChannel.setCover(jsonObject.getString("cover"));
                        if (jsonObject.has("created_at")) entityChannel.setCreated_at(jsonObject.getLong("created_at"));
                        if (jsonObject.has("updated_at")) entityChannel.setUpdated_at(jsonObject.getLong("updated_at"));
                        if (jsonObject.has("description")) entityChannel.setDescription(jsonObject.getString("description"));
                        if (jsonObject.has("is_admin")) entityChannel.setIs_admin(jsonObject.getBoolean("is_admin"));
                        if (jsonObject.has("is_followed")) entityChannel.setIs_followed(jsonObject.getBoolean("is_followed"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(entityChannel);
            }
        },context);
    }

    public void editInfoChannel(int idChannel, EntityChannel entityChannel){
        Map parrams = entityChannel.toMap(entityChannel);
        VolleySingleton.getInstance(context).put(context,"channels/" + idChannel, new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        });
    }

    public void followChannel(int idChannel, final PostModel.VolleyCallBackJson callback){
        VolleySingleton.getInstance(context).post(context, "channels/" + idChannel + "/followers", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void unfollowChannel(int idChannel, final PostModel.VolleyCallBackJson callback){
        VolleySingleton.getInstance(context).delete(context, "channels/" + idChannel + "/followers", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Hủy theo dõi thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void writeNotification(EntityNotificationChannel entityNotificationChannel, final PostModel.VolleyCallBackCheck callback){
        Map parrams = entityNotificationChannel.toMap(entityNotificationChannel);
        VolleySingleton.getInstance(context).post(context, "channels/" + entityNotificationChannel.getChannel().getId()
                + "/channel_notifications", new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }

    public interface VolleyCallbackListChannel{
        void onSuccess(ArrayList<EntityChannel> listChannel);
    }
    public interface VolleyCallbackChannel{
        void onSuccess(EntityChannel entityChannel);
    }
}
