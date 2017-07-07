package Entity;

/**
 * Created by joker on 7/5/17.
 */

public class EntityStudent {
    private int id;
    private String code;
    private String first_name;
    private String last_name;
    private String birth_day;
    private String photo;

    public EntityStudent(int id, String code, String first_name, String last_name, String birth_day, String photo) {
        this.id = id;
        this.code = code;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_day = birth_day;
        this.photo = photo;
    }

    public EntityStudent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
