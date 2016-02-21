package com.now.live.livenow;

import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by jieli on 19.02.16.
 */
public class LoginUser {

    Firebase ref = new Firebase("https://live-now.firebaseio.com/");
    //Firebase usersRef = ref.child("users");

    public void loginAuth(String email, String password){
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                System.out.print("Error");
            }
        });
    }



}
