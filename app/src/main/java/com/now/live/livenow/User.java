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
    private int discoverRange;
    private ArrayList<Observer> groups;


    public User(String email, String name, String password, String picture, int discoverRange){
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.password = password;
        this.discoverRange = discoverRange;
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

    public void setPassword(String password){
        this.password = password;
    }

    public void setDiscoverRange(int discoverRange){
        this.discoverRange = discoverRange;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

}
