#Part 2

###Recycler View & Fake Tweets

####Colors & Styles
That purple and pink color got kind of annoying so we went ahead and changed the colors around.

You can goto 
https://www.materialpalette.com/
or
https://www.google.com/design/spec/style/color.html#color-color-palette

and pick some colors you like. Then in the colors.xml file, replace the primaryColor and primaryDarkColor with the colors you chose.

####Introduction to Model View Controller
Model View Controller or MVC is one of the most common design patterns. I helps organize all components of an app to make it highly scalable and maintainable by multiple people.

The basic foundation starts with your model. Think of these as objects, like a Note in a note taking app. What does a note have?

- Text/the actual note (`String`)
- When the note was created (`Date`)
- Who created the note (`User`, yup its another model)
- Any images or attachments (`List<Image>`)

Next we have the controller. In Android we can think of this as the Activity or Fragment. These two components present both data and controls to the user. Think of it as, a user presses a button, what happens?

In a note taking app, the user types in the text box and presses save on the controller, then the controller creates a new `Note` model and stores it in the database.

You can look a little more up on MVC here. MVP is also pretty interesting, but that's for another day.

#####What is MVC?

- https://teamtreehouse.com/library/build-a-blog-reader-android-app/exploring-the-masterdetail-template/the-modelviewcontroller-mvc-design-pattern-2
- http://programmers.stackexchange.com/a/127632

![MVC][mvc]

[mvc]: http://www.interaria.com/dallaswebdesignblog/wp-content/uploads/2012/10/MVC-web-application-development.png

####MavYak models
In MavYak we created 2 models. The User and a Yak

#####User
- name (`String`)
- email (`String`)
- imageUrl(`String`, we can then pull the image later)

#####Yak
- tweet (`String`, this is the actual yak or tweet)
- createdAt (`Date`, when it was created)
- user (`User`, who created it)

####Drawables and VectorGraphics
In android there are two ways have images, Drawables, which belong in the MipMap folder, or VectorDrawables which belong in the Drawables folder. Kind of confusing, but here's why.

Drawables are actual images. Png's or JPG with an underscore and all lowercase. (i.e. ic_launcher.png or profile.png)
http://developer.android.com/training/material/drawables.html

VectorDrawables are SVG's or scalable images, turned into Android XML. These are great for basic icons.
http://developer.android.com/tools/help/vector-asset-studio.html

####More libraries
We will be using two different libraries in this new update, RecyclerView and CardView. You'll need to add this to your app's build.gradle

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'

    /* Google Support Libraries */
    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:design:23.1.1'
    compile 'com.android.support:recyclerview-v7:23.1.1'
    compile 'com.android.support:cardview-v7:23.1.1'
}
```

####Card View
Card layouts are way to present important list data in Material Design
https://www.google.com/design/spec/components/cards.html#cards-usage

You can use this just like a FrameLayout, it just has a different appearance. We created a view called "row_yak.xml" which will contain each tweet/yak, the user and some basic button actions

```
<android.support.v7.widget.CardView
  ...>

  <LinearLayout></LinearLayout>
</android.support.v7.widget.CardView>
```

Linear layouts can be nested to make the layout look just like a tweet.
Inside we have a couple TextView's an ImageView and 3 ImageButton's.

![Tweet][Card]
[Card]: http://i.imgur.com/xoL5u0E.png

####Setting up the Adapter & ViewHolder
Next for the adapter. This is a class extends RecyclerView.Adapter and has a ViewHolder class inside of it.

A ViewHolder is an easy way to bind data to a layout. You inflate each of the widgets inside the view, then use the Yak object to fill out the list with data.

There are multiple ways to do it, but when I create adapters, I like for the adapter itself to hold the list and have the ability to add and remove objects. This makes it easy to tell the list "Hey you have a new object, redraw the list".

####Setting up the RecyclerView
Inside of our content_main.xml, we added a RecyclerView. This will be inflated inside of the MainActivity which we will need to add the following

```
LinearLayoutManager lm = new LinearLayoutManager(this);
adapter = new YakAdapter();

list = (RecyclerView) findViewById(R.id.list);
list.setHasFixedSize(true);
list.setLayoutManager(lm);
list.setAdapter(adapter);
```

The LinearLayout manager is what makes it a straight list. You could use the GridLayoutManager to make it look a little bit like Pintrest

- [LinearLayoutManager](http://developer.android.com/reference/android/support/v7/widget/LinearLayoutManager.html)
- [GridLayoutManager](http://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.html)
- [StaggeredGridLayoutManager](http://developer.android.com/reference/android/support/v7/widget/StaggeredGridLayoutManager.html)

####Show some data!
Now lets show some data!
We made a couple fake models from the User and Yak we created, then added them to the list!

![Final Output][final]
[final]: http://i.imgur.com/0voX20J.png