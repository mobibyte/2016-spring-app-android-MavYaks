package mobi.idappthat.mavyaks.util;

/**
 * Created by Cameron on 3/4/16.
 */
public interface AuthCallback {
    void onSuccess();
    void onError(String message);
}
