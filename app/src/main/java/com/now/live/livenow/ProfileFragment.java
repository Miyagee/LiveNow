package com.now.live.livenow;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;



public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER = "user";

    private User user;

    //User data display
    private TextView nameView;
    private TextView genderView;
    private TextView descriptionView;
    private TextView ageView;
    private TextView distanceView;
    private SeekBar distanceBar;
    private DateCalculations dateCalc;
    private int age;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Setting stuff for interaction
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_profile,null);
        new DownloadImageTask((ImageView) view.findViewById(R.id.picture_profile))
                .execute(user.getPicture());

        //Init textViews
        nameView = (TextView) view.findViewById(R.id.name_profile);
        genderView = (TextView) view.findViewById(R.id.gender_profile);
        ageView = (TextView) view.findViewById(R.id.age_profile);
        descriptionView = (TextView) view.findViewById(R.id.description_profile);
        distanceView = (TextView) view.findViewById(R.id.distance_number_profile);
        distanceBar = (SeekBar) view.findViewById(R.id.distance_profile);

        distanceBar.setEnabled(false);

        dateCalc = new DateCalculations();
        dateCalc.init();
        if(!StaticHelperClasses.isDateNull(user.getBirthDate())){
            dateCalc.setBirth(user.getBirthDate());
            dateCalc.calculateAge();
            age=dateCalc.getAge();
        }

        //Setting fields
        setFields();

        // Inflate the layout for this fragment
        return view;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setFields(){
        String distanceConvert = Integer.toString(user.getDiscoverRange()) + " km";
        String ageConvert = Integer.toString(age);

        nameView.setText(user.getName());


        genderView.setText(user.getGender());

        descriptionView.setText(user.getDescription());

        ageView.setText(ageConvert);

        //Distance
        distanceView.setText(distanceConvert);
        distanceBar.setProgress(user.getDiscoverRange());
    }


}
