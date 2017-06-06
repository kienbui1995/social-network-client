package Entity;

/**
 * Created by joker on 4/27/17.
 */

public class EntityRoomChat {
    int idFrom;
    int idTo;
    String name;
    Long time;
    int timeRead;
    String lastMessage;

    public EntityRoomChat() {
    }

    public EntityRoomChat(int idFrom, int idTo, String name, Long time, int timeRead, String lastMessage) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.name = name;
        this.time = time;
        this.lastMessage=lastMessage;
        this.timeRead = timeRead;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
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

    public int getTimeRead() {
        return timeRead;
    }

    public void setTimeRead(int timeRead) {
        this.timeRead = timeRead;
    }
}
