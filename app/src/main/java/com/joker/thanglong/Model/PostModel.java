package com.joker.thanglong.Model;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Response;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Entity.EntityComment;
import Entity.EntityListLike;
import Entity.EntityStatus;

/**
 * Created by joker on 5/8/17.
 */

public class PostModel {
    EntityStatus entityStatus;
    Activity activity;
    EntityComment entityComment;
    int idPost;
    int uId;
    String type;

    public PostModel(Activity activity) {
        this.activity = activity;
    }

    public PostModel(Activity activity, int uId, String type) {
        entityStatus = new EntityStatus();
        this.activity=activity;
        this.uId=uId;
        this.type =type;
    }
    public PostModel(Activity activity, int id) {
        entityStatus = new EntityStatus();
        this.activity=activity;
        this.idPost = id;
    }

    public void getSinglePost(final VolleyCallbackStatus callback){
        VolleySingleton.getInstance(activity).get("posts/" + idPost, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONObject jsonOwner = jsonObject.getJSONObject("owner");
                    entityStatus.setContent(jsonObject.getString("message"));
                    entityStatus.setPrivacy(jsonObject.getInt("privacy"));
                    entityStatus.setNameId(jsonOwner.getString("full_name"));
                    entityStatus.setCreatedTime(jsonObject.getLong("created_at"));
                    entityStatus.setLike(jsonObject.getBoolean("is_liked"));
                    if (jsonOwner.has("avatar")){
                        entityStatus.setAvatar(jsonOwner.getString("avatar"));
                    }
                    if (jsonObject.has("photo"))
                    {
                        entityStatus.setImage(jsonObject.getString("photo"));
                    }
                    if (jsonObject.has("comments"))
                    {
                        entityStatus.setNumberComment(jsonObject.getInt("comments"));
                    }
                    if (jsonObject.has("likes"))
                    {
                        entityStatus.setNumberLike(jsonObject.getInt("likes"));
                    }

//                    entityStatus.set
//                    setEntityStatus(entityStatus);
                    callback.onSuccess(entityStatus);
                    Log.d("editPost",entityStatus.getContent());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },activity);
    }
    public void setEntityStatus(EntityStatus entityStatus){
        this.entityStatus=entityStatus;
    }
    public EntityStatus getEntityStatus(){
        return entityStatus;
    }

    public void editPost(String message,int privacy,final VolleyCallBackCheck callback) {
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("message",message);
        HashMap parram = new HashMap();
        parram.put("privacy",privacy);
        parram.put("status",1);
        parrams.putAll(parram);
        Log.d("hashmap", new JSONObject(parrams).toString());
        VolleySingleton.getInstance(activity).put(activity,"posts/" + idPost , new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }
    public  ArrayList<EntityComment> getComment(int number,final VolleyCallbackComment callback){
        final ArrayList<EntityComment> entityComments = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("posts/" + idPost + "/comments?sort=-created_at&limit=15&skip="+ number, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i< jsonArray.length();i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                JSONObject jsonOwner = jsonObject.getJSONObject("owner");
                                entityComment = new EntityComment();
                                entityComment.setuId(Integer.parseInt(jsonOwner.getString("id")));
                                entityComment.setFull_name(jsonOwner.getString("full_name"));
                                entityComment.setIdComment(Integer.parseInt(jsonObject.getString("id")));
                                entityComment.setMessage(jsonObject.getString("message"));
                                entityComment.setUsername(jsonOwner.getString("username"));
                                entityComment.setCreated_at(Long.parseLong(jsonObject.getString("created_at")));
                                entityComment.setCanEdit(jsonObject.getBoolean("can_edit"));
                                if (jsonOwner.has("avatar")){
                                    entityComment.setAvatar(jsonOwner.getString("avatar"));
                                }
                                entityComment.setCanDelete(jsonObject.getBoolean("can_delete"));
                                entityComment.setTotal(response.getInt("total"));
                                entityComments.add(entityComment);
                            }
                            callback.onSuccess(entityComments);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },activity);
        return entityComments;
    }

    public void addPost(String message,String type,int privacy,Uri downloadUri, final VolleyCallBackCheck callback) {
        HashMap<String,String> parram = new HashMap<>();
        parram.put("message",message);
        if (downloadUri !=null){
            parram.put("photo",downloadUri.toString());
        }
        HashMap parramNumber = new HashMap();
        parramNumber.put("status",1);
        parramNumber.put("privacy", privacy);
        parram.putAll(parramNumber);
        VolleySingleton.getInstance(activity).post(activity,type+"/" + uId + "/posts", new JSONObject(parram), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }

    public void CommentPost(final String content){
        final EntityComment entityComment = new EntityComment();
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("message",content);
        VolleySingleton.getInstance(activity).post(activity,"posts/" + idPost + "/comments", new JSONObject(parrams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        }
                });
    }

//    public

    public ArrayList<EntityStatus> getListPost(int total,final VolleyCallbackListStatus callback){
        final ArrayList<EntityStatus> statuses = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("users/" + uId + "/posts?type=" + type +"&limit=10"+ "&skip="+ total, null, new Response.Listener<JSONObject>() {
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
                        statuses.add(entityStatus);
                    }

                    Log.d("result",response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    callback.onSuccess(statuses);
            }
        },activity);
        return statuses;
    }
    public void DeletePost(int idPost1,final VolleyCallBackCheck callback){
        VolleySingleton.getInstance(activity).delete(activity,"posts/" + idPost1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }

    public void LikePost(final VolleyCallBackJson callback){
        VolleySingleton.getInstance(activity).post(activity,"posts/" + idPost + "/likes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response);
                    Log.d("Like","Like: + " + idPost);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void UnLikePost(final VolleyCallBackJson callback){
        VolleySingleton.getInstance(activity).delete(activity,"posts/" + idPost + "/likes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response);
                    Log.d("Unlike","Unlike: + " + idPost);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void addComment(final VolleyCallBackJson callback,String message,ArrayList<JSONObject> listMentions){
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("message",message);
        if (!listMentions.isEmpty()){
            JSONArray jsonArrayMention = new JSONArray();
            for (int i =0;i<listMentions.size();i++){
                jsonArrayMention.put(listMentions.get(i));
            }
            Map map = new HashMap();
            map.put("mentions",jsonArrayMention);
            parrams.putAll(map);
        }
        Log.d("JsonComment",new JSONObject(parrams).toString());
        VolleySingleton.getInstance(activity).post(activity,"posts/" + idPost + "/comments", new JSONObject(parrams),
                new Response.Listener<JSONObject>() {
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


    public void DeleteComment(final VolleyCallBackCheck callback, final int idComment){
        VolleySingleton.getInstance(activity).delete(activity,"comments/" + idComment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("delete",idComment+"");
                callback.onSuccess(true);
            }
        });
    }

    public void getPostGroup(final PostModel.VolleyCallbackListStatus callback, String type){
        final ArrayList<EntityStatus> items = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("groups/"+ uId + "/posts"+"?limit=10"+type+ "", null, new Response.Listener<JSONObject>() {
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

    public void getListLike(final VolleyCallbackListLike callback){
        final ArrayList<EntityListLike> itemsLikeList = new ArrayList<>();
        VolleySingleton.getInstance(activity).get("posts/" + idPost + "/likes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("data");
                    if (jsonArray.equals("null"))
                    {
                        System.exit(0);
                    }else {
                        for (int i = 0; i< jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            EntityListLike entityListLike = new EntityListLike();
                            entityListLike.setFull_name(jsonObject.getString("full_name"));
                            entityListLike.setUsername(jsonObject.getString("username"));
                           if (jsonObject.has("is_followed"))entityListLike.setFollow(jsonObject.getBoolean("is_followed"));
                            entityListLike.setId(jsonObject.getInt("id"));
                            itemsLikeList.add(entityListLike);
                            Log.d("full_name",entityListLike.getFull_name());
                        }
                        callback.onSuccess(itemsLikeList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },activity);
    }



    public interface VolleyCallbackStatus{
        void onSuccess(EntityStatus entityStatus);
    }
    public interface VolleyCallbackListStatus{
        void onSuccess(ArrayList<EntityStatus> entityStatuses);
    }

    public interface VolleyCallbackListLike{
        void onSuccess(ArrayList<EntityListLike> entityListLikes);
    }

    public interface VolleyCallbackComment {
        void onSuccess(ArrayList<EntityComment> entityComments);
    }
    public interface VolleyCallBackCheck {
        void onSuccess(boolean status);
    }
    public interface VolleyCallbackPutComment {
        void onSuccess(EntityComment entityComment);
    }
    public interface VolleyCallBackJson {
        void onSuccess(JSONObject jsonObject) throws JSONException;
    }
}