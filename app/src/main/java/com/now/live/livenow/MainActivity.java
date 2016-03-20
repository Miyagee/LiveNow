package com.now.live.livenow;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Now";

    Firebase ref;
    Firebase userRef;
    private TextView testText;
    //private String userUid;
    private String userNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref = new Firebase("https://live-now.firebaseio.com/");
        userRef = ref.child("users/" + ref.getAuth().getUid()+ "/");
        Log.d(TAG, ref.getAuth().getUid());
        //userUid = ref.getAuth().getUid();
        Log.d(TAG, userRef.toString());

        testText = (TextView) findViewById(R.id.test_db_get_data);

        getName();

    }

    public void getName(){

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user =  snapshot.getValue(User.class);
                userNameString = user.getName();
                setTestText();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void setTestText(){
        testText.setText(userNameString);
    }

    //Log out button
    public void logOut(View view){
        ref.unauth();
        Intent intent = new Intent(this, RegisterLogin.class);
        startActivity(intent);
    }



}
