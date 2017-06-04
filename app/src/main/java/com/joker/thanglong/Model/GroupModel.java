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
import java.util.Map;

import Entity.EntityGroup;
import Entity.EntityMembership;

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
        },context);
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
        },context);
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
        },context);

    }
    public void editInfoGroup(int idGr,EntityGroup entityGroup){
        Map parrams = entityGroup.toMap(entityGroup);
        VolleySingleton.getInstance(context).put(context,"groups/" + idGr, new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        });
    }

    public void joinGroup(int idGr, final PostModel.VolleyCallBackCheck callback){
            VolleySingleton.getInstance(context).post(context,"groups/" + idGr + "/members", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    callback.onSuccess(true);
                }
            });
            }

    public void getListMemberRequest(int idGr, final VolleyCallbackListMemberGroup callback){
        final ArrayList<EntityMembership> listMember = new ArrayList<>();
        VolleySingleton.getInstance(context).get("groups/" + idGr + "/requests", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject jsonUser = jsonObject.getJSONObject("user");
                        EntityMembership membership = new EntityMembership();
                        if (jsonUser.has("avatar")) membership.setAvatar(jsonUser.getString("avatar"));
                        if (jsonUser.has("id")) membership.setuId(jsonUser.getInt("id"));
                        if (jsonUser.has("username")) membership.setUsername(jsonUser.getString("username"));
                        if (jsonUser.has("full_name")) membership.setFull_name(jsonUser.getString("full_name"));
                        if (jsonUser.has("role")) membership.setRole(jsonObject.getInt("role"));
                        if (jsonUser.has("id")) membership.setIdGr(jsonObject.getInt("id"));
                        listMember.add(membership);
                    }
                    callback.onSuccess(listMember);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },context);
    }

    public void requestAction(int id,int type,int role,final PostModel.VolleyCallBackCheck callback){
        EntityMembership entityMembership = new EntityMembership();
        entityMembership.setStatus(type);
        entityMembership.setRole(role);
        VolleySingleton.getInstance(context).put(context,"group_memberships/" + id, new JSONObject(entityMembership.toMap(entityMembership)), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }

    public void leaveGroup(int idGr, final PostModel.VolleyCallBackCheck callback){
        VolleySingleton.getInstance(context).delete(context, "groups/" + idGr + "/members", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    public void getListMemberGroup(int idGr,String type,VolleyCallbackListMemberGroup callback){
        final ArrayList<EntityMembership> listMember = new ArrayList<>();
        VolleySingleton.getInstance(context).get("groups/" + idGr + "/members?role=" + type, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject jsonUser = jsonObject.getJSONObject("user");
                        EntityMembership membership = new EntityMembership();
                        if (jsonUser.has("avatar")) membership.setAvatar(jsonUser.getString("avatar"));
                        membership.setuId(jsonUser.getInt("id"));
                        membership.setUsername(jsonUser.getString("username"));
                        if (jsonUser.has("full_name")) membership.setFull_name(jsonUser.getString("full_name"));
                        if (jsonObject.has("role")) membership.setRole(jsonObject.getInt("role"));
                        if (jsonObject.has("id")) membership.setIdGr(jsonObject.getInt("id"));
                        if (jsonObject.has("created_at")) membership.setCreated_at(jsonObject.getLong("created_at"));
                        if (jsonObject.has("can_edit")) membership.setCan_edit(jsonObject.getBoolean("can_edit"));
                        if (jsonObject.has("can_delete")) membership.setCan_edit(jsonObject.getBoolean("can_delete"));
                        if (jsonObject.has("updated_at")) membership.setUpdated_at(jsonObject.getLong("updated_at"));
                        listMember.add(membership);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },context);
        callback.onSuccess(listMember);
    }

    public void setRole(int idMembership, int role, final PostModel.VolleyCallBackCheck callback){
        EntityMembership entityMembership = new EntityMembership();
        entityMembership.setRole(role);

        VolleySingleton.getInstance(context).put(context,"group_memberships/" + idMembership,
                new JSONObject(entityMembership.toMap(entityMembership)), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(true);
                    }
                });
    }

    public void kickUser(int idMembership,final PostModel.VolleyCallBackCheck callback){
        VolleySingleton.getInstance(context).delete(context, "group_memberships/" + idMembership,
                null, new Response.Listener<JSONObject>() {
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

    public interface VolleyCallbackListGroup{
        void onSuccess(ArrayList<EntityGroup> listGroup);
    }

    public interface VolleyCallbackInfoGroup{
        void onSuccess(EntityGroup entityGroup);
    }
    public interface VolleyCallbackListMemberGroup{
        void onSuccess(ArrayList<EntityMembership> listMember);
    }
}
