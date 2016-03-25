# Part 6


### Attaching Users To "Yaks"

In this guide we want to implement the feature that allows other users
the ability to tell who created a post, to do this we need to save some user
information. Lets get started!


### Modifying Login/Sign up

In order to do what we want we need to save some of the user data locally so
that we can query for the data later. First we need to modify `LoginActivity`
and `SignUpActivity`. Open up the two activities and edit both the callback
methods like below.

```
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
```
As you can see all we did was store the UUID in a local data store called
`SharedPreferences`, we did this so we can get the users data later in the app.

*Note: You must edit the login callback as well!!!*


### Attach The Data

Now we need to actually attach the user to a yak, so to do that what we will do
is get the UUID from the data store and then query firebase for the rest of the
user data with that UUID. Open up the `MainActivity` and put the following code
in the `onActivityResult` method

```
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
```

Notice how we modified the method a bit, it now get the UUID from storage
queries firebase and waits for the data to come back before posting the yak.
This is important because we want to be sure the user data is attached. The
whole method should look like this.

```
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
```

### Conclusion
We now have an understanding of how to store data locally and use it later in the
app so that we can query firebase for more data, we used this idea to attach
users to yaks when they create them.
