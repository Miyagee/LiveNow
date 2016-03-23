package com.now.live.livenow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {


    private User user;

    //User data display
    private EditText nameView;
    private NumberPicker dayPicker;
    private NumberPicker monthPicker;
    private NumberPicker yearPicker;
    private RadioGroup genderGroup;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    private RadioButton otherRadio;
    private EditText descriptionView;
    private TextView distanceView;
    private SeekBar distanceBar;
    private String checkedValue;

    //Date calculation class
    private DateCalculations dateCalc;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_profile,null);
        new DownloadImageTask((ImageView) view.findViewById(R.id.picture_profile_edit))
                .execute(user.getPicture());

        //Init components
        nameView = (EditText) view.findViewById(R.id.name_profile_edit);
        dayPicker = (NumberPicker) view.findViewById(R.id.birth_day);
        monthPicker = (NumberPicker) view.findViewById(R.id.birth_month);
        yearPicker = (NumberPicker) view.findViewById(R.id.birth_year);
        genderGroup = (RadioGroup) view.findViewById(R.id.gender_group);
        maleRadio = (RadioButton) view.findViewById(R.id.male_radio);
        femaleRadio = (RadioButton) view.findViewById(R.id.female_radio);
        otherRadio = (RadioButton) view.findViewById(R.id.other_radio);
        descriptionView = (EditText) view.findViewById(R.id.description_profile_edit);
        distanceView = (TextView) view.findViewById(R.id.distance_number_profile);
        distanceBar = (SeekBar) view.findViewById(R.id.distance_profile);

        distanceBar.setEnabled(true);

        //adding listener to distance bar
        distanceBar.setOnSeekBarChangeListener(new distanceSeekBarListener());

        //Date calc
        dateCalc = new DateCalculations();
        dateCalc.init();

        //Setting fields
        setFields();

        // Inflate the layout for this fragment
        return view;
    }

    private class distanceSeekBarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String progressText = Integer.toString(progress) + " km";
            distanceView.setText(progressText);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public String getCheckedRadioValue(){

        int selectedID = genderGroup.getCheckedRadioButtonId();

        if (selectedID == maleRadio.getId()){
            checkedValue = maleRadio.getText().toString();
        } else if (selectedID == femaleRadio.getId()){
            checkedValue = femaleRadio.getText().toString();
        }else{
            checkedValue = otherRadio.getText().toString();
        }
        return checkedValue;
    }


    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        getFields();

        return this.user;
    }

    public void setFields(){
        String distanceConvert = Integer.toString(user.getDiscoverRange()) + " km";

        nameView.setHint(user.getName());


        descriptionView.setHint("Description");

        //TODO make gendergroup already checked from DB


        //TODO change this to match month and year (feb)
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        //Months
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        //Year TODO need to change max value to current date.year
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(2016);
        yearPicker.setValue(2016);

        //Distance
        distanceView.setText(distanceConvert);
        distanceBar.setProgress(user.getDiscoverRange());
    }



    public void getFields(){
        String name;
        String description;
        int day;
        int month;
        int year;
        int distance;
        String gender;

        //Getting the fields and applying them to
        if(!StaticHelperClasses.isEmpty(nameView)){
            name = nameView.getText().toString();
        }

        if(!StaticHelperClasses.isEmpty(descriptionView)){
            description = descriptionView.getText().toString();
        } else{
            description = null;
        }

        if(StaticHelperClasses.checkRadioGroupChecked(genderGroup)){
            gender = getCheckedRadioValue();
        }


    }



}
