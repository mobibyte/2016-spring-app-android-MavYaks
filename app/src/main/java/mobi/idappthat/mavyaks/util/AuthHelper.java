package mobi.idappthat.mavyaks.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import mobi.idappthat.mavyaks.models.User;

/**
 * Created by Cameron on 3/4/16.
 */
public class AuthHelper {

    public static final String TAG = "LoginHelper";

    static Firebase ref = new Firebase("https://mav-yaks.firebaseio.com");

    /*
    * This is our basic function for creating a new user
    * By default, firebase does not log a user in
    * nor does it save it's info to be easily
    * accessible...
    *
    * As you can see, we had to do that ourselves.
    * Also by modifying the User model
    *
    * As you can see we have a little bit of intermediate Java Dev
    * techniques by using the callback and passing it. See the
    * docs for more info
    *
    * ProTip:
    * By the way, log.d is only when the app is in dev mode
    * Once you release the app, the log won't appear :P
    * */
    public static void registerNewUser(final String name, final String email, final String password, final AuthCallback callback) {
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {

                //Save data to Firebase
                String uid = result.get("uid").toString();


                //Make sure our user object is created
                User user = new User();

                //Set our values for the user
                user.setId(uid);
                user.setEmail(email);
                user.setName(name);
                user.setPictureUrl("http://www.foodvisionusa.com/wp-content/uploads/2014/12/speaker-placeholder-male.png");

                //For the actual saving..
                ref.child("users/" + uid).setValue(user);

                Log.d(TAG, "User created with id " + uid);

                //Login using that other function we made!
                login(email, password, callback);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onError(firebaseError.getMessage());
            }
        });
    }

    /*
    * Function for logging in!
    * This one is pretty simple, we only made it to make
    * the code look a little prettier and its nice to have a
    * Helper Class so you can bend things however you want!
    * */
    public static void login(final String email, final String password, final AuthCallback callback) {
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {

                Log.d(TAG, "Logged in " + authData.toString());
                callback.onSuccess(authData);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onError(firebaseError.getMessage());
            }
        });
    }
}
