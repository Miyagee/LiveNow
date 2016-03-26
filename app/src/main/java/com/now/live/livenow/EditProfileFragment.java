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

import java.util.Calendar;
import java.util.Date;


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
        new DownloadImageTask((ImageView) view.findViewById(R.id.picture_profile))
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
        monthPicker.setOnValueChangedListener(new monthNumPicker());
        yearPicker.setOnValueChangedListener(new yearNumPicker());

        //Date calc
        dateCalc = new DateCalculations();
        dateCalc.init();
        if(!StaticHelperClasses.isDateNull(user.getBirthDate())){
            dateCalc.setBirth(user.getBirthDate());
            dateCalc.setBirthDay();
            dateCalc.setBirthMonth();
            dateCalc.setBirthYear();
        }

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

    private class monthNumPicker implements NumberPicker.OnValueChangeListener{

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            //Check for months and days
            if (newVal == 2 && dateCalc.isLeapYear(yearPicker.getValue())){
                dayPicker.setMaxValue(dateCalc.setFebMaxDate(true));
            }else if(newVal == 2 && !dateCalc.isLeapYear(yearPicker.getValue())){
                dayPicker.setMaxValue(dateCalc.setFebMaxDate(false));
            }else if(dateCalc.checkThirty(newVal)){
                dayPicker.setMaxValue(30);
            }else if(dateCalc.checkThirtyOne(newVal)){
                dayPicker.setMaxValue(31);
            }
        }
    }

    private class yearNumPicker implements NumberPicker.OnValueChangeListener{

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (dateCalc.isLeapYear(newVal) && monthPicker.getValue() == 2){
                dayPicker.setMaxValue(dateCalc.setFebMaxDate(true));
            }else if(!dateCalc.isLeapYear(newVal) && monthPicker.getValue() == 2){
                dayPicker.setMaxValue(dateCalc.setFebMaxDate(false));
            }
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

    public void autoCheckRadioButton(String userGender){
        if (userGender.equals("Male")){
            maleRadio.setChecked(true);
            femaleRadio.setChecked(false);
            otherRadio.setChecked(false);
        }else if(userGender.equals("Female")){
            maleRadio.setChecked(false);
            femaleRadio.setChecked(true);
            otherRadio.setChecked(false);
        }else{
            maleRadio.setChecked(false);
            femaleRadio.setChecked(false);
            otherRadio.setChecked(true);
        }
    }


    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        getFields();

        return this.user;
    }

    public void setNumPickerMaxValDB(DateCalculations dateCalc){
        if(dateCalc.checkThirty(dateCalc.getBirthMonth())){
            dayPicker.setMaxValue(30);
        }else if(dateCalc.checkThirtyOne(dateCalc.getBirthMonth())){
            dayPicker.setMaxValue(31);
        }else{
            if(dateCalc.isLeapYear(dateCalc.getBirthYear())){
                dayPicker.setMaxValue(dateCalc.setFebMaxDate(true));
            }else{
                dayPicker.setMaxValue(dateCalc.setFebMaxDate(false));
            }
        }
    }

    public void setFields(){
        String distanceConvert = Integer.toString(user.getDiscoverRange()) + " km";

        nameView.setText(user.getName());

        if(StaticHelperClasses.checkNull(user.getDescription())){
            descriptionView.setHint("Description");
        }else{
            descriptionView.setText(user.getDescription());
        }

        if(!StaticHelperClasses.checkNull(user.getGender())){
            autoCheckRadioButton(user.getGender());
        }

        //Not going to change
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        dayPicker.setMinValue(1);
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(dateCalc.getCurrentYear());

        //Checks based on date from DB
        if(StaticHelperClasses.isDateNull(user.getBirthDate())){
            dayPicker.setMaxValue(31);
            monthPicker.setValue(1);
            yearPicker.setValue(dateCalc.getCurrentYear());
        }else{
            setNumPickerMaxValDB(dateCalc);
            dayPicker.setValue(dateCalc.getBirthDay());
            monthPicker.setValue(dateCalc.getBirthMonth());
            yearPicker.setValue(dateCalc.getBirthYear());
        }

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
        Calendar tempBirth = Calendar.getInstance();
        Date birth;
        int distance;
        String gender;
        String picture;

        //Getting the fields and applying them to
        if(!StaticHelperClasses.isEmpty(nameView)){
            name = nameView.getText().toString();
        }else{
            name = null;
        }

        if(!StaticHelperClasses.checkNull(user.getPicture())){
            picture = user.getPicture();
        }else{
            picture = "https://pixabay.com/static/uploads/photo/2016/02/29/20/17/almond-blossom-1229138_960_720.jpg";
        }

        if(!StaticHelperClasses.isEmpty(descriptionView)){
            description = descriptionView.getText().toString();
        } else{
            description = null;
        }

        if(StaticHelperClasses.checkRadioGroupChecked(genderGroup)){
            gender = getCheckedRadioValue();
        }else{
            gender = null;
        }

        day = dayPicker.getValue();
        month = monthPicker.getValue();
        year = yearPicker.getValue();
        tempBirth.set(year, month-1, day);
        birth = new Date(tempBirth.getTimeInMillis());
        distance = distanceBar.getProgress();

        user = new User(name,picture, distance, birth, description, gender);

    }



}
