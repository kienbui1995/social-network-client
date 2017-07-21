package Entity;

/**
 * Created by joker on 6/20/17.
 */

public class EntityClass {
    private int id;
    private int code;
    private String name;
    private int day;
    private int start;
    private int end;
    private int status;
    private long created_at;
    private teacher teacher;
    private room room;
    private subject subject;
    private term term;
    public EntityClass() {
    }

    public EntityClass(int id, int code, String name, int day, int start, int end, int status, long created_at, EntityClass.teacher teacher, EntityClass.room room, EntityClass.subject subject, EntityClass.term term) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.day = day;
        this.start = start;
        this.end = end;
        this.status = status;
        this.created_at = created_at;
        this.teacher = teacher;
        this.room = room;
        this.subject = subject;
        this.term = term;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public EntityClass.teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityClass.teacher teacher) {
        this.teacher = teacher;
    }

    public EntityClass.room getRoom() {
        return room;
    }

    public void setRoom(EntityClass.room room) {
        this.room = room;
    }

    public EntityClass.subject getSubject() {
        return subject;
    }

    public void setSubject(EntityClass.subject subject) {
        this.subject = subject;
    }

    public EntityClass.term getTerm() {
        return term;
    }

    public void setTerm(EntityClass.term term) {
        this.term = term;
    }

    public class teacher{
        private int id;
        private String code;
        private String name;

        public teacher(int id, String code, String name) {
            this.id = id;
            this.code = code;
            this.name = name;
        }

        public teacher() {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public class room{
        private int id;
        private String code;

        public room() {
        }

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
    }
    public class subject{
        private int id;
        private String code;
        private String name;

        public subject() {
        }

        public subject(int id, String code, String name) {
            this.id = id;
            this.code = code;
            this.name = name;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class term{
        private int id;
        private String code;
        private String name;

        public term() {
        }

        public term(int id, String code, String name) {
            this.id = id;
            this.code = code;
            this.name = name;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
