package com.now.live.livenow;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by jieli on 19.02.16.
 */
public class LoginUser {

    Firebase ref = new Firebase("https://live-now.firebaseio.com/");
    //Firebase usersRef = ref.child("users");
    private AuthData mAuthData;

    public void loginAuth(String email, String password){
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                setAuthenticatedUser(authData);
                //System.out.println(mAuthData);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                System.out.print(firebaseError);

            }
        });


    }

    public AuthData getAuthenticatedUser(){
        return mAuthData;
    }

    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            /* show a provider specific status text */
            String name = null;
            if (authData.getProvider().equals("facebook")
                    || authData.getProvider().equals("google")
                    || authData.getProvider().equals("twitter")) {
                name = (String) authData.getProviderData().get("displayName");
            } else if (authData.getProvider().equals("anonymous")
                    || authData.getProvider().equals("password")) {
                name = authData.getUid();
            }
        } else {
            /* No authenticated user show all the login buttons */
        }
        this.mAuthData = authData;
        /* invalidate options menu to hide/show the logout button */
        //supportInvalidateOptionsMenu();
    }


}
