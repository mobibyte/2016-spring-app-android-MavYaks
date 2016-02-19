package mobi.idappthat.mavyaks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Date;

import mobi.idappthat.mavyaks.R;
import mobi.idappthat.mavyaks.adapters.YakAdapter;
import mobi.idappthat.mavyaks.models.User;
import mobi.idappthat.mavyaks.models.Yak;

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
            public void onClick(View view)
            {
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

        //Add some fake tweets!
        addFakeTweets();
    }


    /*
    * This function just creates some fake tweets for us
    * You can customize it however you like
    *
    * Calender gets the current time on the device
    *
    * Remember to add each tweet to the adapter!
    * */
    private void addFakeTweets() {
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
    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_POST_REQUEST)
        {
            if(resultCode == RESULT_OK)
            {
                //We can get our data from the intent like so.
                String post = data.getStringExtra(CreatePostActivity.TEXT);
                //Create a new user for our post ie ME!!!
                User user = new User("Me");
                //Create the new Yak with our data the date.
                Yak yak = new Yak(user, post, new Date());
                //Add the yak to the top!
                adapter.addYak(yak, 0);

            }
        }
    }
}
