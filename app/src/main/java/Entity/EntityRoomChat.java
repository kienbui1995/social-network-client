package Entity;

/**
 * Created by joker on 4/27/17.
 */

public class EntityRoomChat {
    String idFrom;
    String idTo;
    String name;
    Long time;
    String lastMessage;

    public EntityRoomChat() {
    }

    public EntityRoomChat(String idFrom, String idTo, String name, Long time,String lastMessage) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.name = name;
        this.time = time;
        this.lastMessage=lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(String idFrom) {
        this.idFrom = idFrom;
    }

    public String getIdTo() {
        return idTo;
    }

    public void setIdTo(String idTo) {
        this.idTo = idTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
