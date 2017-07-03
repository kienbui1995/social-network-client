package Entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 6/30/17.
 */

public class EntityChannel {
    private int id;
    private String name;
    private String short_name;
    private String description;
    private int totalFollower;
    private String avatar;
    private String cover;
    private long created_at;
    private long updated_at;
    private boolean is_admin;
    private boolean is_followed;
    public EntityChannel() {
    }

    public EntityChannel(int id, String name, String short_name, String description, int totalFollower, String avatar, String cover, long created_at, long updated_at, boolean is_admin, boolean is_followed) {
        this.id = id;
        this.name = name;
        this.short_name = short_name;
        this.description = description;
        this.totalFollower = totalFollower;
        this.avatar = avatar;
        this.cover = cover;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_admin = is_admin;
        this.is_followed = is_followed;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public boolean is_followed() {
        return is_followed;
    }

    public void setIs_followed(boolean is_followed) {
        this.is_followed = is_followed;
    }

    public boolean is_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalFollower() {
        return totalFollower;
    }

    public void setTotalFollower(int totalFollower) {
        this.totalFollower = totalFollower;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public Map<String,Object> toMap(EntityChannel entityChannel){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",entityChannel.getName());
        result.put("cover",entityChannel.getCover());
        result.put("short_name",entityChannel.getShort_name());
        result.put("avatar",entityChannel.getAvatar());
        result.put("description",entityChannel.getDescription());
        result.values().removeAll(Collections.singleton(null));
        return result;
    }
}
