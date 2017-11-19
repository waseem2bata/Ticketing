package com.waseem.ticketing.activity.UserApp.activity;

/**
 * Created by waseem on 2/18/2017.
 */



public class listItem {



    public String name;
    public String email;



    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public listItem(String name, String email) {
        this.name = name;
        this.email = email;
    }




}
