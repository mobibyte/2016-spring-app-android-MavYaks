package mobi.idappthat.mavyaks.models;

/**
 * Created by Cameron on 2/11/16.
 */
public class User {

    /*
    * This just holds basic data about the user
    * which we can use to easily display yak and
    * profile data
    * */

    String name;
    String email;
    String pictureUrl;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, String pictureUrl) {
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
