package Entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 7/3/17.
 */

public class EntityNotificationChannel {
    private EntityChannel channel;
    private String tittle;
    private String message;
    private String time;
    private String place;
    private long created_at;

    public EntityNotificationChannel() {
    }

    public EntityNotificationChannel(EntityChannel channel, String tittle, String message, String time, String place, long created_at) {
        this.channel = channel;
        this.tittle = tittle;
        this.message = message;
        this.time = time;
        this.place = place;
        this.created_at = created_at;
    }

    public EntityChannel getChannel() {
        return channel;
    }

    public void setChannel(EntityChannel channel) {
        this.channel = channel;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
    public Map<String,Object> toMap(EntityNotificationChannel entityNotificationChannel){
        HashMap<String,Object> result = new HashMap<>();
        result.put("title",entityNotificationChannel.getTittle());
        result.put("message",entityNotificationChannel.getMessage());
        result.put("time",entityNotificationChannel.getTime());
        result.put("place",entityNotificationChannel.getPlace());
//        result.put("description",entityChannel.getDescription());
        result.values().removeAll(Collections.singleton(null));
        return result;
    }
}
