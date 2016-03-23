package com.now.live.livenow;

import java.util.ArrayList;

/**
 * Created by jieli on 23.03.16.
 */
public class DateCalculations {

    private ArrayList<Integer> thirty_one;
    private ArrayList<Integer> thirty;
    private ArrayList<Integer> feb;

    public void init(){
        //Months with 31 days
        thirty_one = new ArrayList<>();
        thirty = new ArrayList<>();
        feb = new ArrayList<>();

    }

    public static boolean isLeapYear(int year) {
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




}
