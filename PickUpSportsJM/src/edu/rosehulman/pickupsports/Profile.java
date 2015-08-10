package edu.rosehulman.pickupsports;

import java.util.ArrayList;

/**
 * Created by Charlie on 7/26/2015.
 */
public class Profile{

    private long mId;
    private String mEmail;
    private String mPhoneNumber;
    private String mName;
    private ArrayList<Long> mFriends;
    private ArrayList<Long> mEvents;

    public Long getId() {return mId;}
    public void setId(long id) {this.mId = id;}

    public String getEmail() {return this.mEmail;}
    public void setEmail(String email) {this.mEmail = email;}

    public String getPhoneNumber() {return mPhoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.mPhoneNumber = phoneNumber;}

    public String getName() {return this.mName;}
    public void setName(String name){this.mName = name;}

    public ArrayList<Long> getFriends(){return mFriends;}
    public void addFriend(Long id){this.mFriends.add(id);}

    public ArrayList<Long> getEvents(){return mEvents;}
    public void addEvent(Long id){this.mEvents.add(id);}
}
