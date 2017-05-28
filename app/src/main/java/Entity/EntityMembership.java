package Entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 5/27/17.
 */

public class EntityMembership {
    private int uId;
    private String username;
    private String full_name;
    private String avatar;
    private int idGr;
    private long created_at;
    private long updated_at;
    private int role;
    private int status;
    private boolean can_edit;
    private boolean can_delete;


    public EntityMembership() {
    }

    public EntityMembership(int uId, String username, String full_name, String avatar, int idGr, long created_at, long updated_at, int role, int status, boolean can_edit, boolean can_delete) {
        this.uId = uId;
        this.username = username;
        this.full_name = full_name;
        this.avatar = avatar;
        this.idGr = idGr;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.role = role;
        this.status = status;
        this.can_edit = can_edit;
        this.can_delete = can_delete;
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

    public int getIdGr() {
        return idGr;
    }

    public void setIdGr(int idGr) {
        this.idGr = idGr;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCan_edit() {
        return can_edit;
    }

    public void setCan_edit(boolean can_edit) {
        this.can_edit = can_edit;
    }

    public boolean isCan_delete() {
        return can_delete;
    }

    public void setCan_delete(boolean can_delete) {
        this.can_delete = can_delete;
    }

    public Map<String,Object> toMap(EntityMembership entityMembership){
        HashMap<String,Object> result = new HashMap<>();
        result.put("role",entityMembership.getRole());
        result.put("status",entityMembership.getStatus());
        result.values().removeAll(Collections.singleton(null));
        return result;
    }


}
