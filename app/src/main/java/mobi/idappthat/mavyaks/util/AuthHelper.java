package mobi.idappthat.mavyaks.util;

import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by Cameron on 3/4/16.
 */
public class AuthHelper {

    public static final String TAG = "LoginHelper";

    Firebase ref = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com");

    public void registerNewUser(String email, String password) {
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.d("User created with id " + result.get("uuid"));
            }

            @Override
            public void onError(FirebaseError firebaseError) {

            }
        });
    }
}
