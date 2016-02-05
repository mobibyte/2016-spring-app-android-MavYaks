# Part 1
#### Fake login/register pages

##### Setting up the project
We first started off by creating a new project. We started with the "Blank Activity" template since it comes with the Design and AppCompact support library.

##### Creating some activity
We then created two more activities, "LoginActivity" and "SignUpActivity". Then we made two XML files in the layout folder "activity_login" and "activity_signup".

##### Layouts and XML
Looking at "activity_login.xml" you can see that we used the new TextInputLayout from the Support Design library to use the material floating edit texts. This is new to android and won't work unless you have the Support Design library installed. We then added a few buttons and fake logo and header text.

As you can see all of ours text is in the "strings.xml" file. We do this because it will be supported by multiple languages and it's just an all around good practice.

Looking at a the layout you can see its basically all in a "LinearLayout". We also used "gravity center" to align everything in the middle.

For each EditText you can see an InputType and "singleLine true" is added. The input type is not required but forces the keyboard to be more towards what the field is used for. The singleLine attributes makes the focus move to the next EditText when the user presses enter.

On to the Register page, you can see we nested a LinearLayout in a RelativeLayout. You can do this with any layout to match your designs. Relative layout can be dangerous! Make sure your layout looks correct on all size devices.

##### The Activities
To start off, we created two Activity classes "LoginActivity" and "SignUpActivity" which extends the "AppCompactActivity" class. We do this to support older devices, it also handles well with the old actionbar. The new versions of andriod allows you create your own ActionBar or Toolbar, so extending this activity will automatically generate us one.

###### OnCreate
Activities have many different [lifecycles](http://i.stack.imgur.com/2CP6n.png). OnCreate is where we want to set our layout and "inflate" all of our widgets.

We first need to call "setContentView" this will turn that R.layout.activity_login or whatever you called it, into programatic widgets. The android inflator parses though this XML files and allows you to find any object in the layout using "findViewById". This is how you will get your buttons and text boxes.

When called "findViewById" it will only return a "View" object so remember to cast it to whatever class you want (i.e. Button or EditText).

##### Manifest.xml
One of the biggest mistakes people make in android development is not mapping their activity in the Manifest. As you can see we make "LoginActivity" our entry point and moved the custom theme to "MainActivity". Any activity you ever make will need to be called here.

##### Gradle and extra libraries
Just incase you get a gradle build error, make sure your build.gradle for your app his the Support Design and AppCompact libraries.

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:design:23.1.1'
}
```