package com.joker.thanglong.Ultil;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Entity.EntityComment;
import Entity.EntityStatus;

/**
 * Created by joker on 5/8/17.
 */

public class PostUlti {
    public static VolleyHelper volleyHelper;
    EntityStatus entityStatus;
    Activity activity;
    EntityComment entityComment;
    int idPost;
    int uId;
    String type;
    public PostUlti(Activity activity,int uId,String type) {
        this.volleyHelper = new VolleyHelper(activity,activity.getResources().getString(R.string.url));
        entityStatus = new EntityStatus();
        this.activity=activity;
        this.uId=uId;
        this.type =type;
    }
    public PostUlti(Activity activity, int id) {
        this.volleyHelper = new VolleyHelper(activity,activity.getResources().getString(R.string.url));
        entityStatus = new EntityStatus();
        this.activity=activity;
        this.idPost = id;
    }

    public void getSinglePost(final VolleyCallbackStatus callback){
        volleyHelper.get("posts/" + idPost, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    entityStatus.setContent(jsonObject.getString("message"));
                    entityStatus.setPrivacy(jsonObject.getInt("privacy"));
                    entityStatus.setNameId(jsonObject.getString("full_name"));
                    entityStatus.setCreatedTime(jsonObject.getLong("created_at"));
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("editPost",VolleyHelper.checkErrorCode(error)+"");
            }
        });
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
        volleyHelper.put("posts/" + idPost , new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    public  ArrayList<EntityComment> getComment(int number,final VolleyCallbackComment callback){
        final ArrayList<EntityComment> entityComments = new ArrayList<>();
        volleyHelper.get("posts/" + idPost + "/comments?sort=-created_at&limit=15&skip="+ number, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i< jsonArray.length();i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                entityComment = new EntityComment();
                                entityComment.setuId(Integer.parseInt(jsonObject.getString("userid")));
                                entityComment.setFull_name(jsonObject.getString("full_name"));
                                entityComment.setIdComment(Integer.parseInt(jsonObject.getString("id")));
                                entityComment.setMessage(jsonObject.getString("message"));
                                entityComment.setUsername(jsonObject.getString("username"));
                                entityComment.setCreated_at(Long.parseLong(jsonObject.getString("created_at")));
                                entityComment.setCanEdit(jsonObject.getBoolean("can_edit"));
                                entityComment.setCanDelete(jsonObject.getBoolean("can_delete"));
                                entityComment.setTotal(response.getInt("total"));
                                entityComments.add(entityComment);
                            }
                            callback.onSuccess(entityComments);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        return entityComments;
    }

    public void CommentPost(final String content){
        final EntityComment entityComment = new EntityComment();
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("message",content);
        HashMap map = new HashMap();
        map.put("status",1);
        parrams.putAll(map);
        volleyHelper.postHeader("posts/" + idPost + "/comments", new JSONObject(parrams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("postCommentSucees",VolleyHelper.checkErrorCode(error)+"");

                    }
                });
    }
    public ArrayList<EntityStatus> getListPost(int total,final VolleyCallbackListStatus callback){
        final ArrayList<EntityStatus> statuses = new ArrayList<>();
        volleyHelper.get("users/" + uId + "/posts?type=" + type +"&limit=10"+ "&skip="+ total, null, new Response.Listener<JSONObject>() {
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
                        Log.d("canEdit",jsonObject.getBoolean("can_edit")+"");
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return statuses;
    }
    public void DeletePost(final VolleyCallBackCheck callback){
        volleyHelper.delete("posts/" + idPost, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
                Log.d("delete: ",idPost + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void LikePost(final VolleyCallBackJson callback){
        volleyHelper.postHeader("posts/" + idPost + "/likes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response);
                    Log.d("Like","Like: + " + idPost);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Like",VolleyHelper.checkErrorCode(error)+"");
            }
        });

    }

    public void UnLikePost(final VolleyCallBackJson callback){
        volleyHelper.delete("posts/" + idPost + "/likes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response);
                    Log.d("Unlike","Unlike: + " + idPost);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("UnLike",VolleyHelper.checkErrorCode(error)+"");
            }
        });

    }
    public void addComment(final VolleyCallBackJson callback,String message){
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("message",message);
        HashMap map = new HashMap();
        map.put("status",1);
        parrams.putAll(map);
        volleyHelper.postHeader("posts/" + idPost + "/comments", new JSONObject(parrams),
                new Response.Listener<JSONObject>() {
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


                    }
                });

    }


    public void DeleteComment(final VolleyCallBackCheck callback){
        volleyHelper.delete("comments/" + idPost, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onSuccess(true);
            }
        });
    }

    public interface VolleyCallbackStatus{
        void onSuccess(EntityStatus entityStatus);
    }
    public interface VolleyCallbackListStatus{
        void onSuccess(ArrayList<EntityStatus> entityStatuses);
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