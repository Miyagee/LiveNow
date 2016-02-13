package com.now.live.livenow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class CreateUser extends AppCompatActivity {

    Intent intent = getIntent();
    Firebase ref = new Firebase("https://live-now.firebaseio.com/");
    Firebase usersRef = ref.child("users");
    private User user;
    private String password;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void getInfo(String name, String password, String email){
        user = new User(email, name, password, "Picture", 20);
        setEmail(email);
        setPassword(password);
    }

    public void addNewUser(View view){
        getInfo("Jie Li", "123456", "jieli280495@gmail.com");

        usersRef.createUser(getEmail(), getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                String userKey = result.get("uid").toString();
                Firebase userID = usersRef.child(userKey);
                userID.setValue(user);
                System.out.println("Successfully created user account with uid: " + result.get("uid"));
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
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



}
