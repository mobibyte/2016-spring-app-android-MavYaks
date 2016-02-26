package mobi.idappthat.mavyaks.models;

/**
 * Created by Cameron on 2/26/16.
 */
public class DataYak {

    /*
    * This is the new class we created for Firebase!
    * It is basically the same data as the Yak, but
    * we do not yet have a "User" relation. That's
    * next time!
    *
    * Also createdAt is now a long
    * "Time in Milisec"
    * */

    String user;
    String text;
    long createdAt;

    //We need an empty constructor for Firebase
    public DataYak() {

    }

    public DataYak(String user, String text, long createdAt) {
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "DataYak{" +
                "user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
