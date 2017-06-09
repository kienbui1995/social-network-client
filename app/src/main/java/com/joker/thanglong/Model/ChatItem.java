package com.joker.thanglong.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 4/8/17.
 */

public class ChatItem {

    int id;
    private Object timeStamp;
    String content;
    String photo;


    public int getId() {
        return id;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatItem(int id, String content,String photo) {
        this.id = id;
        this.timeStamp= ServerValue.TIMESTAMP;
        this.content = content;
        this.photo = photo;
    }

    public ChatItem() {
    }
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("id",id);
        result.put("content",content);
        result.put("photo",photo);
        result.put("timeStamp",timeStamp);
        return result;
    }

//    public Long getTimeStamp() {
//        if (timeStamp instanceof Long) {
//            return (Long) timeStamp;
//        }
//        else {
//            return null;
//        }
//    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "uID='" + id + '\'' +
                ", timeStamp='" + photo + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
