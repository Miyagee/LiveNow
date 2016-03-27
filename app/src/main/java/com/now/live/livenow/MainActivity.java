package com.now.live.livenow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "Now";

    Firebase ref;
    Firebase userRef;

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

    //Used to iterate through users to get name
    private String nameFriend;


    //Layout
    private RelativeLayout mainPage;

    //Fragments
    private ProfileFragment profileFragment;
    private EditProfileFragment editProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref = new Firebase("https://live-now.firebaseio.com/");
        userRef = ref.child("users/" + ref.getAuth().getUid() + "/");
        //Log.d(TAG, ref.getAuth().getUid());
        userUid = ref.getAuth().getUid();
        friendsName = new ArrayList<>();
        //Log.d(TAG, userRef.toString());

        mainPage = (RelativeLayout) findViewById(R.id.mainPage);

        getUserData();
        getUserFriendList();

        //Checks for fragment alive
        if (findViewById(R.id.fragment_container_main) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
        }

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
        }
    }


}
