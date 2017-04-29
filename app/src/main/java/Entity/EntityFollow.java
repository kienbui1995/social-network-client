package Entity;

/**
 * Created by joker on 4/25/17.
 */

public class EntityFollow {
    private String id;
    private String username;
    private String avatar;
    private String full_name;
    private boolean is_followed;

    public EntityFollow() {
    }

    public EntityFollow(String id, String username, String avatar, String full_name, boolean is_followed) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.full_name = full_name;
        this.is_followed = is_followed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean is_followed() {
        return is_followed;
    }

    public void setIs_followed(boolean is_followed) {
        this.is_followed = is_followed;
    }
}
