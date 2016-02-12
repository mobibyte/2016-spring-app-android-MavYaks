package mobi.idappthat.mavyaks.models;

import java.util.Date;

/**
 * Created by Cameron on 2/11/16.
 */
public class Yak {

    /*
    * A Yak holds a user and a string,
    * just like a real tweet on Twitter
    * */

    User user;
    String tweet;
    Date createdAt;

    public Yak(User user, String tweet, Date createdAt) {
        this.user = user;
        this.tweet = tweet;
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getTweet() {
        return tweet;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
