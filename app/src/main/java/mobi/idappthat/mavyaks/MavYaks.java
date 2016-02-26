package mobi.idappthat.mavyaks;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Cameron on 2/26/16.
 */
public class MavYaks extends Application {

    /*
    * The application class is just one giant
    * Singleton that holds your entire app.
    *
    * Its lifecycle is almost like an activity,
    * except its for the entire application
    * */

    @Override
    public void onCreate() {
        super.onCreate();

        //Setup firebase as soon as the app starts
        Firebase.setAndroidContext(this);
    }

}
