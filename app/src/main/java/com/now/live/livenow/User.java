package com.now.live.livenow;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jieli on 09.02.16.
 */
public class User{

    private String email;
    private String name;
    private Date birthDate;
    private String picture;
    private String password;
    private String description;
    private String gender;
    private int discoverRange;
    private ArrayList<Observer> groups;

    //Password unnecessary?

    public User(){
        //Empty constructor required for firebase to work!!
    }

    public User(String email, String name, String password, String picture, int discoverRange){
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.password = password;
        this.discoverRange = discoverRange;
    }

    public User(String name, String picture, int discoverRange, Date birthDate, String description, String gender){
        this.name = name;
        this.picture = picture;
        this.discoverRange = discoverRange;
        this.birthDate = birthDate;
        this.description = description;
        this.gender = gender;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public String getPicture(){
        return this.picture;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setDiscoverRange(int discoverRange){
        this.discoverRange = discoverRange;
    }

    public int getDiscoverRange(){
        return this.discoverRange;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public Date getBirthDate(){
        return this.birthDate;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
