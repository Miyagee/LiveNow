package com.now.live.livenow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jieli on 25.02.16.
 */
public class GroupList extends UserList{

    public GroupList(String user, String listName, boolean privateSetting){
        super(listName, privateSetting);
        addUser(user);
    }

}
