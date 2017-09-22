package com.example.batrakov.subscriptiontask;

import java.io.Serializable;

/**
 * Created by batrakov on 21.09.17.
 */

public class Subscription implements Serializable {
    private String mName;
    private String mParkCode;
    private String mAccessCode;

    public String getName() {
        return mName;
    }

    public String getParkCode() {
        return mParkCode;
    }

    public String getAccessCode() {
        return mAccessCode;
    }

    public Subscription(String aName, String aParkCode, String aAccessCode){
        mName = aName;
        mParkCode = aParkCode;
        mAccessCode = aAccessCode;
    }
}
