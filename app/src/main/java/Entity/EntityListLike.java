package Entity;

/**
 * Created by joker on 5/5/17.
 */

public class EntityListLike {
    private int id;
    private String full_name;
    private String username;
    private String avatar;
    private boolean isFollow;

    public EntityListLike() {
    }

    public EntityListLike(int id, String username, String avatar, boolean isFollow,String full_name) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.isFollow = isFollow;
        this.full_name = full_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
