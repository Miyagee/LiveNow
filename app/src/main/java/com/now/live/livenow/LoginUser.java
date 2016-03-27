package com.now.live.livenow;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    private Toast errorNotifier;
    private String errorMessage;
    private int errorDuration;
    private Context mContext;

    public LoginUser(Context context){
        mContext = context;
        errorMessage = "";
        errorDuration = Toast.LENGTH_SHORT;
    }

    //TODO add error username or password
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

                switch (firebaseError.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        // handle a non existing user
                        errorMessage = "Not a registered user";
                        errorNotifier = Toast.makeText(mContext, errorMessage, errorDuration);
                        errorNotifier.show();
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        // handle an invalid password
                        errorMessage = "Wrong username or password";
                        errorNotifier = Toast.makeText(mContext, errorMessage, errorDuration);
                        errorNotifier.show();
                        break;
                    case FirebaseError.INVALID_EMAIL:
                        //handle an invalid email
                        errorMessage = "Wrong username or password";
                        errorNotifier = Toast.makeText(mContext, errorMessage, errorDuration);
                        errorNotifier.show();
                        break;
                    case FirebaseError.NETWORK_ERROR:
                        // handle an invalid password
                        errorMessage = "Error connecting to server, make sure you are connected to the web";
                        errorNotifier = Toast.makeText(mContext, errorMessage, errorDuration);
                        errorNotifier.show();
                        break;
                    default:
                        errorMessage = "Something went wrong";
                        errorNotifier = Toast.makeText(mContext, errorMessage, errorDuration);
                        errorNotifier.show();
                        break;
                }

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
