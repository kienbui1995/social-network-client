package Entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joker on 5/23/17.
 */

public class EntitySetting extends RealmObject{
    @PrimaryKey
    int id = 1;
    int newsfeed;

    public EntitySetting(int newsfeed) {
        this.newsfeed = newsfeed;
    }

    public int getNewsfeed() {
        return newsfeed;
    }

    public void setNewsfeed(int newsfeed) {
        this.newsfeed = newsfeed;
    }

    public EntitySetting() {
    }
}
