package mobi.idappthat.mavyaks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import mobi.idappthat.mavyaks.R;
import mobi.idappthat.mavyaks.models.Yak;

public class CreatePostActivity extends AppCompatActivity
{
    public static final String TEXT = "text";

    /*
     * Our fields to be used in the class
     */
    EditText mText;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         * Find our fab by id and make on on click listener to listen for
         * Button clicks
         */
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        });


        /*
         * Get our edit text by id
         * and a text changed listener so we can track when
         * text has changed
         */
        mText = (EditText) findViewById(R.id.et_post);

        //This is how we watch for when text has changed
        mText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Check if it is valid if it is show the a send button
                //if not hide the send button
                if (Yak.isValid(s.toString())) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /*
     * We set the results to be the text post
     * so that we can add it to the adapter in the main activity
     * and even send it to the server.
     */
    private void sendPost() {
        Intent data = new Intent();
        data.putExtra(TEXT, mText.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }

}
