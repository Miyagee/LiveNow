package com.now.live.livenow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import com.now.live.livenow.swipecard.FlingCardListener;
import com.now.live.livenow.swipecard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    private static final String TAG = "Now";

    //Swipecard decs
    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Event> al;
    private SwipeFlingAdapterView flingContainer;

    Firebase ref;
    Firebase userRef;
    Firebase eventRef;

    //User info fields
    private String userUid;
    private String nameUser;
    private String pictureUser;
    private String genderUser;
    private String descriptionUser;
    private Date birthDateUser;
    private int distanceUser;
    private FriendList friendListData;
    private List<String> friendsID;
    private List<String> friendsName;
    private User user;
    private ImageButton homeButton;
    private ImageButton listButton;
    private ImageButton createButton;
    private ImageButton socialButton;
    private ImageButton profileButton;

    //Event info fields
    private Event event;

    //Used to iterate through users to get name
    private String nameFriend;

    //Layout
    private RelativeLayout mainPage;

    //Fragments
    private ProfileFragment profileFragment;
    private EditProfileFragment editProfileFragment;
    private CreateEventFragment createEventFragment;

    public static void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase set-up
        ref = new Firebase("https://live-now.firebaseio.com/");
        userRef = ref.child("users/" + ref.getAuth().getUid() + "/");
        //Log.d(TAG, ref.getAuth().getUid());
        userUid = ref.getAuth().getUid();
        friendsName = new ArrayList<>();
        //Log.d(TAG, userRef.toString());
        eventRef = ref.child("events/" + ref.getAuth().getUid() + "/");

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        mainPage = (RelativeLayout) findViewById(R.id.mainPage);

        getUserData();
        getUserFriendList();

        //Toolbar
        homeButton = (ImageButton) findViewById(R.id.home_button);
        listButton = (ImageButton) findViewById(R.id.calendar_button);
        createButton = (ImageButton) findViewById(R.id.activity_create_button);
        socialButton = (ImageButton) findViewById(R.id.social_button);
        profileButton = (ImageButton) findViewById(R.id.profile_button);

        homeButton.setImageResource(R.drawable.toolbar_home_selected);
        listButton.setImageResource(R.drawable.toolbar_calendar);
        createButton.setImageResource(R.drawable.toolbar_create);
        socialButton.setImageResource(R.drawable.toolbar_groups);
        profileButton.setImageResource(R.drawable.toolbar_friends);

        //Checks for fragment alive
        if (findViewById(R.id.fragment_container_main) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
        }

        al = new ArrayList<>();
        al.add(new Event(
                "Tea & Cake",
                "Fancy a bit of afternoon tea?",
                null,
                "16:00",
                "Diggs"));
        al.add(new Event("Bowling",
                "A game of bowling for the whole family",
                null,
                "18:00",
                "Bowling Land"));
        al.add(new Event("Singing",
                "Choir practice for beginners",
                null,
                "10:00",
                "Nidaros Domen"));
        al.add(new Event("Fruit Picking",
                "Collect wild friuts with me :)",
                null,
                "14:00",
                "Bymarka"));
        al.add(new Event("Programming",
                "I can teach you Python programming.",
                null,
                "09:00",
                "NTNU"));

        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();

                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the right!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView eventTitle;
        public TextView eventText;
        public TextView eventDate;
        public TextView eventTime;
        public TextView eventPlace;
        public ImageView cardAvatar;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Event> parkingList;
        public Context context;

        private MyAppAdapter(List<Event> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.eventTitle = (TextView) rowView.findViewById(R.id.title);
                viewHolder.eventText = (TextView) rowView.findViewById(R.id.description);
                viewHolder.eventTime = (TextView) rowView.findViewById(R.id.time);
                viewHolder.eventDate = (TextView) rowView.findViewById(R.id.date);
                viewHolder.eventPlace = (TextView) rowView.findViewById(R.id.place);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardAvatar = (ImageView) rowView.findViewById(R.id.cardAvatar);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.eventText.setText(parkingList.get(position).getDescription() + "");
            viewHolder.eventTitle.setText(parkingList.get(position).getTitle() + "");
            viewHolder.eventTime.setText("Time: " + parkingList.get(position).getTime() + "");
            viewHolder.eventDate.setText("Date: " + parkingList.get(position).getDate() + "");
            viewHolder.eventPlace.setText("Location: " + parkingList.get(position).getPlace());

            return rowView;
        }
    }

    public void createEventView(View view) {
        removeFragment(view);

        if (findViewById(R.id.fragment_container_main) != null) {
            hideMain();
            createEventFragment = new CreateEventFragment();
            createEventFragment.setEvent(event);
            createEventFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, createEventFragment).commit();
        }
    }

    public void saveEvent(View view) {
        event = createEventFragment.getEvent();
        eventRef.setValue(event);

    }

    public void getUserData(){

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);

                friendListData = user.getFriendList();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void getUserFriendList(){
        Firebase friendListRef = userRef.child("friendList/users");
        friendListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };
                friendsID = snapshot.getValue(t);
                System.out.println(friendsID);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println(firebaseError);
            }

        });

    }

    //TODO SHOULD RUN IN OWN FRAGMENT AND MAKE ADD FRIEND AND REMOVE FUNCTIONS, might change friends name to (key, value)
    //Also add if null
    public void processFriendsID(View view){
        Firebase tempBase;
        ValueEventListener fnm = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameFriend = dataSnapshot.getValue().toString();
                friendsName.add(nameFriend);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        for(String friendID: friendsID){
            tempBase = ref.child("users/" + friendID + "/name");
            tempBase.addListenerForSingleValueEvent(fnm);
        }
        //TODO remove listeners after usage when friends menu closes

        System.out.println(friendsName);

    }
    //-----------------------------------------------------------


    //Log out button
    public void logOut(View view){
        ref.unauth();
        Intent intent = new Intent(this, RegisterLogin.class);
        startActivity(intent);
    }

    public void profileView(View view){
        removeFragment(view);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container_main) != null) {
            hideMain();
            setSelected("profile");

            // Create a new Fragment to be placed in the activity layout
            profileFragment = new ProfileFragment();

            //Maybe bad practice?
            profileFragment.setUser(user);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            profileFragment.setArguments(getIntent().getExtras());


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, profileFragment).commit();

        }
    }

    public void editProfileView(View view){
        removeFragment(view);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container_main) != null) {
            hideMain();
            setSelected("profile");

            // Create a new Fragment to be placed in the activity layout
            editProfileFragment = new EditProfileFragment();

            //Maybe bad practice?
            editProfileFragment.setUser(user);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            editProfileFragment.setArguments(getIntent().getExtras());


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, editProfileFragment).commit();

        }
    }

    public void saveProfile(View view){
        //Getting user from fragment_edit_profile
        user = editProfileFragment.getUser();

        userRef.child("name").setValue(user.getName());
        userRef.child("gender").setValue(user.getGender());
        userRef.child("birthDate").setValue(user.getBirthDate());
        userRef.child("discoverRange").setValue(user.getDiscoverRange());
        userRef.child("description").setValue(user.getDescription());
        userRef.child("picture").setValue(user.getPicture());

        profileView(view);
    }

    public void removeFragment(View view){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_main);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            showMain();


        }

    }

    public void hideMain(){
        if (mainPage.getVisibility() == View.VISIBLE){
            mainPage.setVisibility(View.INVISIBLE);
        }

    }

    public void showMain(){
        if(mainPage.getVisibility() == View.INVISIBLE){
            mainPage.setVisibility(View.VISIBLE);
            setSelected("home");
        }
    }

    public void setSelected(String selected){
        switch(selected){
            case "home":
                homeButton.setImageResource(R.drawable.toolbar_home_selected);
                listButton.setImageResource(R.drawable.toolbar_calendar);
                socialButton.setImageResource(R.drawable.toolbar_groups);
                profileButton.setImageResource(R.drawable.toolbar_friends);
                break;
            case "list":
                homeButton.setImageResource(R.drawable.toolbar_home);
                listButton.setImageResource(R.drawable.toolbar_calendar_selected);
                socialButton.setImageResource(R.drawable.toolbar_groups);
                profileButton.setImageResource(R.drawable.toolbar_friends);
                break;
            case "social":
                homeButton.setImageResource(R.drawable.toolbar_home);
                listButton.setImageResource(R.drawable.toolbar_calendar);
                socialButton.setImageResource(R.drawable.toolbar_groups_selected);
                profileButton.setImageResource(R.drawable.toolbar_friends);
                break;
            case "profile":
                homeButton.setImageResource(R.drawable.toolbar_home);
                listButton.setImageResource(R.drawable.toolbar_calendar);
                socialButton.setImageResource(R.drawable.toolbar_groups);
                profileButton.setImageResource(R.drawable.toolbar_friends_selected);
                break;
            default:
                homeButton.setImageResource(R.drawable.toolbar_home_selected);
                listButton.setImageResource(R.drawable.toolbar_calendar);
                socialButton.setImageResource(R.drawable.toolbar_groups);
                profileButton.setImageResource(R.drawable.toolbar_friends);
                break;
        }

    }

}
