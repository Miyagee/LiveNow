package com.now.live.livenow;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jieli on 23.03.16.
 */
public class DateCalculations {

    private ArrayList<Integer> thirty_one;
    private ArrayList<Integer> thirty;
    private ArrayList<Integer> feb;
    private Date now;
    private DateFormat yearFormat;
    private DateFormat monthFormat;
    private DateFormat dayFormat;
    private Date birth;
    private int currentYear;
    private int birthMonth;
    private int birthYear;
    private int birthDay;
    private int age;

    public void init(){
        //Months with 31 days
        thirty_one = new ArrayList<>();
        thirty = new ArrayList<>();
        feb = new ArrayList<>();

        //Adding months with 31 days
        thirty_one.add(1);
        thirty_one.add(3);
        thirty_one.add(5);
        thirty_one.add(7);
        thirty_one.add(8);
        thirty_one.add(10);
        thirty_one.add(12);

        //Adding months with 30 days
        thirty.add(4);
        thirty.add(6);
        thirty.add(9);
        thirty.add(11);

        //Feb add days
        feb.add(28);
        feb.add(29);

        now = new Date();
        yearFormat = new SimpleDateFormat("yyyy");
        monthFormat = new SimpleDateFormat("MM");
        dayFormat = new SimpleDateFormat("dd");
        setCurrentYear();

    }

    public boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int setFebMaxDate(boolean leapYear){
        if (leapYear){
            return feb.get(1);
        }else {
            return feb.get(0);
        }
    }

    public boolean checkThirty(int month){
        if (thirty.contains(month)){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkThirtyOne(int month){
        if(thirty_one.contains(month)){
            return true;
        }else{
            return false;
        }
    }

    /*
    public Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }*/

    public int getCurrentYear(){
        return currentYear;
    }

    public void setCurrentYear(){
        currentYear = Integer.parseInt(yearFormat.format(now));
    }

    public void setBirth(Date birth){
        this.birth = birth;
    }

    public void setBirthDay(){
        birthDay = Integer.parseInt(dayFormat.format(birth));
    }

    public void setBirthMonth(){
        birthMonth = Integer.parseInt(monthFormat.format(birth));
    }

    public void setBirthYear(){
        birthYear = Integer.parseInt(yearFormat.format(birth));
    }

    public int getBirthYear(){
        return birthYear;
    }

    public int getBirthMonth(){
        return birthMonth;
    }

    public int getBirthDay(){
        return birthDay;
    }

    public void calculateAge(){
        long timeBetween = now.getTime() - birth.getTime();
        double yearsBetween = timeBetween / 3.156e+10;
        age = (int) Math.floor(yearsBetween);
    }

    public int getAge(){
        return age;
    }

}
