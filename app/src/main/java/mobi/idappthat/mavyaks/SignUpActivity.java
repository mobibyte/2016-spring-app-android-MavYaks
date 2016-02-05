package mobi.idappthat.mavyaks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Cameron on 2/4/16.
 */
public class SignUpActivity extends AppCompatActivity {

    EditText etName, etEmail, etPass, etConfirmPass;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
        //Basic way to get input from a EditText
        String name = etName.getText().toString();
        Toast.makeText(this, "Hello, " + name, Toast.LENGTH_SHORT).show();
    }
}
