package Entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joker on 3/16/17.
 */

public class EntityStatus extends RealmObject{
    @PrimaryKey
    private int idStatus;
    private int uId;
    private String nameId;
    private String content;
    private long createdTime;
    private String image;
    private boolean isLike;
    private int status;
    private int numberLike;
    private int numberComment;
    private int privacy;
    private boolean canEdit;
    private boolean canDelete;
    private String avatar;
    private Place place;
    public EntityStatus() {
    }

    public EntityStatus(int idStatus, int uId, String nameId, String content, long createdTime, String image, boolean isLike, int status,String avatar
            ,int numberLike, int numberComment, int privacy, boolean canEdit, boolean canDelete,Place place) {
        this.idStatus = idStatus;
        this.uId = uId;
        this.nameId = nameId;
        this.content = content;
        this.createdTime = createdTime;
        this.image = image;
        this.isLike = isLike;
        this.status = status;
        this.numberLike = numberLike;
        this.numberComment = numberComment;
        this.privacy = privacy;
        this.canEdit = canEdit;
        this.canDelete = canDelete;
        this.avatar = avatar;
        this.place =place;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public int getNumberComment() {
        return numberComment;
    }

    public void setNumberComment(int numberComment) {
        this.numberComment = numberComment;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityStatus)) return false;

        EntityStatus that = (EntityStatus) o;

        if (getIdStatus() != that.getIdStatus()) return false;
        if (getuId() != that.getuId()) return false;
        if (getCreatedTime() != that.getCreatedTime()) return false;
        if (isLike() != that.isLike()) return false;
        if (getStatus() != that.getStatus()) return false;
        if (getNumberLike() != that.getNumberLike()) return false;
        if (getNumberComment() != that.getNumberComment()) return false;
        if (getPrivacy() != that.getPrivacy()) return false;
        if (isCanEdit() != that.isCanEdit()) return false;
        if (isCanDelete() != that.isCanDelete()) return false;
        if (!getNameId().equals(that.getNameId())) return false;
        if (!getContent().equals(that.getContent())) return false;
        if (!getImage().equals(that.getImage())) return false;
        if (!getAvatar().equals(that.getAvatar())) return false;
        return getPlace().equals(that.getPlace());

    }

    @Override
    public int hashCode() {
        int result = getIdStatus();
        return result;
    }
}
