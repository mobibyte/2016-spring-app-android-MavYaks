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

    String id;
    String name;
    String email;
    String pictureUrl;

    public User() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
