package com.joker.thanglong.Model;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.Ultil.VolleyHelper;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Entity.EntityComment;
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("editPost",VolleySingleton.getInstance(activity).checkErrorCode(error)+"");
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
        VolleySingleton.getInstance(activity).put("posts/" + idPost , new JSONObject(parrams), new Response.Listener<JSONObject>() {
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
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        return entityComments;
    }

    public void addPost(String message,int privacy,String type,Uri downloadUri, final VolleyCallBackCheck callback) {
        HashMap<String,String> parram = new HashMap<>();
        parram.put("message",message);
        if (downloadUri !=null){
            parram.put("photo",downloadUri.toString());
        }
        HashMap parramNumber = new HashMap();
        parramNumber.put("status",1);
        parramNumber.put("privacy", privacy);
        parram.putAll(parramNumber);
        VolleySingleton.getInstance(activity).post("users/" + uId + "/"+type, new JSONObject(parram), new Response.Listener<JSONObject>() {
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

    public void CommentPost(final String content){
        final EntityComment entityComment = new EntityComment();
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("message",content);
        VolleySingleton.getInstance(activity).post("posts/" + idPost + "/comments", new JSONObject(parrams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("postCommentSucees",VolleySingleton.getInstance(activity).checkErrorCode(error)+"");

                    }
                });
    }
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return statuses;
    }
    public void DeletePost(int idPost1,final VolleyCallBackCheck callback){
        VolleySingleton.getInstance(activity).delete("posts/" + idPost1, null, new Response.Listener<JSONObject>() {
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

    public void LikePost(final VolleyCallBackJson callback){
        VolleySingleton.getInstance(activity).post("posts/" + idPost + "/likes", null, new Response.Listener<JSONObject>() {
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
                Log.d("Like",VolleySingleton.getInstance(activity).checkErrorCode(error)+"");
            }
        });

    }

    public void UnLikePost(final VolleyCallBackJson callback){
        VolleySingleton.getInstance(activity).delete("posts/" + idPost + "/likes", null, new Response.Listener<JSONObject>() {
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
                Log.d("UnLike",VolleySingleton.getInstance(activity).checkErrorCode(error)+"");
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
        VolleySingleton.getInstance(activity).post("posts/" + idPost + "/comments", new JSONObject(parrams),
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
                        Log.d("addComment", VolleyHelper.checkErrorCode(error)+"");

                    }
                });

    }


    public void DeleteComment(final VolleyCallBackCheck callback, final int idComment){
        VolleySingleton.getInstance(activity).delete("comments/" + idComment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("delete",idComment+"");
                callback.onSuccess(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onSuccess(true);
            }
        });
    }



    public JSONObject itemListToJsonConvert(ArrayList<HashMap<String, String>> list) {

        JSONObject jResult = new JSONObject();// main object
        JSONArray jArray = new JSONArray();// /ItemDetail jsonArray

        for (int i = 0; i < list.size(); i++) {
            JSONObject jGroup = new JSONObject();// /sub Object

            try {
                jGroup.put("ItemMasterID", list.get(i).get("ItemMasterID"));
                jGroup.put("ID", list.get(i).get("id"));
                jGroup.put("Name", list.get(i).get("name"));
                jGroup.put("Category", list.get(i).get("category"));

                jArray.put(jGroup);

                // /itemDetail Name is JsonArray Name
                jResult.put("itemDetail", jArray);
                return jResult;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jResult;
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