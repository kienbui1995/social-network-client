package Entity;

/**
 * Created by joker on 4/17/17.
 */

public class EntityDangKy{
    private String username;
    private String password;
    private String email;

    public EntityDangKy(String username, String password, String email, String avatar) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
    }

    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
