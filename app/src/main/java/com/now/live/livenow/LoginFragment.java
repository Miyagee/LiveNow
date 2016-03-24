package com.now.live.livenow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class LoginFragment extends Fragment {

    private EditText loginEmailField;
    private EditText loginPasswordField;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_login,null);


        loginEmailField = (EditText) view.findViewById(R.id.login_email);
        loginPasswordField = (EditText) view.findViewById(R.id.login_password);
        // Inflate the layout for this fragment
        return view;
    }

    public String getEmail(){
        return loginEmailField.getText().toString();
    }

    public String getPassword(){
        return loginPasswordField.getText().toString();
    }
}
