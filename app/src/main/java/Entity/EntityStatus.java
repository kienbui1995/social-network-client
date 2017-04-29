package Entity;

/**
 * Created by joker on 3/16/17.
 */

public class EntityStatus {
    private int idStatus;
    private int uId;
    private String nameId;
    private String content;
    private long createdTime;
    private String image;

    public EntityStatus() {
    }

    public EntityStatus(int idStatus, int uId, String nameId, String content, long createdTime, String image) {
        this.idStatus = idStatus;
        this.uId = uId;
        this.nameId = nameId;
        this.content = content;
        this.createdTime = createdTime;
        this.image = image;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
