package com.joker.thanglong.Model;

import android.content.Context;

import com.android.volley.Response;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityNotification;
import Entity.EntityUserProfile;

/**
 * Created by joker on 6/7/17.
 */

public class NotificationModel {
    private Context context;

    public NotificationModel(Context context) {
        this.context = context;
    }

    public NotificationModel() {
    }
    public void getNotification(final VolleyCallBackNotification callback){
        final ArrayList<EntityNotification> notifications = new ArrayList<>();
        VolleySingleton.getInstance(context).get("notifications", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityNotification entityNotification = new EntityNotification();
                        if (jsonObject.has("actor")){
                            JSONObject jsonObjectActor = jsonObject.getJSONObject("actor");
                            EntityNotification.Actor actor = entityNotification.new Actor();
                            actor.setId(jsonObjectActor.getInt("id"));
                            if (jsonObjectActor.has("avatar"))actor.setAvatar(jsonObjectActor.getString("avatar"));
                            actor.setFullName(jsonObjectActor.getString("full_name"));
                            actor.setUsename(jsonObjectActor.getString("username"));
                            entityNotification.setActor(actor);
                        }
                        if (jsonObject.has("last_user")){
                            JSONObject jsonObjectActor = jsonObject.getJSONObject("last_user");
                            EntityNotification.Actor last_user = entityNotification.new Actor();
                            last_user.setId(jsonObjectActor.getInt("id"));
                            if (jsonObjectActor.has("avatar"))last_user.setAvatar(jsonObjectActor.getString("avatar"));
                            last_user.setFullName(jsonObjectActor.getString("full_name"));
                            last_user.setUsename(jsonObjectActor.getString("username"));
                            entityNotification.setLast_user(last_user);
                        }
                        if (jsonObject.has("last_post")){
                            JSONObject jsonObjectPost = jsonObject.getJSONObject("last_post");
                            EntityNotification.Post last_post = entityNotification.new Post();
                            last_post.setId(jsonObjectPost.getInt("id"));
                            last_post.setMessage(jsonObjectPost.getString("message"));
                            if (jsonObjectPost.has("photo")) last_post.setPhoto(jsonObjectPost.getString("photo"));
                            if (jsonObjectPost.has("owner")){
                                JSONObject jsonOwner = jsonObjectPost.getJSONObject("owner");
                                EntityUserProfile profile = new EntityUserProfile();
                                profile.setFull_name(jsonOwner.getString("full_name"));
                                profile.setuID(jsonOwner.getInt("id"));
                                entityNotification.setUser(profile);
                            }
                            entityNotification.setLast_post(last_post);
                        }
                        if (jsonObject.has("last_comment")){
                            JSONObject jsonObjectComment = jsonObject.getJSONObject("last_comment");
                            EntityNotification.Comment last_comment = entityNotification.new Comment();
                            last_comment.setId(jsonObjectComment.getInt("id"));
                            last_comment.setMessage(jsonObjectComment.getString("message"));
                            entityNotification.setLast_comment(last_comment);
                        }
                        entityNotification.setId(jsonObject.getInt("id"));
                        entityNotification.setActionId(jsonObject.getInt("action"));
                        if (jsonObject.has("total_action")) entityNotification.setTotalAction(jsonObject.getInt("total_action"));
                        entityNotification.setUpdatedAt(jsonObject.getLong("updated_at"));
                        if (jsonObject.has("seen")) entityNotification.setSeen(jsonObject.getLong("seen"));
                        notifications.add(entityNotification);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(notifications);
            }
        },context);
    }

    public interface VolleyCallBackNotification{
        void onSuccess(ArrayList<EntityNotification> notifications);
    }
}
