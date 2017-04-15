package com.joker.hoclazada.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 4/8/17.
 */

public class ChatItem {

    String userName;
    private Object timeStamp;
    String content;

    public String getUserName() {
        return userName;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatItem(String uID, String content) {
        this.userName = uID;
        this.timeStamp= ServerValue.TIMESTAMP;
        this.content = content;
    }

    public ChatItem() {
    }
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("userName",userName);
        result.put("timeStamp",timeStamp);
        result.put("content",content);
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

    @Override
    public String toString() {
        return "ChatItem{" +
                "uID='" + userName + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
