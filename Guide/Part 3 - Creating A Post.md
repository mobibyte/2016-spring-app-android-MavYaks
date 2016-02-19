#Part 3
####Creating a post

For this guide we will be going over how to create the logic and UI needed to create a post for 
MavYaks, but we will not go over sending data to the server yet!!

####Set up button
First we need a button on our feed activity (Main Activity), so that a user has something that is obvious to
press when they want to create a new post.

So to do this we need to create Floating Action Button, luckily Android Studio generated this for this but 
I will show you the code you need to create the fab.
Open the `res -> layout -> activity_main.xml` and post the code below to the bottom, but inside the root layout.

```
    <!-- This what the user interacts with to create a new post -->
    <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        android:src="@drawable/ic_add_black_24dp" />
```

Now you will notice that the `"@drawable/ic_add_black_24dp"` can't be found, but we can have android studio generate the 
icon for us! To do this go to `File -> New -> Vector Asset` then when the dialog pops up press the `Choose` button a new 
dialog will pop then click `Content` on the side pane and select the `+` icon then press okay and next.

![Fab][fab]
[fab]: http://i.imgur.com/nby7V2H.png?1


####Create "CreatePostActivity"
Now That we have a button in order to allow users to create a post we need an activity 
for that button to take us to! In order to create a new activity we will use one of android 
studios templates.

`New -> Activity -> Gallery -> Basic Activity` and name it CreatePostActivity

#####Create The Layout
This will generate the activity, layout and even add it to the manifest for us. Now
we open the content_create_post.xml, which is our layout file and create something like this.

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:context="mobi.idappthat.mavyaks.activities.CreatePostActivity"
    tools:showIn="@layout/activity_create_post">


    <!--
    Set our Max Length to 144 our max lines to 5
    We also set our imeOptions to "actionSend" to give the action button the send icon/send type
    Set the input type to use auto cap auto correct and multi line
    -->
    <EditText
        android:id="@+id/et_post"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:hint="@string/type_post"
        android:inputType="textCapSentences|textAutoCorrect|textMultiLine"
        android:maxLength="144"
        android:lines="5" />

</LinearLayout>
```

**Note: I also changed the icon of the fab in the activity_create_post.xml by generating a vector like before**

Now you will need to set up your Activity code so open up the CreatePostActivity.class 
and set up the toolbar/fab in the OnCreate method like so

```
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
```

Then you will want to set up the edit text in the onCreate method for the actual content of the Yak like so.

```
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
```

Lastly we need to create our `sendPost()` method

```
    /*
     * We set the results to be the text post
     * so that we can add it to the adapter in the main activity
     * and even send it to the server.
     */
    private void sendPost()
    {
        Intent data = new Intent();
        data.putExtra(TEXT, mText.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
```


The full onCreate method of the `CreatePostActivity.class` should look like this


```
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
```

The `isValid` function is implemented in the `Yak.class` like this.

```
  //Tells us weather a Yak is valid or not
    //In this example we simply check the length.
    public static boolean isValid(String text)
    {
        return text.length() > 0 && text.length() < 144;
    }
```

![Create][create]
[create]: http://i.imgur.com/0bilNbv.png?1

Now you will want to open the `MainActivity.class` and add this code, so that your fab
onClick method looks like this. This will allow you to start the post activity 
for a result when the fab is clicked

```
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Here we start the start the CreatePostActivity and wait for its result
                Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
                startActivityForResult(i, CREATE_POST_REQUEST);
            }
        });
```

Now you will want to set up the code to wait for and recieve the result, when the user
is done with creating the post or cancels the post creation. To do this you must
override the `onActivityResult` method like so.

```
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
                String post = data.getStringExtra(CreatePostActivity.TEXT);
                User user = new User("Me");
                Yak yak = new Yak(user, post, new Date());
                adapter.addYak(yak, 0);

            }
        }
    }
```

As you can see all this method does is wait for a result from the CreatePostActivity and
then creates a new Yak from that data and adds it to our adapter.

**Note that I had to crtate a new method in the adapter so that we could add the yak to the top.**

That should be it! You can now craete more posts!
