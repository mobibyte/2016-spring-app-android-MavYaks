# Part 5

#### Auth with Firebase - [Firebase Guide](https://www.firebase.com/docs/android/guide/user-auth.html)
One of the first things we want to do is enable the "Auth" ability in Firebase. Just go to your dashboard and click the right buttons.

![Activate](http://i.imgur.com/djATyld.png)

#### User Model updates
Not a whole lot chagned here, all we need to do is have an empty constructor and make a few setters! Oh yeah and store an id.

**Pro tip:** If you press `CMD/CTRL` + `N` you can generate getters and setters automatically!

```
String id;
...

public User() {
}

public void setName(String name) {
    this.name = name;
}

public void setEmail(String email) {
    this.email = email;
}

public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
}
```

#### The Auth Callback
Okay so now we're on to interfaces. This is somewhat of an advanced technique but you needed to know it some day so how about now. So all callback, interface, delegate, however you want to call it is a function that gets called once something else happens somewhere else. Okay, you’re like wtf right now but I swear, it saves lives.

Imagine this, you want to login to your app (Asynchronous) and you want something to happen AFTER the login request is complete. Well instead of telling your app to sleep for so many seconds (thats bad, never do that.. EVER) you can setup some kind of progress dialog then stop it when the request comes back.

Here’s an example of a callback. See? You’ve already used an interface and you didn’t even know it.

```
btnRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        register();
    }
});
```

But here’s the interface we want. We call this `AuthCallback`. Why not use the default firebase callbacks? You can, this is just cleaner.

```
public interface AuthCallback {
    void onSuccess();
    void onError(String message);
}
```

#### Auth Helper
We want some kind of class that can help manager our auth system. What if we want to add facebook or twitter later? Well having this manager or helper class makes our app more scalable. That means even more people can work on it at one time. We start of by making this static function called `registerNewUser`. We don’t return anything, instead we implement our callback. You’re probably thinking, why final? Final lets us access variables above the callback from firebase.

First we will use the default firebase ref to call `createUser`. This creates the user, but does not login. When the registration is successful, we want to store that info they just registered with, then call our new function, `login`

```
public static void registerNewUser(final String name, final String email, final String password, final AuthCallback callback) {
    ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
        @Override
        public void onSuccess(Map<String, Object> result) {
            //Save data to Firebase
            String uid = result.get("uid").toString();

            User user = new User();
            user.setId(uid);
            user.setEmail(email);
            user.setName(name);
            user.setPictureUrl("http://www.foodvisionusa.com/wp-content/uploads/2014/12/speaker-placeholder-male.png");

            //For the actual saving..
            ref.child("users/" + uid).setValue(user);

            Log.d(TAG, "User created with id " + uid);

            //Login using that other function we made!
            login(email, password, callback);
        }

        @Override
        public void onError(FirebaseError firebaseError) {
            callback.onError(firebaseError.getMessage());
        }
    });
}
```

If we called this from the `createUser` function, its okay because the callback was passed by reference. That means we can use the same callback and give the same two responses. This function is a lot more basic because we literally just call `authWithPassword`. That’s it!

```
public static void login(final String email, final String password, final AuthCallback callback) {
    ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
        @Override
        public void onAuthenticated(AuthData authData) {
            Log.d(TAG, "Logged in " + authData.toString());
            callback.onSuccess();
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            callback.onError(firebaseError.getMessage());
        }
    });
}
```

#### SignUpActivity
Okay, so we made the ability to login or register a user, but now for implementing it. In our case, we have  another helper function called `formIsValid`. It basically iterates though each EditText and make’s sure there is actually text in it. It also makes sure both the passwords match.

But now we want to use that `LoginHelperClass`. Remember, since its a static function, we don’t need to instantiate it. Since it will take some time to log the user in, we went ahead and set the register button to disabled. This keeps the user from hitting it again, and KINDA shows there is progress being made.

If we succeed, we want to continue to the main activity, if not, show the error!

```
private void register() {
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
            public void onSuccess() {
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
```

#### LoginActivity
The login activity is about as equal as easy. All we did was create another helper `formIsValid()` and using our login function. If it was successful, we went ahead and went to the next page.

```
AuthHelper.login(email, password, new AuthCallback() {
    @Override
    public void onSuccess() {
        finishLogin();
    }

    @Override
    public void onError(String message) {
        Snackbar.make(baseView, message, Snackbar.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }
});
```

#### Conclusion
Firebase makes auth really easy. You can also checkout their tutorial about facebook and twitter login!

A few things you could add is making a progress dialog pop up while waiting for the request to be sent back, also maybe a form validator function.

Next week we'll be logging these users out, updating users and their tweets and skipping the login if the user is already logged in.