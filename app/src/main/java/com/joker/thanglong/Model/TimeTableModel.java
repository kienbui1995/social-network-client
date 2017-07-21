package com.joker.thanglong.Model;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityClass;
import Entity.EntityExamSchedule;
import Entity.EntityTerm;

/**
 * Created by joker on 6/20/17.
 */

public class TimeTableModel {
    private Activity context;
    private ArrayList<EntityClass> itemsClass;
    private ArrayList<EntityTerm> itemsTerm;
    public TimeTableModel(Activity context) {
        this.context = context;
    }

    public void getDataTimeTable(int term,String code,final VolleyCallbackGetDataTimeTable callback){
        itemsClass = new ArrayList<>();
        VolleySingleton.getInstance(context).get("classes?student_code="+code+"&semester_code=" + term, null,
                new Response.Listener<JSONObject>() {
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
                        Log.d("sizeTime", itemsClass.size()+"'");
                    }
                },context);
    }

    public void getTeacherTimeTable(int term,String code,final VolleyCallbackGetDataTimeTable callback){
        itemsClass = new ArrayList<>();
        VolleySingleton.getInstance(context).get("teachers/"+code+"/classes?semester_code="+term, null,
                new Response.Listener<JSONObject>() {
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
                        Log.d("sizeTime", itemsClass.size()+"'");
                    }
                },context);
    }

    public void getTeacherTerm(String code, final VolleyCallbackGetTerm callback){
        itemsTerm = new ArrayList<>();
        VolleySingleton.getInstance(context).get("teachers/"+ code+ "/semesters?sort=-code", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityTerm entityTerm = new EntityTerm();
                        entityTerm.setId(jsonObject.getInt("id"));
                        entityTerm.setCode(jsonObject.getInt("code"));
                        entityTerm.setName(jsonObject.getString("name"));
                        entityTerm.setStart(jsonObject.getString("start_at"));
                        entityTerm.setEnd(jsonObject.getString("finish_at"));
                        entityTerm.setSymbol(jsonObject.getString("symbol"));
                        entityTerm.setYear(jsonObject.getString("year"));
                        itemsTerm.add(entityTerm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(itemsTerm);
            }
        },context);
    }


    public void getTerm(String code, final VolleyCallbackGetTerm callback){
        itemsTerm = new ArrayList<>();
        VolleySingleton.getInstance(context).get("students/"+ code+ "/semesters?sort=-code", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityTerm entityTerm = new EntityTerm();
                        entityTerm.setId(jsonObject.getInt("id"));
                        entityTerm.setCode(jsonObject.getInt("code"));
                        entityTerm.setName(jsonObject.getString("name"));
                        entityTerm.setStart(jsonObject.getString("start_at"));
                        entityTerm.setEnd(jsonObject.getString("finish_at"));
                        entityTerm.setSymbol(jsonObject.getString("symbol"));
                        entityTerm.setYear(jsonObject.getString("year"));
                        itemsTerm.add(entityTerm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(itemsTerm);
            }
        },context);
    }

    public void getLichThi(int term,String code, final VolleyCallbackGetLichThi callback){

        final ArrayList<EntityExamSchedule> items = new ArrayList<>();
        VolleySingleton.getInstance(context).get("exam_schedules?student_code="+ code +"&semester_code=" + term, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject jsonRoom = jsonObject.getJSONObject("room");
                        JSONObject jsonSubject = jsonObject.getJSONObject("subject");
                        EntityExamSchedule entityExamSchedule = new EntityExamSchedule();
                        EntityExamSchedule.room room = entityExamSchedule.new room();
                        EntityExamSchedule.Subject subject = entityExamSchedule.new Subject();
                        room.setId(jsonRoom.getInt("id"));
                        room.setCode(jsonRoom.getString("code"));
                        entityExamSchedule.setRoom(room);
                        subject.setId(jsonSubject.getInt("id"));
                        subject.setCode(jsonSubject.getString("code"));
                        subject.setName(jsonSubject.getString("name"));
                        entityExamSchedule.setSubject(subject);
                        entityExamSchedule.setId(jsonObject.getInt("id"));
                        entityExamSchedule.setDay(jsonObject.getString("day"));
                        entityExamSchedule.setTime(jsonObject.getString("exam_time"));
                        items.add(entityExamSchedule);
                    }
                    callback.onSuccess(items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },context);
    }

    public interface VolleyCallbackGetDataTimeTable {
        void onSuccess(ArrayList<EntityClass> itemsClass);
    }

    public interface VolleyCallbackGetLichThi {
        void onSuccess(ArrayList<EntityExamSchedule> items);
    }

    public interface VolleyCallbackGetTerm {
        void onSuccess(ArrayList<EntityTerm> itemsTerm);
    }
}
