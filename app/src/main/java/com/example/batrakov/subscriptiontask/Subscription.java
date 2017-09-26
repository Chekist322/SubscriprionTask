package com.example.batrakov.subscriptiontask;

import java.io.Serializable;

/**
 * Created by batrakov on 21.09.17.
 */

public class Subscription implements Serializable {
    private String mName;
    private String mParkCode;
    private String mAccessCode;
    private boolean mEnabled;

    public String getName() {
        return mName;
    }

    public void setName(String aName) {
        mName = aName;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnableState(boolean aState) {
        mEnabled = aState;
    }

    public Subscription(String aName, String aParkCode, String aAccessCode, boolean aState) {
        mName = aName;
        mParkCode = aParkCode;
        mAccessCode = aAccessCode;
        mEnabled = aState;
    }
}
