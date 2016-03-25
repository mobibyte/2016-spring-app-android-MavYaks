package mobi.idappthat.mavyaks.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;

import mobi.idappthat.mavyaks.R;
import mobi.idappthat.mavyaks.util.AuthCallback;
import mobi.idappthat.mavyaks.util.AuthHelper;

/**
 * Created by Cameron on 2/4/16.
 */
public class SignUpActivity extends AppCompatActivity {

    ViewGroup baseView;

    EditText etName, etEmail, etPass, etConfirmPass;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /*
        * Get the base view
        * This is another one of those cool tricks
        * to just kind of save
        *
        * This base view will be used to show the
        * "Snackbar" at the bottom of the screen
        * */
        baseView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        //Setup widgets
        btnRegister = (Button) findViewById(R.id.btn_register);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPass = (EditText) findViewById(R.id.et_password);
        etConfirmPass = (EditText) findViewById(R.id.et_password_confirm);

        /*
        * Setup single click listener for button
        * This is just like the "implements" method,
        * but for only one button at a time.
        *
        * You can choose either way, this one's cool too
        * */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    /*
    * Toast is that little pop up at the bottom,
    * kind of like the snack bar but a little older
    *
    * You pass in a "context", which the activity is a child of,
    * the tell it a length, but remember to say show!
    * */
    private void register() {

        /*
        * Now we want to make sure the form is valid
        * See the function below for more details
        * */
        if(formIsValid()) {
            //Get all the inputs
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();

            //Later you can make a loading bar instead!
            btnRegister.setEnabled(false);


            //User our login helper to login
            AuthHelper.registerNewUser(name, email, pass, new AuthCallback() {
                @Override
                public void onSuccess(AuthData authData) {
                    //Create our shared prefs instance
                    SharedPreferences.Editor prefs = getSharedPreferences("MavYak", MODE_PRIVATE).edit();

                    //Save our UUID for later and commit it synchronously
                    prefs.putString("uuid", authData.getUid()).commit();

                    finishLogin();
                }

                @Override
                public void onError(String message) {
                    Snackbar.make(baseView, message, Snackbar.LENGTH_SHORT).show();
                    btnRegister.setEnabled(true);
                }
            });
        }
    }

    //Just like before, clear the stack and goto MainActivity
    private void finishLogin() {
        Intent loginIntent = new Intent(this, MainActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    //Helper function for the form
    private boolean formIsValid() {
        //We add all the EditTexts so we can loop though them
        EditText forms[] = { etName, etEmail, etPass, etConfirmPass };
        boolean valid = true;

        /*
        * We could always just return when we find
        * a form not filled out, but in our case
        * we want each field to say if it has
        * the proper data needed
        * */
        for(EditText et : forms) {
            if(et.getText().toString().isEmpty()) {
                et.setError("Form Required");

                valid = false;
            }
        }

        //Premature return for required forms
        if(!valid) {
            Snackbar.make(baseView, "All forms required", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        //Make sure passwords are equal
        if(!etPass.getText().toString().equals(etConfirmPass.getText().toString())) {
            etPass.setError("Passwords must match");
            etConfirmPass.setError("Passwords must match");

            Snackbar.make(baseView, "Passwords must match", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
