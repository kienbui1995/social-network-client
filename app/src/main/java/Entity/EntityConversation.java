package Entity;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 4/26/17.
 */

public class EntityConversation {
    String name;
    int idFrom;
    int idTo;
    int timeRead;
    Object time;
    String lastMessage;

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public EntityConversation(String name, int idFrom, int idTo, String lastMessage, int timeRead) {
        this.name = name;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.lastMessage = lastMessage;
        this.timeRead = timeRead;
        this.time = ServerValue.TIMESTAMP;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",name);
        result.put("idFrom",idFrom);
        result.put("idTo",idTo);
        result.put("time",time);
        result.put("timeRead",timeRead);
        result.put("lastMessage",lastMessage);
        return result;
    }

    public String getName() {
        return name;
    }

    public int getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(int idFrom) {
        this.idFrom = idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getTime() {
        return time;
    }


}
