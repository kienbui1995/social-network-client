package Entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 5/25/17.
 */

public class EntityGroup {

    private int id;
    private String name;
    private int type;
    private int members;
    private int posts;
    private String description;
    private String avatar;
    private String cover;
    private int privacy;
    private long created_at;
    private long updated_at;
    private int status;
    private boolean can_request;
    private boolean can_join;
    private boolean is_pending;
    private boolean is_admin;
    private boolean is_member;

    public EntityGroup() {
    }

    public EntityGroup(int id, String name, int type, int members, int posts, String description, String avatar, String cover, int privacy, long created_at, long updated_at, int status, boolean can_request, boolean can_join, boolean is_pending, boolean is_admin, boolean is_member) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.members = members;
        this.posts = posts;
        this.description = description;
        this.avatar = avatar;
        this.cover = cover;
        this.privacy = privacy;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.can_request = can_request;
        this.can_join = can_join;
        this.is_pending = is_pending;
        this.is_admin = is_admin;
        this.is_member = is_member;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCan_request() {
        return can_request;
    }

    public void setCan_request(boolean can_request) {
        this.can_request = can_request;
    }

    public boolean isCan_join() {
        return can_join;
    }

    public void setCan_join(boolean can_join) {
        this.can_join = can_join;
    }

    public boolean is_pending() {
        return is_pending;
    }

    public void setIs_pending(boolean is_pending) {
        this.is_pending = is_pending;
    }

    public boolean is_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean is_member() {
        return is_member;
    }

    public void setIs_member(boolean is_member) {
        this.is_member = is_member;
    }

    public Map<String,Object> toMap(EntityGroup entityGroup){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name",entityGroup.getName());
        result.put("cover",entityGroup.getCover());
        result.put("avatar",entityGroup.getAvatar());
        result.put("description",entityGroup.getDescription());
        result.values().removeAll(Collections.singleton(null));
        return result;
    }
}
