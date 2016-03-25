package mobi.idappthat.mavyaks.util;

import com.firebase.client.AuthData;

/**
 * Created by Cameron on 3/4/16.
 */
public interface AuthCallback {
    void onSuccess(AuthData authData);
    void onError(String message);
}
