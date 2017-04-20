package Entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joker on 4/18/17.
 */

public class EntityUserProfile extends RealmObject {
    String uID;
    String userName;
    @PrimaryKey
    String fbID;
    String last_name;
    String first_name;
    String tokenFB;
    String token;
    String email;
    String avatar;
    String gender;

    public EntityUserProfile(String uID, String userName, String fbID, String last_name, String first_name, String tokenFB, String token, String email, String gender, String avatar) {
        this.uID = uID;
        this.userName = userName;
        this.fbID = fbID;
        this.last_name = last_name;
        this.first_name = first_name;
        this.tokenFB = tokenFB;
        this.token = token;
        this.email = email;
        this.gender = gender;
        this.avatar = avatar;
    }

    public EntityUserProfile() {
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFbID() {
        return fbID;
    }

    public void setFbID(String fbID) {
        this.fbID = fbID;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getTokenFB() {
        return tokenFB;
    }

    public void setTokenFB(String tokenFB) {
        this.tokenFB = tokenFB;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}