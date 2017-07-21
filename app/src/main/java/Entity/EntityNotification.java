package Entity;

/**
 * Created by joker on 6/5/17.
 */

public class EntityNotification {
    private int id;
    private Actor actor;
    private int actionId;
    private int totalAction;
    private Post last_post;
    private Comment last_comment;
    private Mention last_mention;
    private Actor last_user;
    private long updatedAt;
    private long seen;
    private EntityUserProfile user;

    public EntityUserProfile getUser() {
        return user;
    }

    public void setUser(EntityUserProfile user) {
        this.user = user;
    }

    public class Actor {
        private int id;
        private String usename;
        private String fullName;
        private String avatar;

        public Actor() {
        }

        public Actor(int id, String usename, String fullName, String avatar) {
            this.id = id;
            this.usename = usename;
            this.fullName = fullName;
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsename() {
            return usename;
        }

        public void setUsename(String usename) {
            this.usename = usename;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public class Post {
        private int id;
        private String message;
        private String photo;

        public Post(int id, String message, String photo) {
            this.id = id;
            this.message = message;
            this.photo = photo;
        }

        public Post() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

    public class Comment {
        private int id;
        private String message;

        public Comment(int id, String message) {
            this.id = id;
            this.message = message;
        }

        public Comment() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


    public class Mention {
        private int id;
        private String message;

        public Mention(int id, String message) {
            this.id = id;
            this.message = message;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public EntityNotification(int id, Actor actor, int actionId, int totalAction
            , Post last_post, Comment last_comment, Mention last_mention, Actor last_user, long updatedAt, long seen, EntityUserProfile user) {
        this.id = id;
        this.actor = actor;
        this.actionId = actionId;
        this.totalAction = totalAction;
        this.last_post = last_post;
        this.last_comment = last_comment;
        this.last_mention = last_mention;
        this.last_user = last_user;
        this.updatedAt = updatedAt;
        this.seen = seen;
        this.user = user;
    }

    public EntityNotification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getTotalAction() {
        return totalAction;
    }

    public void setTotalAction(int totalAction) {
        this.totalAction = totalAction;
    }

    public Post getLast_post() {
        return last_post;
    }

    public void setLast_post(Post last_post) {
        this.last_post = last_post;
    }

    public Comment getLast_comment() {
        return last_comment;
    }

    public void setLast_comment(Comment last_comment) {
        this.last_comment = last_comment;
    }

    public Mention getLast_mention() {
        return last_mention;
    }

    public void setLast_mention(Mention last_mention) {
        this.last_mention = last_mention;
    }

    public Actor getLast_user() {
        return last_user;
    }

    public void setLast_user(Actor last_user) {
        this.last_user = last_user;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getSeen() {
        return seen;
    }

    public void setSeen(long seen) {
        this.seen = seen;
    }
}
