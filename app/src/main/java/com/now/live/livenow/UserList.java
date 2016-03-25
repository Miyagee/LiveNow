package com.now.live.livenow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jieli on 25.03.16.
 */
public abstract class UserList {

    private List<String> users;
    private String listName;
    private int userCount;
    private boolean privateSetting;

    public UserList(){

    }

    public UserList(String listName, boolean privateSetting){
        this.users = new ArrayList<>();
        this.listName = listName;
        this.userCount = users.size();
        this.privateSetting = privateSetting;

    }

    public void addUser(String user){
        this.users.add(user);
        incrementUserCount();
    }

    public void removeUser(String user){
        this.users.remove(user);
        decrementUserCount();
    }

    public void incrementUserCount(){
        this.userCount +=1;
    }

    public void decrementUserCount(){
        this.userCount -=1;
    }

    public int getUserCount(){
        return userCount;
    }

    public List<String> getUsers(){
        return users;
    }

    public String getListName(){
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setPrivateSetting(boolean privateSetting) {
        this.privateSetting = privateSetting;
    }

    public boolean getPrivateSetting() {
        return privateSetting;
    }

    public void setUsersList(ArrayList<String> users){
        this.users = users;
    }
}
