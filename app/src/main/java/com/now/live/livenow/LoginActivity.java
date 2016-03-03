package com.now.live.livenow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthStateListener;


public class LoginActivity extends AppCompatActivity {

    private EditText passwordField;
    private EditText emailField;
    private AuthStateListener mAuthStateListener;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //AutoLogin----------------------------------------
        ref = new Firebase("https://live-now.firebaseio.com/");

        autoLogIn();
        //-------------------------------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //TextFields INIT-------------------------------------
        passwordField = (EditText) findViewById(R.id.loginPassword);
        emailField = (EditText) findViewById(R.id.loginEmail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Goto new user activity
    public void newUser(View view){
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }

    //Gets data from fields and tries to login
    public void loginUser(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        LoginUser login = new LoginUser();
        login.loginAuth(email, password);
        logInSuccess(login.getAuthenticatedUser());
        ref.addAuthStateListener(mAuthStateListener);
    }

    //Checks if login was a success
    public void logInSuccess(AuthData authData){
        mAuthStateListener = new AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null){
                    System.out.println(authData);
                    goToLoggedInScreen();
                }
                else {
                    System.out.println("Wrong");
                }
            }
        };

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
}
