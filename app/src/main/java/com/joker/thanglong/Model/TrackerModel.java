package com.joker.thanglong.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import Entity.EntityStudent;
import Entity.EntityViolation;

/**
 * Created by joker on 7/5/17.
 */

public class TrackerModel {
    Context context;

    public TrackerModel(Context context) {
        this.context = context;
    }

    public void findStudent(String query, final VolleyCallBackListStudent callback){
        URI uri = null;
        try {
            uri = new URI(query.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final ArrayList<EntityStudent> items = new ArrayList<>();
        VolleySingleton.getInstance(context).get("students?name=" + uri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityStudent entityStudent = new EntityStudent();
                        entityStudent.setId(jsonObject.getInt("id"));
                        entityStudent.setCode(jsonObject.getString("code"));
                        entityStudent.setBirth_day(jsonObject.getString("birthday"));
                        entityStudent.setFirst_name(jsonObject.getString("first_name"));
                        entityStudent.setLast_name(jsonObject.getString("last_name"));
                        items.add(entityStudent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(items);
            }
        },context);
    }

    public  void GetDetailStudent(String msv, final VolleyCallBackStudentViolation callback){
        final ArrayList<EntityViolation> items = new ArrayList<>();
        VolleySingleton.getInstance(context).get("students/" + msv + "/violations", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityViolation entityViolation = new EntityViolation();
                        entityViolation.setId(jsonObject.getInt("id"));
                        if (jsonObject.has("message")) entityViolation.setMessage(jsonObject.getString("message"));
                        if (jsonObject.has("photo")) entityViolation.setPhoto(jsonObject.getString("photo"));
                        entityViolation.setTime_at(jsonObject.getLong("time_at"));
                        if (jsonObject.has("place")) entityViolation.setPlace(jsonObject.getString("place"));
                        items.add(entityViolation);
                        Log.d("itemss",items.size()+"");
                    }
                    callback.onSuccess(items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },context);
    }

    public void createViolation(String id, EntityViolation entityViolation, final PostModel.VolleyCallBackCheck callback){
        Map parrams = entityViolation.toMap(entityViolation);
        VolleySingleton.getInstance(context).post(context, "students/" + id + "/violations", new JSONObject(parrams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(true);
            }
        });
    }


    public interface VolleyCallBackListStudent{
        void onSuccess(ArrayList<EntityStudent> list);
    }
    public interface VolleyCallBackStudentViolation{
        void onSuccess(ArrayList<EntityViolation> list);
    }
}
