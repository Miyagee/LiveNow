package com.now.live.livenow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener{

    private static final String TAG = "Now";

    Firebase ref;
    Firebase userRef;
    private TextView testText;

    //User info fields
   // private String userUid;
    private String nameUser;
    private String pictureUser;
    private String genderUser;
    private String descriptionUser;
    private Date birthDateUser;
    private int distanceUser;
    private User user;


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
        userRef = ref.child("users/" + ref.getAuth().getUid()+ "/");
        //Log.d(TAG, ref.getAuth().getUid());
        //userUid = ref.getAuth().getUid();
        //Log.d(TAG, userRef.toString());

        testText = (TextView) findViewById(R.id.test_db_get_data);

        mainPage = (RelativeLayout) findViewById(R.id.mainPage);

        getUserData();

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
                nameUser = user.getName();
                pictureUser = user.getPicture();
                genderUser = user.getGender();
                descriptionUser = user.getDescription();
                birthDateUser = user.getBirthDate();
                distanceUser = user.getDiscoverRange();


                //Test
                setTestText();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    //test function
    public void setTestText(){
        testText.setText(nameUser + "\n" + pictureUser + "\n" + genderUser + "\n" + descriptionUser + "\n" + birthDateUser + "\n" + distanceUser);
    }

    //Log out button
    public void logOut(View view){
        ref.unauth();
        Intent intent = new Intent(this, RegisterLogin.class);
        startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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

        userRef.setValue(user);

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
