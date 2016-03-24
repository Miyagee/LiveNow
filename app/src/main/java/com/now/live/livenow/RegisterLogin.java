package com.now.live.livenow;

/**
 * Created by Jie Li on 14.03.16.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.RelativeLayout;
//import android.widget.ImageView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegisterLogin extends AppCompatActivity{

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
    //private Button loginButton;
    //private Button signUpButton;
    private RelativeLayout signUpLayout;
    private Firebase.AuthStateListener mAuthStateListener;
    private LoginFragment loginFragment;

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
        //loginButton = (Button) findViewById(R.id.login_button);
        //signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpLayout = (RelativeLayout) findViewById(R.id.signUpLayout);
        //logo = (ImageView) findViewById(R.id.logoIcon);

        if (findViewById(R.id.fragment_container_register_login) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
        }

    }

    public void getInfo(String name, String password, String email){
        user = new User(email, name, password, "https://pixabay.com/static/uploads/photo/2016/02/29/20/17/almond-blossom-1229138_960_720.jpg", 20);
        setEmail(email);
        setPassword(password);
    }

    //TODO user exists
    public void addNewUser(View view){
        getInfo(nameField.getText().toString(), passField.getText().toString(), emailField.getText().toString());

        usersRef.createUser(getEmail(), getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                String userKey = result.get("uid").toString();
                Firebase userID = usersRef.child(userKey);
                userID.setValue(user);
                Log.d(TAG, "New user added");
                loginAfterSignUp();
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
        if (findViewById(R.id.fragment_container_register_login) != null) {
            hideSignUp();
            // Create a new Fragment to be placed in the activity layout
            loginFragment = new LoginFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            loginFragment.setArguments(getIntent().getExtras());


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_register_login, loginFragment).commit();


        }
    }

    public void cancelOnClick(View view){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_register_login);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            showSignUp();
        }

    }


    public void hideSignUp(){
        signUpLayout.setVisibility(View.INVISIBLE);
    }

    public void showSignUp(){
        signUpLayout.setVisibility(View.VISIBLE);
    }

    //Gets data from fields and tries to login
    public void authenticateUserLogin(View view){
        String email = loginFragment.getEmail();
        String password = loginFragment.getPassword();
        LoginUser login = new LoginUser();
        login.loginAuth(email, password);
        logInSuccess(login.getAuthenticatedUser());
        ref.addAuthStateListener(mAuthStateListener);
    }

    //Checks if login was a success
    public void logInSuccess(AuthData authData){
        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null){
                    System.out.println(authData);
                    goToLoggedInScreen();
                }
                else {
                    System.out.println("Buffering");
                }
            }
        };
    }

    //Login straight after signing up to reduce actions by user
    public void loginAfterSignUp(){
        String email = emailField.getText().toString();
        String password = passField.getText().toString();
        LoginUser login = new LoginUser();
        login.loginAuth(email, password);
        logInSuccess(login.getAuthenticatedUser());
        ref.addAuthStateListener(mAuthStateListener);
    }
}
