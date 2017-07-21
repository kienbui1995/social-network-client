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

import Entity.EntityClass;
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
                        entityViolation.setTime_at(jsonObject.getLong("created_at"));
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

    public  void GetListViolation(String mtt, final VolleyCallBackStudentViolation callback){
        final ArrayList<EntityViolation> items = new ArrayList<>();
        VolleySingleton.getInstance(context).get("supervisiors/" + mtt + "/violations", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject jsonOwner = jsonObject.getJSONObject("owner");
                        EntityViolation entityViolation = new EntityViolation();
                        EntityViolation.owner owner = entityViolation.new owner();
                        owner.setCode(jsonOwner.getString("code"));
                        owner.setName(jsonOwner.getString("name"));
                        entityViolation.setOwner(owner);
                        entityViolation.setId(jsonObject.getInt("id"));
                        if (jsonObject.has("message")) entityViolation.setMessage(jsonObject.getString("message"));
                        if (jsonObject.has("photo")) entityViolation.setPhoto(jsonObject.getString("photo"));
                        entityViolation.setTime_at(jsonObject.getLong("created_at"));
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

    public void searchRoom(String id, int shift, final VolleyCallBackListRoom callback){
        final ArrayList<EntityClass> itemsClass = new ArrayList<>();
        VolleySingleton.getInstance(context).get("rooms/" + id + "/classes?day=" + shift, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject jsonTeacher = jsonObject.getJSONObject("teacher");
                        JSONObject jsonRoom = jsonObject.getJSONObject("room");
                        JSONObject jsonSubject = jsonObject.getJSONObject("subject");
                        JSONObject jsonTerm = jsonObject.getJSONObject("semester");
                        EntityClass entityClass = new EntityClass();
                        EntityClass.teacher teacher = entityClass.new teacher();
                        teacher.setId(jsonTeacher.getInt("id"));
                        teacher.setCode(jsonTeacher.getString("code"));
                        teacher.setName(jsonTeacher.getString("name"));
                        EntityClass.room room = entityClass.new room();
                        room.setId(jsonRoom.getInt("id"));
                        room.setCode(jsonRoom.getString("code"));
                        EntityClass.subject subject = entityClass.new subject();
                        subject.setId(jsonSubject.getInt("id"));
                        subject.setCode(jsonSubject.getString("code"));
                        subject.setName(jsonSubject.getString("name"));
                        entityClass.setId(jsonObject.getInt("id"));
                        EntityClass.term term = entityClass.new term();
                        term.setCode(jsonTerm.getString("code"));
                        term.setName(jsonTerm.getString("name"));
                        entityClass.setTerm(term);
                        entityClass.setCode(jsonObject.getInt("code"));
                        entityClass.setName(jsonObject.getString("name"));
                        entityClass.setDay(jsonObject.getInt("day"));
                        entityClass.setStart(jsonObject.getInt("start_at"));
                        entityClass.setEnd(jsonObject.getInt("finish_at"));
                        entityClass.setCreated_at(jsonObject.getLong("created_at"));
                        entityClass.setTeacher(teacher);
                        entityClass.setRoom(room);
                        entityClass.setSubject(subject);
                        itemsClass.add(entityClass);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(itemsClass);

            }
        },context);
    }

    public void getListStudent(int idClass, final VolleyCallBackListStudent callback){
        final ArrayList<EntityStudent> items = new ArrayList<>();
        VolleySingleton.getInstance(context).get("classes/" + idClass + "/students?limit=100", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("data");
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

    public interface VolleyCallBackListStudent{
        void onSuccess(ArrayList<EntityStudent> list);
    }
    public interface VolleyCallBackListRoom{
        void onSuccess(ArrayList<EntityClass> list);
    }
    public interface VolleyCallBackStudentViolation{
        void onSuccess(ArrayList<EntityViolation> list);
    }
}
