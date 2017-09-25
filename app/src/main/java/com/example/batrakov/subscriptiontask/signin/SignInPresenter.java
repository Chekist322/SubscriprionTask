package com.example.batrakov.subscriptiontask.signin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.example.batrakov.subscriptiontask.Subscription;
import com.example.batrakov.subscriptiontask.SubscriptionService;

import java.util.ArrayList;

/**
 * Created by batrakov on 19.09.17.
 */

public class SignInPresenter implements SignInContract.Presenter {

    public static final String WRITE_TO_SERVICE = "write to service";
    public static final String GET_NAMES = "get names";
    public static final String NEW_SUB = "new sub";
    public static final String SERVICE_TASK = "service task";
    public static final String BROADCAST_ACTION = "filter sign in";
    public static final String SUB_INDEX = "sub index";

    private ArrayList<String> mNames;
    private SignInContract.View mView;

    public SignInPresenter(SignInContract.View aView){
        mNames = new ArrayList<>();
        mView = aView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void writeToService(Subscription aSub) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(NEW_SUB, aSub);
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class)
                .putExtra(SERVICE_TASK, WRITE_TO_SERVICE)
                .putExtras(bundle);
        mView.getCurrentActivity().startService(intent);
    }

    @Override
    public void readFromService() {
        Intent intent = new Intent(mView.getCurrentActivity().getApplicationContext(), SubscriptionService.class).putExtra(SERVICE_TASK, GET_NAMES);
        mView.getCurrentActivity().startService(intent);
    }

    @Override
    public void buildReciever() {
        Log.i("stage", "recieve");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras() != null) {
                    mNames = intent.getStringArrayListExtra(GET_NAMES);
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        mView.getCurrentActivity().registerReceiver(receiver, intFilt);
    }

    @Override
    public boolean[] fullCheck(String aName, String aParkCode, String aAccessCode) {
        boolean[] checkList = {false, false, false};
        checkList[0] = checkName(aName, mNames);
        checkList[1] = checkParkCode(aParkCode);
        checkList[2] = checkAccessCode(aAccessCode);
        return checkList;
    }

    @Override
    public boolean checkName(String aName, ArrayList<String> aList) {
        for (int i = 0; i < aList.size(); i++) {
            if (aName.equals(mNames.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkParkCode(String aParkCode) {
        return false;
    }

    @Override
    public boolean checkAccessCode(String aAccessCode) {
        return false;
    }




}
