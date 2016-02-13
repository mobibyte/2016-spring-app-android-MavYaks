package mobi.idappthat.mavyaks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mobi.idappthat.mavyaks.R;

/**
 * Created by Cameron on 2/4/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    * Making your widgets a member variable allows
    * you to do things like checking the edit text value
    * and access though without passing them though
    * a parameter
    * */
    EditText etEmail, etPass;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * Setting the content view is what "inflates"
        * the xml from the layout folder
        * to programatic android elements
        * */
        setContentView(R.layout.activity_login);


        /*
        * You can find any element from the layout by
        * calling "findViewById"
        *
        * You need to cast it because it will return
        * only a "View" object
        * */
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPass = (EditText) findViewById(R.id.et_password);

        /*
        * Since we have OnClickListener implemented,
        * we just need to give reference to the
        * buttons
        * */

        ///ahange
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*
        * The best way to decipher which button was press
        * is by getting it's id and matching it to
        * id's you already know exist
        * */
        switch(v.getId()) {
            case R.id.btn_login:
                login();
                break;

            case R.id.btn_register:
                navigateToRegister();
                break;
        }
    }

    /*
    * Intent's are used to navigate in android
    * you need to pass in a "Context" then let the
    * intent know which activity you want to go to
    *
    * Remember to call "startActivity"
    * */
    private void navigateToRegister() {
        Intent registerIntent = new Intent(this, SignUpActivity.class);
        startActivity(registerIntent);
    }

    /*
    * This one is actually a really cool snippet you might
    * want to hang on to
    *
    * Adding the flag "NEW_TASK" and "CLEAR_TASK" will
    * stop users from pressing back and coming back to
    * the login page
    * */
    private void login() {
        Intent loginIntent = new Intent(this, MainActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
}
