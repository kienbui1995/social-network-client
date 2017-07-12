package Entity;

/**
 * Created by joker on 7/10/17.
 */

public class EntityExamSchedule {
    private int id;
    private String day;
    private String time;
    private int status;
    public Subject subject;
    public room room;

    public EntityExamSchedule(int id, String day, String time, int status, Subject subject, EntityExamSchedule.room room) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.status = status;
        this.subject = subject;
        this.room = room;
    }

    public EntityExamSchedule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public EntityExamSchedule.room getRoom() {
        return room;
    }

    public void setRoom(EntityExamSchedule.room room) {
        this.room = room;
    }

    public class Subject{
        private int id;
        private String name;
        private String code;

        public Subject() {
        }

        public Subject(int id, String name, String code) {
            this.id = id;
            this.name = name;
            this.code = code;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
    public class room{
        private int id;
        private String code;

        public room(int id, String code) {
            this.id = id;
            this.code = code;
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

        public room() {
        }
    }

}
