package mobi.idappthat.mavyaks.models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Cameron on 2/11/16.
 */
public class Yak {

    /*
     * A Yak holds a user and a string,
     * just like a real tweet on Twitter
     * we also create a unique ID for the Yak for the server (later)
     */

    User user;
    String tweet;
    Date createdAt;
    String id;

    public Yak(User user, String tweet, Date createdAt) {
        this.user = user;
        this.tweet = tweet;
        this.createdAt = createdAt;
        id = UUID.randomUUID().toString();
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


    //Tells us weather a Yak is valid or not
    //In this example we simply check the length.
    public static boolean isValid(String text)
    {
        return text.length() > 0 && text.length() < 144;
    }
}
