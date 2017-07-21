package Entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 7/5/17.
 */

public class EntityViolation {
    private int id;
    private String message;
    private String place;
    private String photo;
    private Long time_at;
    private owner owner;

    public EntityViolation() {
    }

    public EntityViolation(int id, String message, String place, String photo, Long time_at, EntityViolation.owner owner) {
        this.id = id;
        this.message = message;
        this.place = place;
        this.photo = photo;
        this.time_at = time_at;
        this.owner = owner;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getTime_at() {
        return time_at;
    }

    public void setTime_at(Long time_at) {
        this.time_at = time_at;
    }

    public Map<String,Object> toMap(EntityViolation entityViolation){
        HashMap<String,Object> result = new HashMap<>();
        result.put("photo",entityViolation.getPhoto());
        result.put("message",entityViolation.getMessage());
        result.put("place",entityViolation.getPlace());
        result.put("time_at",entityViolation.getTime_at());
        result.values().removeAll(Collections.singleton(null));
        return result;
    }

    public EntityViolation.owner getOwner() {
        return owner;
    }

    public void setOwner(EntityViolation.owner owner) {
        this.owner = owner;
    }

    public class owner{
        private int id;
        private String code;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public owner(int id, String code, String name) {
            this.id = id;
            this.code = code;
            this.name = name;
        }

        public owner() {
        }
    }
}
