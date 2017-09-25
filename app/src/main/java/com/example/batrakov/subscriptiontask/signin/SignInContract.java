package com.example.batrakov.subscriptiontask.signin;

import android.app.Activity;

import com.example.batrakov.subscriptiontask.BasePresenter;
import com.example.batrakov.subscriptiontask.BaseView;
import com.example.batrakov.subscriptiontask.Subscription;

import java.util.ArrayList;

/**
 * Created by batrakov on 20.09.17.
 */

public interface SignInContract {

    interface View extends BaseView<Presenter>{

        Activity getCurrentActivity();

        void startDelay();

    }

    interface Presenter extends BasePresenter{

        void writeToService(Subscription aSub);

        void readFromService();

        void buildReciever();

        boolean[] fullCheck(String aName, String aParkCode, String aAccessCode);

        boolean checkName(String aName, ArrayList<String> aList);

        boolean checkParkCode(String aParkCode);

        boolean checkAccessCode(String aAccessCode);
    }
}
