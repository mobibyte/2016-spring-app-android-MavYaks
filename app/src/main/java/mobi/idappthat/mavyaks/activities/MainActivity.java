package mobi.idappthat.mavyaks.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Pattern;

import mobi.idappthat.mavyaks.R;
import mobi.idappthat.mavyaks.adapters.YakAdapter;
import mobi.idappthat.mavyaks.models.DataYak;
import mobi.idappthat.mavyaks.models.User;

public class MainActivity extends AppCompatActivity {

    //Just a constant to represent our post activity
    private static final int CREATE_POST_REQUEST = 1;



    /*
    * Adapter and Recycler view are member variables
    * so you can access them anywhere
    * */
    YakAdapter adapter;
    RecyclerView list;
    Context mContext;

    /*
    * This is the object we use to reference the tweets
    * or yaks in our Firebase database. This is real time
    * data so we can keep retrieving them and it will
    * live update*/
    Firebase yaksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Here we start the start the CreatePostActivity and wait for its result
                Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
                startActivityForResult(i, CREATE_POST_REQUEST);
            }
        });

        /*
        * Time to setup the RecyclerView!
        *
        * A LinearLayoutManager makes a straight list
        * A GridLayoutManaged makes something similar to Pintrest
        *
        * You then have to link the list to the adapter you created
        * */
        LinearLayoutManager lm = new LinearLayoutManager(this);
        adapter = new YakAdapter();

        list = (RecyclerView) findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(lm);
        list.setAdapter(adapter);

        /*
        * Setup the reference to Firebase. Put in your
        * App url from firebase then call the child of your
        * array, ours is called "yaks"
        *
        * We don't really need to worry about anything except
        * onChildAdded. You can check for deletion on your own!
        * */
        yaksRef = new Firebase("https://mav-yaks.firebaseio.com");
        yaksRef.child("yaks").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataYak yak = dataSnapshot.getValue(DataYak.class);
                adapter.addYak(yak, 0);
                list.scrollToPosition(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DataYak yak = dataSnapshot.getValue(DataYak.class);
                adapter.removeYak(yak);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /*
    * This is just a cool snippet you might find useful in your
    * android dev spiritual journey
    *
    * It just gets the default user account, remember to add
    * the permission!
    *
    * http://stackoverflow.com/questions/2112965/how-to-get-the-android-devices-primary-e-mail-address
    * */
    private String getUserAccount() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(mContext).getAccounts();

        for(Account account : accounts) {
            if(emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return "Secret User";
    }


    /*
    * This function just creates some fake tweets for us
    * You can customize it however you like
    *
    * Calender gets the current time on the device
    *
    * Remember to add each tweet to the adapter!
    * */

    /*
    * EDIT: Part 4, this is no longer needed since
    * We have firebase and DataYak
    * */
    /*private void addFakeTweets() {
        Calendar c = Calendar.getInstance();

        User u1 = new User("Fred Fred");
        User u2 = new User("Mike Gomez");

        Yak yak1 = new Yak(u1, "Yo whas good?", new Date(c.getTimeInMillis()));
        Yak yak2 = new Yak(u2, "Mobi is so cool! I'm learning some android!", new Date(1423717200));
        Yak yak3 = new Yak(u1, "Second tweet ever!", new Date(1423617200));
        Yak yak4 = new Yak(u1, "First tweet ever!", new Date(1423617200));

        adapter.addYak(yak1);
        adapter.addYak(yak2);
        adapter.addYak(yak3);
        adapter.addYak(yak4);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Here we override the method so that we can be notified
     * when the Activity calls back with a Yak.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_POST_REQUEST)
        {
            if(resultCode == RESULT_OK)
            {
                /*
                * First of all, we need the current time in millis.
                * We next need to get the data they just set as
                * the "result". We made a public static final string
                * just incase it ever changes
                *
                * We then send off the data to firebase!!
                *
                * Remember to use "push" if its an array
                *
                *
                * EDIT: part 4
                * We no longer need to add to the adapter since
                * "OnChildAdded" does that for us! thanks firebase
                * */

                final Calendar c = Calendar.getInstance();
                final String post = data.getStringExtra(CreatePostActivity.TEXT);
//                String user = getUserAccount();

                //Get the prefs we made earlier
                SharedPreferences prefs = getSharedPreferences("MavYak", MODE_PRIVATE);
                //Get our UUID from the store
                String uuid = prefs.getString("uuid", "");

                //Now that we have the save UUID we can get our Users data from Firebase.
                yaksRef.child("users/" + uuid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Fill in our model
                        User user = dataSnapshot.getValue(User.class);
                        String name = user.getName();

                        //Create our yak with the user data
                        DataYak yak = new DataYak(name, post, c.getTimeInMillis());
                        yaksRef.child("yaks").push().setValue(yak);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });
            }
        }
    }
}
