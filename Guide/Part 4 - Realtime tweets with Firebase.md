# Part 4

#### Firebase vs Parse vs AWS
First of all, what the heck are these? Well its always been particularly difficult to get an app to communicate with a database. This entails setting up a database, making sure you know what tables you’ll need and their columns, as well as setting up the server to even host it. That already sounds like a lot of work but it doesn’t stop there. Next you’ll need some time of middle ware server to communicate with the database. This middleware sets up “Api” calls which will communicate data with HTTP requests.

Lets say i want to get a list of all tweets.
I would need to make a GET request to a server which looks something like this

http://api.myserver.com/v1/tweets?access_token=39d02qh

Now you would have to make your server read that and securely interact with the database.

You would think, why can’t my app just go straight into a database? We’ll it could, but that’s an awful idea. If anything ever happened, the user could get the connection info of your entire database. If its on a server, they can’t. Well they can, but if a user somehow got access to your server codebase, you’re a different kind of screwed.


##### AWS - [Getting started](https://aws.amazon.com/documentation/gettingstarted/)
![AWS][aws]


AWS has a crazy ecosystem with modules from setting up that mentioned server and database to services that will duplicate your servers if your requests get too high.

It also has a few awesome tools like Dynamo DB which basically does the middleware part for you. You just feed it data. 

AWS is awesome but it’s made for huge scalable projects. It has quite a bit of overhead so we won’t be using that for MavYaks.

##### Parse - [Deploying a server](https://devcenter.heroku.com/articles/deploying-a-parse-server-to-heroku)
![Parse][parse]

Parse is great, it’s amazing! But it’s kind of dead. Parse recently made an announcement that they will be disbanding the project but left everything open source. You can literally go to AWS or Heroku and deploy your own version of parse, which is awesome! But it is a service that might be dead for good in the next couple of years, but hey that’s software and we’ll see how it goes.

Besides the whole dying part, Parse is really awesome. You can setup your tables through an interactive dashboard and create columns for each of them. But it is a server that allows your app to communicate with the database, without that middleware. Parse is the middleware. It generates endpoints for your as earlier discussed and actually has an SDK which makes it even easier.

##### Firebase - [Overview](https://www.firebase.com/)
![Firebase][firebase]

This is what we’ll be using for MavYaks. It’s very similar to Parse but is “Event Driven”. What this means is every piece of data can be real time updated. Basically if another person makes a tweet, your app will update automatically.

The only downfall to Firebase is that it is mainly for event driven data. Everything is stored as JSON so the data structure isn’t the greatest but it gets a small project going pretty well.

#### Setup Firebase
You can follow our guide, but we recommend looking at the  [Firebase Quick Start Guide](https://www.firebase.com/docs/android/quickstart.html).

First of all we need to add the library to our app's build.gradle. 
```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'

    /* Google Support Libraries */
    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:design:23.1.1'
    compile 'com.android.support:recyclerview-v7:23.1.1'
    compile 'com.android.support:cardview-v7:23.1.1'

    /* Firebase */
    compile 'com.firebase:firebase-client-android:2.5.2+'
}
```

Along with the dependencies, you will need to exclude the META files but putting the second snippet inside your “Android” section.
```
android {
    ...
    packagingOptions {
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE-FIREBASE.txt'
        exclude 'META-INF/NOTICE'
    }
}
```

Next we need to create an Application class and fix a few things in our Manifest.xml

Lets start with the Application file. This is Singleton that carries your entire application. The lifecycle is very similar to an activity except onCreate is for the entire application, before any activity is called.

We created a new class called “MavYaks” and extended the Application class.
We will then need to add this line to setup Firebase.

```
Firebase.setAndroidContext(this);
```

Finally, we setup or Manifest to allow for internet connections and point to that new Application class.

We also have another permission that will read the accounts. This is mostly temperary and just allows us to get the default email since we don't have the login setup yet. These go right about the `<application>` tag

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
```

Now lets point to that new Application file by adding a `name` attribute to the `<application>` tag.
```
<application
        android:name=".MavYaks"
        ...>
```

Now firebase is on your app, lets get some data!

#### Updates to the model and adapter
We don't want to kill the Yak model right now, so we created another one called DataYak. This will be used by Firebase to retreive and store data.

It looks very similar but "user" is now a String, and "createdAt" is now a long (time in milisec). We also have an empty constructor so that firebase can easily add its data.

On the adapter we then need to change everything that uses a "Yak" model to "DataYak" like so.

```
List<DataYak> yaks;
...
public void bind(DataYak yak, SimpleDateFormat dateFormat) {}
```

#### Getting data - [Firebase Guide](https://www.firebase.com/docs/android/guide/retrieving-data.html)
In our MainActivity we need to setup a reference to the firebase object. Ours looks something like this
```
Firebase yaksRef = new Firebase("https://mav-yaks.firebaseio.com");
```

We then need to specify that we want the "yaks" child and set a "ChildAdded" listener and add each new child to the list. ChildAdded is called for every item in the list so you do not have to iterate though any loops.

```
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

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
});
```

If you're not super familiar with java, you're probably like "Why the heck do we have to have all of these empty functions when we're not using them??" This is called an [interface](https://docs.oracle.com/javase/tutorial/java/concepts/interface.html). It's basically a change triggered by another part of the application.

If you look at the onChildAdded block, you'll see how Firebase automatically fills your model up with data. We then add the yak to the top of the adapter and tell the list to scroll up.

#### Posting data - [Firebase Guide](https://www.firebase.com/docs/android/guide/saving-data.html)
We'll need to make a few changes on our `onActivityResult` function.
Our code should now look like this

```
Calendar c = Calendar.getInstance();
String post = data.getStringExtra(CreatePostActivity.TEXT);
String user = getUserAccount();

DataYak yak = new DataYak(user, post, c.getTimeInMillis());
yaksRef.child("yaks").push().setValue(yak);
```

First of all, we want the current time in milisec to give to the object. Then, just like before, we want to get the data the user sent back. Finally we want to push that new object to our firebase array.

You can checkout a little more about Firebase arrays [here](https://www.firebase.com/blog/2014-04-28-best-practices-arrays-in-firebase.html)

[aws]: https://images-ihjoz-com.s3.amazonaws.com/events/cover/1369/amazon-aws-logo.jpg
[parse]: https://cdn.evbuc.com/eventlogos/522659/parselogo-2.png
[firebase]: http://www.livechat.codeteam.in/images/firebase-logo.png
