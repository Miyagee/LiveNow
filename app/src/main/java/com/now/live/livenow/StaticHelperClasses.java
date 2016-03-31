package com.now.live.livenow;

import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Date;

/**
 * Created by jieli on 23.03.16.
 */
public class StaticHelperClasses {

    public static boolean checkNull(String s){
        return s==null;
    }

    public static boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkRadioGroupChecked(RadioGroup group){
        if(group.getCheckedRadioButtonId() == -1){
            return  false;
        }else{
            return true;
        }
    }

    public static boolean isDateNull(Date d){
        if (d==null){
            return true;
        }else{
            return false;
        }
    }


}