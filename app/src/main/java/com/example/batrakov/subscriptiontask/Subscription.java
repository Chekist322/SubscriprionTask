package com.example.batrakov.subscriptiontask;

/**
 * Created by batrakov on 21.09.17.
 */

public class Subscription {
    private String mName;
    private String mParkCode;
    private String mAccessCode;

    Subscription(String aName, String aParkCode, String aAccessCode){
        mName = aName;
        mParkCode = aParkCode;
        mAccessCode = aAccessCode;
    }
}
