package Entity;

/**
 * Created by joker on 5/2/17.
 */

public class EntityComment {
    private int idComment;
    private int uId;
    private String username;
    private String full_name;
    private String avatar;
    private String message;
    private long created_at;

    public EntityComment(int idComment, int uId, String username, String full_name, String avatar, String message, long created_at) {
        this.idComment = idComment;
        this.uId = uId;
        this.username = username;
        this.full_name = full_name;
        this.avatar = avatar;
        this.message = message;
        this.created_at = created_at;
    }

    public EntityComment() {
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
