package com.now.live.livenow;

/**
 * Created by Jie Li on 14.03.16.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
//import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegisterLogin extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    private static final String TAG = "Now";

    //ImageView logo;
    Firebase ref;
    Firebase usersRef;
    private User user;
    private String password;
    private String email;
    private EditText nameField;
    private EditText passField;
    private EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_register_login);

        //Init firebase URL
        ref = new Firebase("https://live-now.firebaseio.com/");
        usersRef = ref.child("users");

        //Auto login
        autoLogIn();

        //Init textfields
        nameField = (EditText) findViewById(R.id.nameField);
        passField = (EditText) findViewById(R.id.passwordField);
        emailField = (EditText) findViewById(R.id.emailField);
        //logo = (ImageView) findViewById(R.id.logoIcon);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
        }

    }

    public void getInfo(String name, String password, String email){
        user = new User(email, name, password, "Picture", 20);
        setEmail(email);
        setPassword(password);
    }


    public void addNewUser(View view){
        getInfo(nameField.getText().toString(), passField.getText().toString(), emailField.getText().toString());

        usersRef.createUser(getEmail(), getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                String userKey = result.get("uid").toString();
                Firebase userID = usersRef.child(userKey);
                userID.setValue(user);
                Log.d(TAG, "New user added");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
                Log.e(TAG, "Something went wrong");
            }
        });

    }

    private void setEmail(String email){
        this.email = email;
    }

    private void setPassword(String password){
        this.password = password;
    }

    private String getEmail(){
        return email;
    }

    private String getPassword(){
        return password;
    }

    //Moves to logged-in activity
    public void goToLoggedInScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //Auto log in if token exists
    public void autoLogIn(){
        if (ref.getAuth() != null){
            goToLoggedInScreen();
        }
    }

    public void loginOnClick(View view){
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // Create a new Fragment to be placed in the activity layout
            LoginFragment loginFragment = new LoginFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            loginFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, loginFragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
