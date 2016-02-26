package com.now.live.livenow;

import java.util.ArrayList;

/**
 * Created by jieli on 25.02.16.
 */
public class Group{

    private ArrayList<User> users;
    private String name;
    private int members;

    public Group(User user, String name){
        this.users = new ArrayList<User>();
        this.name = name;
        users.add(user);
        this.members = users.size();

    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public void incrementMember(){
        members+=1;
    }

    public void decrementMember(){
        members-=1;
    }


}
