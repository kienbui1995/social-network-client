package com.joker.thanglong.Model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityGroup;

/**
 * Created by joker on 5/25/17.
 */

public class GroupModel {
    private Context context;
    private EntityGroup entityGroup;
    public GroupModel(Context context) {
        this.context = context;
    }

    public void getListGroup(final VolleyCallbackListGroup callback){
        final ArrayList<EntityGroup> lisGroups = new ArrayList<>();
        VolleySingleton.getInstance(context).get("groups", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        EntityGroup entityGroup = new EntityGroup();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        entityGroup.setId(jsonObject.getInt("id"));
                        entityGroup.setName(jsonObject.getString("name"));
                        if (jsonObject.has("members")) entityGroup.setMembers(jsonObject.getInt("members"));
                        if (jsonObject.has("avatar")) entityGroup.setAvatar(jsonObject.getString("avatar"));
                        if (jsonObject.has("type")) entityGroup.setType(jsonObject.getInt("type"));
                        if (jsonObject.has("posts")) entityGroup.setPosts(jsonObject.getInt("posts"));
                        if (jsonObject.has("cover")) entityGroup.setCover(jsonObject.getString("cover"));
                        if (jsonObject.has("privacy")) entityGroup.setPrivacy(jsonObject.getInt("privacy"));
                        if (jsonObject.has("created_at")) entityGroup.setCreated_at(jsonObject.getLong("created_at"));
                        if (jsonObject.has("updated_at")) entityGroup.setUpdated_at(jsonObject.getLong("updated_at"));
                        if (jsonObject.has("status")) entityGroup.setStatus(jsonObject.getInt("status"));
                        if (jsonObject.has("can_request")) entityGroup.setCan_request(jsonObject.getBoolean("can_request"));
                        if (jsonObject.has("is_pending")) entityGroup.setIs_pending(jsonObject.getBoolean("is_pending"));
                        if (jsonObject.has("is_admin")) entityGroup.setIs_admin(jsonObject.getBoolean("is_admin"));
                        if (jsonObject.has("is_member")) entityGroup.setIs_member(jsonObject.getBoolean("is_member"));
                        if (jsonObject.has("can_join")) entityGroup.setCan_join(jsonObject.getBoolean("can_join"));
                        if (jsonObject.has("description")) entityGroup.setDescription(jsonObject.getString("description"));
                        lisGroups.add(entityGroup);
                    }
                    callback.onSuccess(lisGroups);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(lisGroups);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void getInfoGroup(int idGroup,final VolleyCallbackInfoGroup callback){
        VolleySingleton.getInstance(context).get("groups/" + idGroup, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                EntityGroup entityGroup = new EntityGroup();
                JSONObject jsonObject = null;
                try {
                    jsonObject = response.getJSONObject("data");
                    entityGroup.setId(jsonObject.getInt("id"));
                    entityGroup.setName(jsonObject.getString("name"));
                    if (jsonObject.has("members")) entityGroup.setMembers(jsonObject.getInt("members"));
                    if (jsonObject.has("avatar")) entityGroup.setAvatar(jsonObject.getString("avatar"));
                    if (jsonObject.has("type")) entityGroup.setType(jsonObject.getInt("type"));
                    if (jsonObject.has("posts")) entityGroup.setPosts(jsonObject.getInt("posts"));
                    if (jsonObject.has("cover")) entityGroup.setCover(jsonObject.getString("cover"));
                    if (jsonObject.has("privacy")) entityGroup.setPrivacy(jsonObject.getInt("privacy"));
                    if (jsonObject.has("created_at")) entityGroup.setCreated_at(jsonObject.getLong("created_at"));
                    if (jsonObject.has("updated_at")) entityGroup.setUpdated_at(jsonObject.getLong("updated_at"));
                    if (jsonObject.has("status")) entityGroup.setStatus(jsonObject.getInt("status"));
                    if (jsonObject.has("can_request")) entityGroup.setCan_request(jsonObject.getBoolean("can_request"));
                    if (jsonObject.has("is_pending")) entityGroup.setIs_pending(jsonObject.getBoolean("is_pending"));
                    if (jsonObject.has("is_admin")) entityGroup.setIs_admin(jsonObject.getBoolean("is_admin"));
                    if (jsonObject.has("is_member")) entityGroup.setIs_member(jsonObject.getBoolean("is_member"));
                    if (jsonObject.has("can_join")) entityGroup.setCan_join(jsonObject.getBoolean("can_join"));
                    if (jsonObject.has("description")) entityGroup.setDescription(jsonObject.getString("description"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(entityGroup);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void getListGroupJoin(final VolleyCallbackListGroup callback){
        final ArrayList<EntityGroup> lisGroups = new ArrayList<>();
        VolleySingleton.getInstance(context).get("users/"+ ProfileInstance.getProfileInstance(context).getProfile().getuID()+"/groups",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        EntityGroup entityGroup = new EntityGroup();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        entityGroup.setId(jsonObject.getInt("id"));
                        entityGroup.setName(jsonObject.getString("name"));
                        if (jsonObject.has("members")) entityGroup.setMembers(jsonObject.getInt("members"));
                        if (jsonObject.has("avatar")) entityGroup.setAvatar(jsonObject.getString("avatar"));
                        if (jsonObject.has("type")) entityGroup.setType(jsonObject.getInt("type"));
                        if (jsonObject.has("posts")) entityGroup.setPosts(jsonObject.getInt("posts"));
                        if (jsonObject.has("cover")) entityGroup.setCover(jsonObject.getString("cover"));
                        if (jsonObject.has("privacy")) entityGroup.setPrivacy(jsonObject.getInt("privacy"));
                        if (jsonObject.has("created_at")) entityGroup.setCreated_at(jsonObject.getLong("created_at"));
                        if (jsonObject.has("updated_at")) entityGroup.setUpdated_at(jsonObject.getLong("updated_at"));
                        if (jsonObject.has("status")) entityGroup.setStatus(jsonObject.getInt("status"));
                        if (jsonObject.has("can_request")) entityGroup.setCan_request(jsonObject.getBoolean("can_request"));
                        if (jsonObject.has("is_pending")) entityGroup.setIs_pending(jsonObject.getBoolean("is_pending"));
                        if (jsonObject.has("is_admin")) entityGroup.setIs_admin(jsonObject.getBoolean("is_admin"));
                        if (jsonObject.has("is_member")) entityGroup.setIs_member(jsonObject.getBoolean("is_member"));
                        if (jsonObject.has("can_join")) entityGroup.setCan_join(jsonObject.getBoolean("can_join"));
                        if (jsonObject.has("description")) entityGroup.setDescription(jsonObject.getString("description"));
                        lisGroups.add(entityGroup);
                    }
                    callback.onSuccess(lisGroups);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(lisGroups);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    public interface VolleyCallbackListGroup{
        void onSuccess(ArrayList<EntityGroup> listGroup);
    }

    public interface VolleyCallbackInfoGroup{
        void onSuccess(EntityGroup entityGroup);
    }
}
